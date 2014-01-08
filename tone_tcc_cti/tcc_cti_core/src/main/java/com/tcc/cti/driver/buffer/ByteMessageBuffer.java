package com.tcc.cti.driver.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用{@link ByteBuffer}为容器来缓存从Socket中读出的数据， 在该缓冲区可完成服务端消息整合，最后客户端读到的是完整的消息。
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ByteMessageBuffer implements MessageBuffer {

    private static final Logger logger = LoggerFactory.getLogger(ByteMessageBuffer.class);

    private static final int HEAD_LENGTH = 18;
    private static final int HEAD_VALUE_LENGTH = 5;
    private static final int HEAD_VALUE_OFFSET = 6;
    private static final int MESSAGE_MAX_LENGTH = 32 * 1024;
    private static final int DEFAULT_CAPACITY = 256 * 1024;
    private static final Pattern DEFAULT_HEAD_PATTERN = Pattern.compile("<head>\\d{5}</head>");
    private static final int NONE_MESSAGE_LEN = -1;
    private static final int MESSAGE_LEN_NOT_INTEGER = -2;
    private static final int MESSAGE_OVER_MAX_LENGTH = -3;

    private final int _maxLength;
    private final Charset _charset;
    private final ByteBuffer _buffer;

    private final Pattern headPatter = DEFAULT_HEAD_PATTERN;

    /**
     * 实例{@link ByteMessageBuffer}对象
     *
     * @param charsetName 消息字符集名
     */
    public ByteMessageBuffer(String charsetName) {
        this(DEFAULT_CAPACITY, Charset.forName(charsetName));
    }

    /**
     * 实例{@link ByteMessageBuffer}对象
     *
     * @param charset 消息字符集
     */
    public ByteMessageBuffer(Charset charset) {
        this(DEFAULT_CAPACITY, charset);
    }

    /**
     * 实例{@link ByteMessageBuffer}对象
     *
     * @param capacity 缓冲区大小
     * @param charsetName 消息字符集名
     */
    public ByteMessageBuffer(int capacity, String charsetName) {
        this(capacity, Charset.forName(charsetName));
    }

    /**
     * 实例{@link ByteMessageBuffer}对象
     *
     * @param capacity 缓冲区大小
     * @param charset 消息字符集
     */
    public ByteMessageBuffer(int capacity, Charset charset) {
        _maxLength = (capacity < MESSAGE_MAX_LENGTH) ? capacity : MESSAGE_MAX_LENGTH;
        _charset = charset;
        _buffer = (ByteBuffer) ByteBuffer.
            allocate(capacity).
            position(0).
            limit(0).
            mark();
    }

    @Override
    public String next() {
        synchronized (_buffer) {
            int len = getNextMessageLength();
            if (isNone(len)) {
                logger.debug("message is empty");
                return null;
            }

            if (notComplete(len)) {
                logger.debug("message is not complete");
                return null;
            }

            _buffer.reset();
            byte[] m = new byte[len];
            _buffer.get(m);
            _buffer.mark();

            String message = new String(m, _charset);
            logger.debug("Message is \"{}\"", message);
            return message;
        }
    }

    private boolean isNone(int len) {
        return len == NONE_MESSAGE_LEN;
    }

    /**
     * 消息不完整
     *
     * @param len 消息长度
     * @return
     */
    private boolean notComplete(int len) {
        int remaining = _buffer.remaining();
        return len > remaining;
    }

    /**
     * 清除缓存区
     */
    private void clearBuffer() {
        _buffer.position(0).limit(0).mark();
    }

    /**
     * 从消息头得到消息长度
     *
     * @return
     */
    private int getNextMessageLength() {
        boolean head = isPositionHead();
        int len = NONE_MESSAGE_LEN;
        if (head) {
            len = parseMessageLength();
        } else {
            boolean next = positionNextHead();
            if (next) {
                len = getNextMessageLength();
            }
        }

        if (len == MESSAGE_LEN_NOT_INTEGER
            || len == MESSAGE_OVER_MAX_LENGTH) {

            boolean next = positionNextHead();
            if (next) {
                len = getNextMessageLength();
            } else {
                clearBuffer();
                len = NONE_MESSAGE_LEN;
            }
        }

        return len;
    }

    /**
     * 判断{@code position}是指到消息头
     *
     * <pre>
     * 消息头格式：<head>00001</head>
     * <pre>
     * @return true 是消息头
     */
    private boolean isPositionHead() {
        int remaining = _buffer.remaining();
        if (remaining < HEAD_LENGTH) {
            return false;
        }

        byte[] bytes = new byte[HEAD_LENGTH];
        _buffer.get(bytes);
        String s = new String(bytes, _charset);
        Matcher matcher = headPatter.matcher(s);
        boolean head = matcher.matches();
        _buffer.reset();

        return head;
    }

    /**
     * 解析消息头得到消息长度
     *
     * @return
     */
    private int parseMessageLength() {
        byte[] bytes = new byte[HEAD_LENGTH];
        _buffer.get(bytes);
        byte[] values = new byte[HEAD_VALUE_LENGTH];
        System.arraycopy(bytes, HEAD_VALUE_OFFSET, values, 0, HEAD_VALUE_LENGTH);
        String lenStr = new String(values, _charset);
        logger.debug("Parse message len is \"{}\"", lenStr);

        try {
            int len = Integer.valueOf(lenStr) + HEAD_LENGTH;
            if (len > _maxLength) {
                _buffer.mark();
                len = MESSAGE_OVER_MAX_LENGTH;
            } else {
                _buffer.reset();
            }
            return len;
        } catch (Exception e) {
            logger.error("Message len type is error \"{}\"", e.getMessage());
            _buffer.mark();
            return MESSAGE_LEN_NOT_INTEGER;
        }
    }

    /**
     * 指定下一个消息头位置
     *
     * @param position true 移动到下一个节点
     */
    private boolean positionNextHead() {
        int remaining = _buffer.remaining();
        byte[] bytes = new byte[remaining];
        _buffer.get(bytes);
        String s = new String(bytes, _charset);

        Matcher matcher = headPatter.matcher(s);
        boolean has = matcher.find();
        if (has) {
            int len = matcher.start();
            int p = getBytesLength(s, len);
            _buffer.position(p).mark();
        } else {
            _buffer.reset();
        }

        return has;
    }

    /**
     * 通过得字符转换byte数据组长度，得到字符在{@link ByteBuffer}中所占长度。
     *
     * @param s 字符串
     * @param len 长度
     * @return
     */
    private int getBytesLength(String s, int len) {
        return s.substring(0, len).getBytes(_charset).length;
    }

    @Override
    public boolean append(byte[] bytes) {
        if (bytes == null) {
            bytes = new byte[0];
        }

        int len = bytes.length;
        if (len == 0) {
            logger.debug("Message is empty");
            return true;
        }

        String m = new String(bytes, _charset);
        logger.debug("Append message is \"{}\"", m);

        if (len > _maxLength) {
            logger.warn("Append message must length < {}", len);
            return false;
        }

        synchronized (_buffer) {
            int free = _buffer.capacity() - _buffer.limit();
            if (free < len) {
                int position = _buffer.reset().position();
                if ((free + position) > len) {
                    _buffer.compact();
                    _buffer.flip().mark();
                } else {
                    logger.warn("Message buffer is full");
                    clearBuffer();
                }
            }
            int limit = _buffer.limit();
            _buffer.position(limit).limit(limit + len);
            _buffer.put(bytes);
            _buffer.reset();
            _buffer.notifyAll();
            return true;
        }
    }

    protected int getPosition() {
        synchronized (_buffer) {
            return _buffer.position();
        }
    }

    protected int getLimit() {
        synchronized (_buffer) {
            return _buffer.limit();
        }
    }
}
