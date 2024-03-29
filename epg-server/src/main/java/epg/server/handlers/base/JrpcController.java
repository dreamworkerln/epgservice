package epg.server.handlers.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark class as billing request handler
 * <br>
 * This allow to find appropriate handler for request (mapping)
 */

// Make the annotation available at runtime:
@Retention(RetentionPolicy.RUNTIME)




// Allow to use only on types:
@Target(ElementType.TYPE)
public @interface JrpcController {

    String path();

//    /**
//     * JrpcParameter class - класс, представляющий собой параметр (json-rpc) запроса
//     * @return
//     */
//    Class<? extends JrpcParameter> request();

//    /**
//     * Name of request class (short)
//     */
//    Class requestClassName();
}
