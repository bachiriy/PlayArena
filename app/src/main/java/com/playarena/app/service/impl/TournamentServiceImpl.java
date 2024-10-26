package com.playarena.app.service.impl;

import com.playarena.app.model.Tournament;
import com.playarena.app.repository.Repository;
import com.playarena.app.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class TournamentServiceImpl implements TournamentService {
    private final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);
    private final Repository<Tournament> tournamentRepository;

    public TournamentServiceImpl(final Repository<Tournament> tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    public int getEstimatedTournamentDuration(){
        return 1;
    }

    public void addTournament(Tournament tournament) {
        tournamentRepository.add(tournament);
    }

    public Set<Tournament> getAllTournaments() {
        log.info("[+] Fetching all tournaments");
        return tournamentRepository.getAll();
    }

    public Tournament getTournamentById(Long id) {
        log.info("[+] Fetching tournament with id {}", id);
        return tournamentRepository.get(id).orElse(null);
    }


    public void removeTournament(Long tournamentId) {
        Tournament tournamentFound = getTournamentById(tournamentId);
        log.info("[+] Removing tournament with id {}", tournamentId);
        tournamentRepository.remove(tournamentFound);
    }

    public void updateTournament(Tournament tournament) {
        Tournament foundTournament = getTournamentById(tournament.getId());
        if (foundTournament != null) {
            log.info("[+] Updating tournament with id {}", tournament.getId());
            tournamentRepository.update(tournament);
        } else log.error("[+] No tournament found with id {}", tournament.getId());
    }
}
