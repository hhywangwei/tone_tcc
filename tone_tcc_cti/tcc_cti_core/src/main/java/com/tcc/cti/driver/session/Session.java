package com.tcc.cti.driver.session;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.buffer.ByteMessageBuffer;
import com.tcc.cti.driver.buffer.MessageBuffer;
import com.tcc.cti.driver.connection.Connectionable;
import com.tcc.cti.driver.heartbeat.HeartbeatKeepable;
import com.tcc.cti.driver.message.MessageType;
import com.tcc.cti.driver.message.request.LogoutRequest;
import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.sequence.MemoryGeneratorSeq;
import com.tcc.cti.driver.session.process.MessageProcessable;
import com.tcc.cti.driver.session.process.handler.receive.ParseMessageException;

/**
 * 每个与CTI服务连接的用户都会创建于服务连接{@link Session},该方法封装了网络连接的复杂度。
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class Session implements Sessionable {

    private static final Logger logger = LoggerFactory.getLogger(Session.class);

    public static class Builder {

        private final Operator _key;
        private final Connectionable _connection;
        private final MessageProcessable _messageProcess;
        private final HeartbeatKeepable _heartbeatKeep;
        private GeneratorSeq _generatorSeq;
        private int _heartbeatTimeout = 65 * 1000;
        private int _clientTimeout = 90 * 1000;
        private Charset _charset = Charset.forName("GBK");

        public Builder(Operator key, Connectionable connection,
            MessageProcessable messageProcess, HeartbeatKeepable heartbeatKeep) {

            _key = key;
            _connection = connection;
            _messageProcess = messageProcess;
            _heartbeatKeep = heartbeatKeep;
            _generatorSeq = new MemoryGeneratorSeq(
                _key.getCompanyId(), _key.getCompanyId());
        }

        public Builder setGeneratorSeq(GeneratorSeq generatorSeq) {
            _generatorSeq = generatorSeq;
            return this;
        }

        public Builder setHeartbeatTimeout(int timeout) {
            _heartbeatTimeout = timeout;
            return this;
        }

        public Builder setCharset(Charset charset) {
            _charset = charset;
            return this;
        }

        public Builder setClientTimeout(int timeout) {
            _clientTimeout = timeout;
            return this;
        }

        public Session build() {
            return new Session(_key, _connection, _messageProcess,
                _heartbeatKeep, _generatorSeq, _heartbeatTimeout,
                _clientTimeout, _charset);
        }
    }

    private final Operator _key;
    private final Connectionable _conn;
    private final MessageProcessable _messageProcess;
    private final HeartbeatKeepable _heartbeatKeep;
    private final GeneratorSeq _generator;
    private final int _heartbeatTimeout;
    private final int _clientTimeout;
    private final MessageBuffer _messageBuffer;
    private final Object _monitor = new Object();
    private final Phone _phone = new Phone();

    private volatile Status _status = Status.New;
    private volatile long _lastHeartbeanTime = System.currentTimeMillis();
    private volatile long _lastClientRequestTime = System.currentTimeMillis();

    private SocketChannel _channel;

    protected Session(Operator operatorKey, Connectionable conn,
        MessageProcessable messageProcess, HeartbeatKeepable heartbeatKeep,
        GeneratorSeq generator, int heartbeatTimeout, int clientTimeout, Charset charset) {

        _key = operatorKey;
        _conn = conn;
        _messageProcess = messageProcess;
        _heartbeatKeep = heartbeatKeep;
        _generator = generator;
        _heartbeatTimeout = heartbeatTimeout;
        _clientTimeout = clientTimeout;
        _messageBuffer = new ByteMessageBuffer(charset);
    }

    /**
     * 链接服务端开始服务
     *
     * @throws ClientException
     */
    @Override
    public void start() throws IOException {
        synchronized (_monitor) {
            if (isNew()) {
                logger.debug("{} Session is start", _key.toString());
                try {
                    _status = Status.Active;
                    _channel = _conn.connect(this);
                } catch (IOException e) {
                    logger.error("{} Session start is fail,error is {}", _key.toString(), e.getMessage());
                    _status = Status.Close;
                    throw e;
                }
            }
        }
    }

    /**
     * 登录是否成功成功开始心跳
     *
     * @param success
     */
    @Override
    public void login(boolean success) {
        synchronized (_monitor) {
            if (success && isActive()) {
                _status = Status.Service;
                _heartbeatKeep.start(this);
                heartbeatTouch();
            }
        }
    }

    @Override
    public void heartbeatTouch() {
        _lastHeartbeanTime = System.currentTimeMillis();
        try {
            if (isClientRquestTimeout()) {
                logger.debug("{} client request timeout close session", _key.toString());
                close();
            }
        } catch (IOException e) {
            logger.error("Client request timeout close error {}", e.toString());
        }
    }

    boolean isClientRquestTimeout() {
        boolean calling = false;
        synchronized (_phone) {
            calling = _phone.isCalling();
        }
        if (calling) {
            _lastClientRequestTime = System.currentTimeMillis();
            return false;
        }
        long now = System.currentTimeMillis();
        long deff = (now - _lastClientRequestTime);
        logger.debug("Now is {},last request time {}", now, _lastClientRequestTime);
        logger.debug("deff is {} ......", deff);
        return deff > (long) _clientTimeout;
    }

    @Override
    public void close() throws IOException {
        synchronized (_monitor) {

            if (isClose()) {
                return;
            }

            if (isNew()) {
                _status = Status.Close;
                return;
            }

            logger.debug("{} start session close", _key.toString());
            logout();
            _status = Status.Close;
            _heartbeatKeep.shutdown();
            if (_channel != null && _channel.isOpen()) {
                _channel.close();
            }
            logger.debug("{} finish session close", _key.toString());
        }
    }

    private void logout() {
        try {
            LogoutRequest r = new LogoutRequest();
            send(r);
        } catch (IOException e) {
            logger.error("{} send logout", _key.toString());
        }
    }

    @Override
    public void append(byte[] bytes) {
        _messageBuffer.append(bytes);
        String m = _messageBuffer.next();
        try {
            if (StringUtils.isNotBlank(m)) {
                _messageProcess.receiveProcess(this, m);
            }
        } catch (ParseMessageException e) {
            logger.error("ParseMessage {} is error.", m);
        }
    }

    @Override
    public void send(Requestable<? extends Response> request) throws IOException {
        synchronized (_monitor) {
            if (isNew()) {
                start();
            }
        }

        if (isClose()) {
            logger.debug("{} Send message,but closed", _key.toString());
            throw new IOException("Session already closed,not send message.");
        }
        if (isAccess(request.getMessageType())) {
            _messageProcess.sendProcess(this, request, _generator);
            if (isRequestMessage(request.getMessageType())) {
                _lastClientRequestTime = System.currentTimeMillis();
                heartbeatTouch();
            }
        } else {
            logger.debug("{} not access cti server.", _key.toString());
            throw new SessionAccessException(_key);
        }
    }

    private boolean isRequestMessage(String type) {
        return !(MessageType.Heartbeat.isRequest(type)
            || MessageType.Logout.isRequest(type));
    }

    private boolean isAccess(String messageType) {
        boolean access = false;
        if (isService()) {
            access = true;
        }
        if (isActive() && MessageType.Login.isRequest(messageType)) {
            access = true;
        }

        return access;
    }

    @Override
    public Operator getOperator() {
        return _key;
    }

    @Override
    public Status getStatus() {

        if (_status == Status.New || _status == Status.Close) {
            return _status;
        }
        if (isHeartbeanTimeout()) {
            _status = Status.Timeout;
        }

        return _status;
    }

    private boolean isHeartbeanTimeout() {
        long now = System.currentTimeMillis();
        int deff = (int) (now - _lastHeartbeanTime);
        return (deff > _heartbeatTimeout);
    }

    @Override
    public void updatePhone(UpdatePhoneCallBack callback) {
        synchronized (_phone) {
            callback.update(_phone);
        }
    }

    public Phone getPhone() {
        synchronized (_phone) {
            return _phone;
        }
    }

    @Override
    public SocketChannel getSocketChannel() {
        return _channel;
    }

    @Override
    public boolean isNew() {
        return getStatus() == Status.New;
    }

    @Override
    public boolean isActive() {
        return getStatus() == Status.Active;
    }

    @Override
    public boolean isService() {
        return getStatus() == Status.Service;
    }

    @Override
    public boolean isClose() {
        return getStatus() == Status.Close;
    }

    @Override
    public boolean isTimeout() {
        return getStatus() == Status.Timeout;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((_key == null) ? 0 : _key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Session other = (Session) obj;
        if (_key == null) {
            if (other._key != null) {
                return false;
            }
        } else if (!_key.equals(other._key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OperatorChannel [_operatorKey=");
        builder.append(_key.toString());
        builder.append("]");
        return builder.toString();
    }
}
