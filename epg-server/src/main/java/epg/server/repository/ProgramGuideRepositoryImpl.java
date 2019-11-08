package epg.server.repository;

import epg.server.entities.channel.Channel;
import epg.server.entities.programguide.ProgramGuide;
import org.springframework.stereotype.Service;


@Service
public class ProgramGuideRepositoryImpl implements ProgramGuideRepository {

    private ProgramGuide programGuide = new ProgramGuide();

    @Override
    public ProgramGuide get() {
        return programGuide;
    }
}
