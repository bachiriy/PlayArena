package com.playarena.app.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.playarena.app.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToOne()
    @JoinColumn(name="team_id", nullable=true)
    private Team team;

    public Player(){
    }

    public Player(String username) {
        this.username = username;
//        this.team = team;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    public void display(){
        System.out.println(
                "\tPlayer - ID: " + id + " | Username: " + username   + " | Team: " + (team == null ? "No Team" : team.getName()) + "\n"
        );
    }
}
