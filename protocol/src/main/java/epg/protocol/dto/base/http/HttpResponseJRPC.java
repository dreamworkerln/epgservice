package epg.protocol.dto.base.http;

import epg.protocol.dto.base.jrpc.JrpcData;

/**
 * HTTP response that encapsulate jrpc
 */
public class HttpResponseJRPC extends HttpResponse {

    protected JrpcData result;

    public HttpResponseJRPC() {}

    public HttpResponseJRPC(JrpcData result) {
        this.result = result;
    }

    public JrpcData getResult() {
        return result;
    }

    public void setResult(JrpcData result) {
        this.result = result;
    }
}