package com.playarena.app.model;

import com.playarena.app.enums.Status;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.playarena.app.model.Game;
import com.playarena.app.model.Team;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    private Date startDate;
    private Date endDate;
    private int spectatorsCount;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Tournament_Team",
            joinColumns = { @JoinColumn(name = "tournament_id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") }
    )
    private Set<Team> teams;

    private int estimatedDuration;
    private int matchPauseTime;
    private int ceremonyTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Tournament(){

    }

    public Tournament(String title, Date startDate, Date endDate, int spectatorsCount, int estimatedDuration, int matchPauseTime, int ceremonyTime, Status status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.spectatorsCount = spectatorsCount;
        this.estimatedDuration = estimatedDuration;
        this.matchPauseTime = matchPauseTime;
        this.ceremonyTime = ceremonyTime;
        this.status = status;
    }

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

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
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

    public void display(){
        System.out.println("\n\t\tGame: " + game);
        System.out.println(
                "\n\tTournament - ID: " + id + " | Title: " + title + " | Start: " + startDate + " | End: " + endDate + " | Estimated Duration: " + estimatedDuration + " | Match Pause Time: " + matchPauseTime + " | Ceremony Time: " + ceremonyTime + " | Status: " + status + "\n"
        );
        if (teams != null){
            teams.forEach(Team::display);
        }
    }
}
