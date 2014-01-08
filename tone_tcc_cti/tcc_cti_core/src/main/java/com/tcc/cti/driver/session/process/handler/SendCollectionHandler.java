package com.tcc.cti.driver.session.process.handler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.tcc.cti.driver.message.request.Requestable;
import com.tcc.cti.driver.message.response.Response;
import com.tcc.cti.driver.sequence.GeneratorSeq;
import com.tcc.cti.driver.session.Sessionable;
import com.tcc.cti.driver.session.process.handler.send.CallHelpSendHandler;
import com.tcc.cti.driver.session.process.handler.send.CallHoldSendHandler;
import com.tcc.cti.driver.session.process.handler.send.CallSendHandler;
import com.tcc.cti.driver.session.process.handler.send.CommonSendHandler;
import com.tcc.cti.driver.session.process.handler.send.GroupSendHandler;
import com.tcc.cti.driver.session.process.handler.send.HeartbeatSendHandler;
import com.tcc.cti.driver.session.process.handler.send.LoginSendHandler;
import com.tcc.cti.driver.session.process.handler.send.MobileNumberSendHandler;
import com.tcc.cti.driver.session.process.handler.send.MonitorSendHandler;
import com.tcc.cti.driver.session.process.handler.send.OutCallCancelSendHandler;
import com.tcc.cti.driver.session.process.handler.send.OutCallSendHandler;
import com.tcc.cti.driver.session.process.handler.send.OwnSendHandler;
import com.tcc.cti.driver.session.process.handler.send.SilenceSendHandler;
import com.tcc.cti.driver.session.process.handler.send.StatusSendHandler;
import com.tcc.cti.driver.session.process.handler.send.TransferGroupSendHanlder;
import com.tcc.cti.driver.session.process.handler.send.TransferOneSendHandler;

/**
 * 把发送消息处理类聚合在一起，简化客户调用
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SendCollectionHandler implements SendHandlerable {

    private List<SendHandlerable> _handlers = new ArrayList<>();

    public SendCollectionHandler() {
        initHandlers(_handlers);
    }

    private void initHandlers(List<SendHandlerable> handlers) {

        handlers.add(new CallSendHandler());
        handlers.add(new CallHelpSendHandler());
        handlers.add(new CallHoldSendHandler());
        handlers.add(new CommonSendHandler());
        handlers.add(new GroupSendHandler());
        handlers.add(new HeartbeatSendHandler());
        handlers.add(new LoginSendHandler());
        handlers.add(new MobileNumberSendHandler());
        handlers.add(new MonitorSendHandler());
        handlers.add(new OutCallSendHandler());
        handlers.add(new OutCallCancelSendHandler());
        handlers.add(new OwnSendHandler());
        handlers.add(new SilenceSendHandler());
        handlers.add(new StatusSendHandler());
        handlers.add(new TransferGroupSendHanlder());
        handlers.add(new TransferOneSendHandler());

    }

    @Override
    public void send(Sessionable session,
        Requestable<? extends Response> request, GeneratorSeq generator,
        Charset charset) throws IOException {

        for (SendHandlerable handler : _handlers) {
            handler.send(session, request, generator, charset);
        }
    }

    public void addHandler(SendHandlerable handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Send handler is not null");
        }
        _handlers.add(handler);
    }

    public void addHandlers(List<SendHandlerable> handlers) {
        if (handlers == null) {
            throw new IllegalArgumentException("Send handlers is not null");
        }
        _handlers.addAll(handlers);
    }

    public void setHandlers(List<SendHandlerable> handlers) {
        if (handlers == null) {
            throw new IllegalArgumentException("Send handlers is not null");
        }
        _handlers = handlers;
    }
}
