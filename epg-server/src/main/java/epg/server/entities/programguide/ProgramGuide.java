package epg.server.entities.programguide;

import epg.server.entities.channel.Channel;
import epg.server.entities.base.AbstractEntity;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Программа передач<br>
 * Репозиторий ProgramGuideRepository предоставляет доступ к основному ProgramGuide -
 * резидентной, полной программе передач<br>
 * Также этот класс используется при передачи данных клиентам, в этом случае там может находиться
 * лишь кусок из основного ProgramGuide.
 * (main program guide list - its singleton in ProgramGuideRepository existing residential in memory)
 */
public class ProgramGuide extends AbstractEntity {

    private ConcurrentNavigableMap<Integer,Channel> channelList = new ConcurrentSkipListMap<>();

    public ConcurrentNavigableMap<Integer, Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(ConcurrentNavigableMap<Integer, Channel> channelList) {
        this.channelList = channelList;
    }

}

