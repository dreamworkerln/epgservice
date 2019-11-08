package epg.protocol.dto.programguide;

import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.protocol.dto.channel.ChannelDto;

import java.util.Map;
import java.util.TreeMap;

public class ProgramGuideDto extends AbstractDto {

    private Map<Integer,ChannelDto> channelList = new TreeMap<>();

    public Map<Integer, ChannelDto> getChannelList() {
        return channelList;
    }

    public void setChannelList(Map<Integer, ChannelDto> channelList) {
        this.channelList = channelList;
    }

}
