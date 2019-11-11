package epg.server.handlers.base;

import com.fasterxml.jackson.databind.JsonNode;
import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.protocol.dto.base.jrpc.JrpcData;

import java.util.function.Function;

public interface JrpcMethodHandler extends Function<JsonNode,AbstractDto> {}
