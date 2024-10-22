package com.playarena.app;

import java.util.Arrays;
import java.util.List;

import com.playarena.app.model.Player;
import com.playarena.app.model.Team;

public class App {
    public static void main(String[] args) {
        Team team = new Team();
        Player player1 = new Player(1L, "med");
        Player player2 = new Player(2L, "foo");

        List<Player> players = Arrays.asList(player1, player2);
        team.setPayers(players);
        team.getPlayers().forEach(Player::display);
    }
}
