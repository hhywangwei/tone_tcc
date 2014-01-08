package com.tcc.cti.driver.session.process.handler.receive;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.message.token.RequestToken;
import com.tcc.cti.driver.message.token.Tokenable;
import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.process.Requestsable;
import com.tcc.cti.driver.session.process.handler.ReceiveHandlerable;

/**
 * 实现消息接受处理，实现了消息处理主要流程。
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public abstract class AbstractReceiveHandler implements ReceiveHandlerable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractReceiveHandler.class);
    private static final String ROOT = "root";
    private static final String XML_FULL_PATTER = "<?xml version=\"1.0\"?>\n<root>%s</root>";

    protected static final String MESSAGE_TYPE_PARAMETER = "msg";
    protected static final String COMPANY_ID_PARAMETER = "CompanyID";
    protected static final String OP_ID_PARAMETER = "OPID";
    protected static final String WORK_ID_PARAMETER = "WorkID";
    protected static final String SEQ_PARAMETER = "seq";
    private static final String RESULT_PARAMETER = "result";

    @Override
    public void receive(Requestsable requests,
        Sessionable channel, String message) throws ParseMessageException {

        logger.debug("receive message is \"{}\"", message);

        try {
            Map<String, String> content = parseMessage(message);
            String msgType = content.get(MESSAGE_TYPE_PARAMETER);
            if (StringUtils.isNotBlank(msgType) && isReceive(msgType)) {
                receiveHandler(requests, channel, content);
            }
        } catch (SAXException e) {
            throw new ParseMessageException(e);
        }
    }

    /**
     * 解析消息得到{@link Map}消息集合，key是消息标准，value消息值。
     *
     * @param message cit服务返回消息字符串
     * @return 消息集合
     * @throws SAXException
     */
    protected Map<String, String> parseMessage(String message) throws SAXException {
        Map<String, String> content = new HashMap<String, String>();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            String xml = String.format(XML_FULL_PATTER, message);

            logger.debug("message xml is \"{}\"", xml);

            StringReader reader = new StringReader(xml);
            sp.parse(new InputSource(reader), new XmlHandler(content));
        } catch (ParserConfigurationException e) {
            //none instance
            logger.error("ParserConfigurationException is exception:{}", e);
        } catch (IOException e) {
            //none instance
            logger.error("IOException is exception:{}", e);
        }

        return content;
    }

    /**
     * 判读消息是否处理
     *
     * @param msgType 消息类型
     * @return
     */
    protected abstract boolean isReceive(String msgType);

    /**
     * 处理接受消息
     *
     * @param pool 消息接收池
     * @param session {@link Sessionable}
     * @param content 接受消息内容
     */
    protected void receiveHandler(Requestsable requests, Sessionable session,
        Map<String, String> content) {

        String companyId = session.getOperator().getCompanyId();
        String opId = session.getOperator().getOpId();
        String seq = content.get(SEQ_PARAMETER);
        String msgType = getRequestMessageType(content.get(MESSAGE_TYPE_PARAMETER));

        Response response = buildMessage(companyId, opId, seq, content);
        if (response != null) {
            Tokenable token = new RequestToken(session.getOperator(), seq, msgType);
            requests.recevie(token, response);
        }
    }

    /**
     * 得到消息需求类型，对应请求{@link Requestable}对象
     *
     * @param msgType 接收消息类型
     * @return
     */
    protected String getRequestMessageType(String msgType) {
        return msgType;
    }

    /**
     * 通过接收消息构建消息对象
     *
     * @param companyId 公司编号
     * @param opId 操作员编号
     * @param seq 消息序列号
     * @param content 接收类容
     * @return
     */
    protected Response buildMessage(String companyId, String opId,
        String seq, Map<String, String> content) {

        String result = content.get(RESULT_PARAMETER);
        result = result == null ? "0" : result;
        return new Response(seq, result);
    }

    /**
     * 接受消息解析处理类，使用SAX解析器处理
     *
     * @author <a href="hhywangwei@gmail.com">wangwei</a>
     */
    protected static class XmlHandler extends DefaultHandler {

        private final Map<String, String> _content;
        private StringBuilder _value = new StringBuilder();

        public XmlHandler(Map<String, String> content) {
            _content = content;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            int len = _value.length();
            if (len != 0) {
                _value.delete(0, len);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            _value.append(new String(ch, start, length));
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (ROOT.equals(qName)) {
                return;
            }

            String v = _value.toString();
            logger.debug("element is \"{}\" value is \"{}\"", qName, v);
            _content.put(qName, v);
        }
    }

}
