package com.playarena.app.dao.impl;


import com.playarena.app.dao.TournamentDao;
import com.playarena.app.enums.Difficulty;
import com.playarena.app.model.Game;
import com.playarena.app.model.Tournament;
import com.playarena.app.repository.impl.TournamentRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TournamentDaoExtension implements TournamentDao {
      private final Logger log = LoggerFactory.getLogger(TournamentDaoExtension.class);
    private final TournamentRepositoryImpl tournamentRepository;

    public TournamentDaoExtension(TournamentRepositoryImpl tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public int calculateEstimatedTournamentDuration(Tournament tournament){
        Game game = tournament.getGame();
        int gameDifficulty = (game.getDifficulty().equals(Difficulty.EASY) ? 1 : (game.getDifficulty().equals(Difficulty.MEDIUM) ? 2 : 3));

        int numberOfTeams = tournament.getTeams().size();
        int matchDuration = game.getAverageMatchDuration();
        int matchPauseTime = tournament.getMatchPauseTime();
        int ceremonyTime = tournament.getCeremonyTime();
        int estimatedDuration = (numberOfTeams * matchDuration * gameDifficulty) + (matchPauseTime + ceremonyTime);
        tournament.setEstimatedDuration(estimatedDuration);
        tournamentRepository.update(tournament); 
        return estimatedDuration;
    }
}
