package com.dolfdijkstra.beacon.capture.body;

import java.util.UUID;

import com.dolfdijkstra.beacon.capture.NameValuePair;
import com.dolfdijkstra.beacon.capture.RequestEncoder;

import org.codehaus.jackson.util.CharTypes;

public class JsonEncoder implements RequestEncoder<BodyCapturedRequest, String> {
    private static final class Builder {
        private StringBuilder b = new StringBuilder("{");
        private int attrCount = 0;

        private void addName(String name) {
            if (attrCount++ > 0) {
                b.append(",");
            }
            b.append('"').append(name).append('"').append(':');
        }

        public void add(String name, UUID value) {
            if (value == null)
                return;
            addName(name);
            b.append('"').append(value.toString()).append('"');
        }

        public void add(String name, String value) {
            if (value == null)
                return;
            addName(name);
            b.append('"');
            CharTypes.appendQuoted(b, value);
            b.append('"');
        }

        public void add(String name, long value) {
            addName(name);
            b.append(value);

        }

        public void add(String name, Iterable<NameValuePair> value) {
            if (value == null)
                return;
            addName(name);
            b.append('{');
            int count = 0;
            for (NameValuePair h : value) {
                if (count++ > 0) {
                    b.append(",");
                }
                b.append("\"");
                CharTypes.appendQuoted(b, h.getName());
                b.append("\":\"");
                CharTypes.appendQuoted(b, h.getValue());
                b.append("\"");

            }
            b.append('}');

        }

        public void add(String name, boolean value) {
            addName(name);
            b.append(value);

        }

        public void addJson(String name, String value) {
            if (value == null)
                return;
            addName(name);
            // b.append('{').append(value).append('}');
            b.append(value);

        }

        /**
         * @return
         * @see java.lang.StringBuilder#toString()
         */
        public String toString() {
            return b.toString();
        }

        public void close() {
            b.append("}");

        }

    }

    // private ObjectMapper mapper = new ObjectMapper(); // can reuse, share
    // globally

    @Override
    public String encode(BodyCapturedRequest message) {

        Builder builder = new Builder();
        builder.add("uuid", message.getUUID());

        builder.add("count", message.getCount());
        builder.add("timestamp", message.getTimestamp());
        builder.add("headers", message.getHeaders());
        builder.add("secure", message.isSecure());
        builder.add("method", message.getMethod());
        builder.add("protocol", message.getProtocol());
        builder.add("queryString", message.getQueryString());
        builder.add("remoteAddr", message.getRemoteAddr());
        builder.add("remoteHost", message.getRemoteHost());

        builder.add("requestUri", message.getRequestURI());
        builder.add("requestUrl", message.getRequestURL());

        builder.add("serverName", message.getServerName());
        builder.add("serverPort", message.getServerPort());

        if (message.isRequestedSessionIdValid()) {
            builder.add("requestedSessionId", message.getRequestedSessionId());
        }
        builder.addJson("body", message.getBody());
        builder.close();
        return builder.toString();
    }
}
