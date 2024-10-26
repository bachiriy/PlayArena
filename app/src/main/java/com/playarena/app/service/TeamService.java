package com.playarena.app.service;

import com.playarena.app.model.Team;
import com.playarena.app.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public Team getTeamById(Long id) {
        return teamRepository.get(id).orElse(null);
    }
}
