package epg.protocol.dto.base.http;

import com.fasterxml.jackson.databind.JsonNode;
import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.protocol.dto.base.jrpc.JrpcData;

/**
 * HTTP response that encapsulate jrpc
 */
public class HttpResponseJRPC extends HttpResponse {

    protected AbstractDto result;

    public HttpResponseJRPC() {}

    public HttpResponseJRPC(AbstractDto result) {
        this.result = result;
    }

    public AbstractDto getResult() {
        return result;
    }

    public void setResult(AbstractDto result) {
        this.result = result;
    }

}