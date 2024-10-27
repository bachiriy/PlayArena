package com.playarena.app.service.impl;

import com.playarena.app.dao.TournamentDao;
import com.playarena.app.model.Team;
import com.playarena.app.model.Tournament;
import com.playarena.app.repository.Repository;
import com.playarena.app.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class MainTest {

    @Mock
    private Repository<Tournament> tournamentRepository;

    @Mock
    private TournamentDao tournamentDao;

    @Mock
    private ApplicationContext context;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        when(tournamentRepository.get(1L)).thenReturn(Optional.of(tournament));

        Tournament result = tournamentService.getTournamentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tournamentRepository, times(1)).get(1L);
    }

    @Test
    void testAddTournament() {
        Tournament tournament = new Tournament();
        tournamentService.addTournament(tournament);

        verify(tournamentRepository, times(1)).add(tournament);
    }

    @Test
    void testRemoveTournament() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        when(tournamentRepository.get(1L)).thenReturn(Optional.of(tournament));

        tournamentService.removeTournament(1L);

        verify(tournamentRepository, times(1)).remove(tournament);
    }

    @Test
    void testAssignToTeam() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        when(tournamentRepository.get(1L)).thenReturn(Optional.of(tournament));
        when(context.getBean("teamService")).thenReturn(teamService);

        Team team = new Team();
        team.setId(1L);
        when(teamService.getTeamById(1L)).thenReturn(team);

        ArrayList<Long> teamIds = new ArrayList<>();
        teamIds.add(1L);

        tournamentService.assignToTeam(context, 1L, teamIds);

        assertTrue(tournament.getTeams().contains(team));
        verify(tournamentRepository, times(1)).update(tournament);
    }

    @Test
    void testGetEstimatedTournamentDuration() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        when(tournamentRepository.get(1L)).thenReturn(Optional.of(tournament));
        when(tournamentDao.calculateEstimatedTournamentDuration(tournament)).thenReturn(10);

        tournamentService.getEstimatedTournamentDuration(1L);

        verify(tournamentDao, times(1)).calculateEstimatedTournamentDuration(tournament);
    }
}
