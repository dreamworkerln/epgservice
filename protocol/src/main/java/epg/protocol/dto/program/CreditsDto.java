package epg.protocol.dto.program;

import epg.protocol.dto.base.jrpc.AbstractDto;

import java.util.ArrayList;
import java.util.List;

public class CreditsDto extends AbstractDto {
    private List<String> director = new ArrayList<>();
    private List<String> presenter = new ArrayList<>();
    private List<String> actor = new ArrayList<>();
}
