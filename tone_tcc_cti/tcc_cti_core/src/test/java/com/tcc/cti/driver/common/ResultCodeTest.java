package com.tcc.cti.driver.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单元测试 {@link ResultCode}
 *
 * @author 《a href="hhywangwei@gmail.com">wangwei</a>
 *
 */
public class ResultCodeTest {

    @Test
    public void testGetDetail() {
        ResultCode code = ResultCode.InstanceCode;

        String detail = code.getDetail("0");
        Assert.assertEquals("正常返回", detail);
        detail = code.getDetail("9");
        Assert.assertEquals("工号不存在", detail);
        detail = code.getDetail("18");
        Assert.assertEquals("座席号码不匹配", detail);
    }
}
