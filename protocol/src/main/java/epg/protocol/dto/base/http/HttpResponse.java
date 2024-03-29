package epg.protocol.dto.base.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import epg.protocol.dto.base.Message;
import org.springframework.http.HttpStatus;

import java.beans.Transient;

public class HttpResponse extends Message {

    protected HttpStatus status;

    public static HttpResponse forbidden() {

        HttpResponse result = new HttpResponse();
        result.status =  HttpStatus.FORBIDDEN;

        return result;
    }

    public static HttpResponse ok() {

        HttpResponse result = new HttpResponse();
        result.status =  HttpStatus.OK;
        return result;
    }


    HttpResponse() {}


    public HttpResponse(HttpStatus status) {
        this.status = status;
    }



    @Transient
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
