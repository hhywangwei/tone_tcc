package com.tcc.cti.driver.session;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.Configure;
import com.tcc.cti.driver.Operator;
import com.tcc.cti.driver.connection.Connectionable;
import com.tcc.cti.driver.connection.NioConnection;
import com.tcc.cti.driver.heartbeat.HeartbeatKeepable;
import com.tcc.cti.driver.heartbeat.ScheduledHeartbeatKeep;
import com.tcc.cti.driver.session.process.MessageProcess;
import com.tcc.cti.driver.session.process.MessageProcessable;
import com.tcc.cti.driver.session.task.StocketReceiveTask;

/**
 * 用户连接服务端Session Factory,得到Session可与CTI服务端通讯
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class SessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(SessionFactory.class);
    private static final Object _monitor = new Object();
    private static final Selector _selector = openSelector();
    private static final StocketReceiveTask _receiveTask = new StocketReceiveTask(_selector);
    private static final Thread _receiveThread = startReceiveThread(_receiveTask);

    private final Configure _configure;
    private final MessageProcessable _messageProcess;
    private final ScheduledExecutorService _heartExcecutorService;

    private HeartbeatKeepable _heartbeatKeeep;

    private static Selector openSelector() {
        try {
            return Selector.open();
        } catch (IOException e) {
            logger.debug("Open selector fail,Error is {}", e.getMessage());
            throw new RuntimeException("Open selector fail,Error is " + e.getMessage());
        }
    }

    private static Thread startReceiveThread(StocketReceiveTask task) {
        Thread t = new Thread(task);
        t.start();
        return t;
    }

    public SessionFactory(Configure configure) {
        this(configure, new MessageProcess(configure.getCharset()));
    }

    public SessionFactory(Configure configure, MessageProcessable messageProcess) {
        _configure = configure;
        _messageProcess = messageProcess;
        _heartExcecutorService = Executors.newScheduledThreadPool(_configure.getHeartPoolSize());
        _heartbeatKeeep = new ScheduledHeartbeatKeep(_heartExcecutorService,
            configure.getHeartbeatInitDelay(), _configure.getHeartbeatDelay());
    }

    public Sessionable getSession(Operator key) throws SessionRegisterException {
        Sessionable session = SessionManager.getManager().getSession(key);
        if (session != null) {
            return session;
        }
        return register(key);
    }

    private Sessionable register(Operator key) throws SessionRegisterException {
        Sessionable session = null;

        synchronized (_monitor) {
            Connectionable connection = new NioConnection(_selector, _configure.getAddress());
            session = new Session.Builder(key, connection, _messageProcess, _heartbeatKeeep).
                setHeartbeatTimeout(_configure.getHeartbeatTimeout()).
                setCharset(_configure.getCharset()).
                build();
            SessionManager.getManager().addSession(key, session);
        }

        try {
            _receiveTask.suspend();
            session.start();
        } catch (IOException e) {
            logger.error("Register {} is error:{}", key.toString(), e.toString());
            throw new SessionRegisterException(key, e);
        } finally {
            _receiveTask.restart();
        }

        return session;
    }

    public void close() {
        synchronized (_monitor) {
            SessionManager.getManager().close();
            _receiveThread.interrupt();
            _heartExcecutorService.shutdownNow();
        }
    }

    public void setHeartbeatKeepable(HeartbeatKeepable keep) {
        if (keep == null) {
            throw new IllegalArgumentException("Heartbeat keep not null");
        }
        _heartbeatKeeep = keep;
    }

}
