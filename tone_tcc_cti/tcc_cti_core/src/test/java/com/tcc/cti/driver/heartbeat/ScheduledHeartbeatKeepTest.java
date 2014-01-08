package com.tcc.cti.driver.heartbeat;

import static org.mockito.Mockito.mock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import junit.framework.Assert;

import org.junit.Test;

import com.tcc.cti.driver.session.Sessionable;

/**
 * {@link ScheduledHeartbeatKeep}单元测试
 *
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class ScheduledHeartbeatKeepTest {

    @Test
    public void testStartIndependent() throws Exception {
        Sessionable session = mock(Sessionable.class);

        ScheduledHeartbeatKeep keep = new ScheduledHeartbeatKeep();
        Assert.assertTrue(keep.isIndependent());

        keep.start(session);
        Assert.assertTrue(keep.isStart());
        keep.shutdown();
        Assert.assertTrue(keep.getExecutorService().isShutdown());
        Assert.assertTrue(keep.isShutdown());
    }

    @Test
    public void testStart() throws Exception {
        Sessionable session = mock(Sessionable.class);
        ScheduledExecutorService executor
            = Executors.newSingleThreadScheduledExecutor();
        ScheduledHeartbeatKeep keep = new ScheduledHeartbeatKeep(executor);
        Assert.assertFalse(keep.isIndependent());

        keep.start(session);
        Assert.assertTrue(keep.isStart());
        keep.shutdown();
        Assert.assertTrue(keep.isShutdown());
        Assert.assertFalse(keep.getExecutorService().isShutdown());
        keep.getExecutorService().shutdownNow();
    }
}
