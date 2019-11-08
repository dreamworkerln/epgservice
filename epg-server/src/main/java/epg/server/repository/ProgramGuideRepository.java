package epg.server.repository;

import epg.server.entities.programguide.ProgramGuide;

// ToDo: Перейти к специализированным коллекциям и методам
// ChanellList, addChannel, add program, get all program for all channels
// - после того как будет понятно, какие методы нужны в интерфейсе,
public interface ProgramGuideRepository {

    ProgramGuide get();
}
