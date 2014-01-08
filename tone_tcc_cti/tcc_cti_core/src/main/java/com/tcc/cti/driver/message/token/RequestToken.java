package com.tcc.cti.driver.message.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcc.cti.driver.Operator;

/**
 * 实现请求令牌
 * 
 * @author <a href="hhywangwei@gmail.com">wangwei</a>
 */
public class RequestToken implements Tokenable {

    private static final Logger logger = LoggerFactory.getLogger(RequestToken.class);

    private static final String KEY_TEMPLATE = "%s-%s-%s-%s";
    private final String key;

    public RequestToken(Operator operator, String seq, String messageType) {
        key = String.format(KEY_TEMPLATE,
            operator.getCompanyId(), operator.getOpId(), seq, messageType);
        logger.debug("toke is {}", key);
    }

    @Override
    public Object token() {
        return key;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        RequestToken other = (RequestToken) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RequestToken [key=");
        builder.append(key);
        builder.append("]");
        return builder.toString();
    }
}
