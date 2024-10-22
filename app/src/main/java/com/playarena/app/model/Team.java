package com.playarena.app.model;

import java.util.List;

public class Team {
    private Long id;
    private String name;
    private List<Player> players;
    private List<Tournament> tournaments;

    public void setPayers(List<Player> players){this.players = players;}
    public List<Player> getPlayers(){return this.players;}
}