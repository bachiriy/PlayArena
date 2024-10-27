package com.playarena.app.service;

import com.playarena.app.model.Team;
import com.playarena.app.model.Player;
import com.playarena.app.repository.Repository;
import com.playarena.app.service.PlayerService;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


import java.util.Set;

public class TeamService {
    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final Repository<Team> teamRepository;


    public TeamService(Repository<Team> teamRepository) {
        this.teamRepository = teamRepository;
    }
    public Set<Team> getAllTeams() {
        return teamRepository.getAll();
    }
    public Team addTeam(Team team) {
        return teamRepository.add(team);
    }
    public void updateTeam(Team team) {
        Team foundTeam = getTeamById(team.getId());
        if (foundTeam != null) {
            team.setPlayers(foundTeam.getPlayers());
            teamRepository.update(team);
        } else log.error("Team with id {} not found", team.getId());
    }
    public void removeTeam(Team team) {
        if (team.getId() != null) {
            teamRepository.remove(team);
        } else log.error("Team with id {} not found", team.getId());
    }

    public void addNewPlayerToTeam(ApplicationContext context, long teamId, long playerId) {
        PlayerService playerService = (PlayerService) context.getBean("playerService");
        Team foundTeam = getTeamById(teamId);
   
        if (foundTeam != null) {
            Player player = playerService.findById(playerId);
            if (player != null) {
                player.setTeam(foundTeam);
                playerService.updatePlayer(player, true);
            } else log.error("[+] Player with ID {} not found", playerId);
            log.error("[-] Player assigned to Team with ID {}", teamId);
        } else log.error("[-] Team with id {} not found", teamId);
    }

    public void detachPlayerFromTeam(ApplicationContext context, long playerId){
        PlayerService playerService = (PlayerService) context.getBean("playerService");
        Player foundPlayer = playerService.findById(playerId);
        if (foundPlayer != null) {
            foundPlayer.setTeam(null);
            playerService.updatePlayer(foundPlayer, true);
            log.error("[+] Player with ID {} detached from team", playerId);
        } else log.error("[-] Player with ID {} not found", playerId);
    }

    public Team getTeamById(Long id) {
        return teamRepository.get(id).orElse(null);
    }
}
