package com.playarena.app.model;

import javax.persistence.*;
import java.util.Set;
import com.playarena.app.model.Tournament;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "teams")
public class Team {
    private static final Logger log = LoggerFactory.getLogger(Team.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Set<Player> players;

    @ManyToMany(mappedBy = "teams")
    private Set<Tournament> tournaments;

    // Getters and setters
    public Team() {

    }

    public Team(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void display(){
        System.out.println("\n\tTeam - ID: " + id + " | Name: " + name + " | Players("+ (players == null ? 0 : players.size()) +") -> \n");
        if (players != null) players.forEach(Player::display);
    }
}
