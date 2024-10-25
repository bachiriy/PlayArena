package com.playarena.app.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.playarena.app.model.Team;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToMany(mappedBy = "players", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Team> teams = new HashSet<>();

    public Player(){
    }

    public Player(String username){
        this.username = username;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
