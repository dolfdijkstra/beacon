package com.dolfdijkstra.beacon.capture.body;

import java.io.Serializable;

import com.dolfdijkstra.beacon.capture.BaseCapturedRequest;

/**
 * @author Dolf.Dijkstra
 * @since Jan 8, 2008
 */
public final class BodyCapturedRequest extends BaseCapturedRequest implements Serializable {

    private String body;

    private long count;

    /**
     * 
     */
    private static final long serialVersionUID = -4762070410378881594L;

    /**
     * 
     */
    public BodyCapturedRequest() {
        super();
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    public void setCount(long num) {
        count = num;

    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BodyCapturedRequest [getBody()=" + getBody() + ", getCount()=" + getCount() + ", getHeaders()="
                + getHeaders() + ", isSecure()=" + isSecure() + ", getMethod()=" + getMethod() + ", getProtocol()="
                + getProtocol() + ", getQueryString()=" + getQueryString() + ", getRemoteAddr()=" + getRemoteAddr()
                + ", getRemoteHost()=" + getRemoteHost() + ", getRequestedSessionId()=" + getRequestedSessionId()
                + ", getRequestURI()=" + getRequestURI() + ", getRequestURL()=" + getRequestURL()
                + ", getServerName()=" + getServerName() + ", getServerPort()=" + getServerPort()
                + ", isRequestedSessionIdValid()=" + isRequestedSessionIdValid() + ", getTimestamp()=" + getTimestamp()
                + ", getUUID()=" + getUUID() + "]";
    }

}
