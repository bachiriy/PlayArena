package com.playarena.app.dao.impl;

import com.playarena.app.dao.TournamentDao;
import com.playarena.app.model.Tournament;
import com.playarena.app.repository.impl.TournamentRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TournamentDaoImpl implements TournamentDao {
    private final Logger log = LoggerFactory.getLogger(TournamentDaoImpl.class);
    private final TournamentRepositoryImpl tournamentRepository;

    public TournamentDaoImpl(TournamentRepositoryImpl tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    public int calculateEstimatedTournamentDuration(Tournament tournament){
        int numberOfTeams = tournament.getTeams().size();
        int matchDuration = tournament.getGame().getAverageMatchDuration();
        int matchPauseTime = tournament.getMatchPauseTime();
        int estimatedDuration = (numberOfTeams * matchDuration) + matchPauseTime;
        tournament.setEstimatedDuration(estimatedDuration);
        tournamentRepository.update(tournament); 
        return estimatedDuration;
    }
    
}
