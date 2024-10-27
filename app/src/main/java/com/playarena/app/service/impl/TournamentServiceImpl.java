package com.playarena.app.service.impl;

import com.playarena.app.dao.TournamentDao;
import com.playarena.app.model.Team;
import com.playarena.app.model.Tournament;
import com.playarena.app.repository.Repository;
import com.playarena.app.service.TeamService;
import com.playarena.app.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TournamentServiceImpl implements TournamentService {
    private final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);
    private final Repository<Tournament> tournamentRepository;
    private TournamentDao tournamentDao;

    public TournamentServiceImpl(final Repository<Tournament> tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    public void setTournamentDao(TournamentDao tournamentDao){
        this.tournamentDao = tournamentDao;
    }


    public void getEstimatedTournamentDuration(long tournamentId){
        Tournament tournamentFound = getTournamentById(tournamentId);
        if (tournamentFound != null) {
            int estimatedDuration = tournamentDao.calculateEstimatedTournamentDuration(tournamentFound);
            log.info("[+] Estimated Duration for this Tournament is `{}`.", estimatedDuration);
        } else log.error("[+] No tournament found with id {}", tournamentId);
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

    public void assignToTeam(ApplicationContext context, long tournamentId, ArrayList<Long> teamIds){
        Tournament tournamentFound = getTournamentById(tournamentId);
        if (tournamentFound != null) {
            Set<Team> teamsSet = new HashSet<>();
            TeamService teamService = (TeamService) context.getBean("teamService");

            for(int i = 0; i < teamIds.size(); i++){
                Team teamFound = teamService.getTeamById(teamIds.get(i));
                if (teamFound != null) {
                    teamsSet.add(teamFound);
                } else log.error("[-] Team with ID {} not found.", teamIds.get(i));
            }
            tournamentFound.setTeams(teamsSet);
            tournamentRepository.update(tournamentFound);
        }  else log.error("[+] No tournament found with id {}", tournamentId);
    }

    public void detachFromTeam(ApplicationContext context, long tournamentId, long teamId){
        Tournament tournamentFound = getTournamentById(tournamentId);
        if (tournamentFound != null) {
            TeamService teamService = (TeamService) context.getBean("teamService");
            Team teamFound = teamService.getTeamById(teamId);
            if (teamFound != null) {
                tournamentFound.getTeams().remove(teamFound);
                tournamentRepository.update(tournamentFound);
                teamService.updateTeam(teamFound);
            } else log.error("[-] Team with ID {} not found.", teamId);
        }  else log.error("[+] No tournament found with id {}", tournamentId);
    }
}
