package epg.protocol.dto.base.jrpc;

import java.io.Serializable;

public abstract class AbstractDto extends JrpcData implements Serializable {

}


/*

import com.fasterxml.jackson.annotation.JsonFormat;

public abstract class AbstractDto implements Serializable {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime updated;
}
*/
