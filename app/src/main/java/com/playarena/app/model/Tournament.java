package com.playarena.app.model;

import com.playarena.app.enums.Status;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import com.playarena.app.model.Game;
import com.playarena.app.model.Team;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "game_id") // Foreign key to Game
    private Game game;

    private Date startDate;
    private Date endDate;
    private int spectatorsCount;

    @OneToMany(mappedBy = "tournaments", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    private int estimatedDuration;
    private int matchPauseTime;
    private int ceremonyTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSpectatorsCount() {
        return spectatorsCount;
    }

    public void setSpectatorsCount(int spectatorsCount) {
        this.spectatorsCount = spectatorsCount;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public int getMatchPauseTime() {
        return matchPauseTime;
    }

    public void setMatchPauseTime(int matchPauseTime) {
        this.matchPauseTime = matchPauseTime;
    }

    public int getCeremonyTime() {
        return ceremonyTime;
    }

    public void setCeremonyTime(int ceremonyTime) {
        this.ceremonyTime = ceremonyTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
