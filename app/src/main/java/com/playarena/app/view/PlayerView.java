package com.playarena.app.view;

import com.playarena.app.model.Player;
import com.playarena.app.model.Team;
import com.playarena.app.service.PlayerService;
import com.playarena.app.util.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class PlayerView {
    private static final Logger log = LoggerFactory.getLogger(PlayerView.class);
    private static InputValidator input = new InputValidator();
    private static PlayerService playerService;

    static void display(ApplicationContext context){
        playerService = (PlayerService) context.getBean("playerService");

        System.out.println(menu());
        int choice = input.getNum("Enter your choice");
        switch (choice){
            case 1:
                Player player = playerService.addPlayer(getPlayer());
                if (player == null)  log.error("[-] Player already exists, or must be in a valid Team.");
                break;
            case 2:
                playerService.getAllPlayers().forEach(Player::display);
                break;
            case 3:
                updatePlayer();
                break;
            case 4:
                removePlayer();
            break;
            case 5:
                break;
            default:
                log.error("[-] Invalid choice.");
                display(context);
                break;
        }
    }

    private static Player getPlayer(){
        String username = input.getStr("Player Username");
        int teamId = input.getNum("Team ID");
        Player player = new Player(username);
        Team team = new Team();
        team.setId((long) teamId);
        player.setTeam(team);
        return player;
    }

    private static void removePlayer() {
        long playerId = input.getNum("Enter Player ID to remove");
        playerService.removePlayer(playerId);
    }

    private static void updatePlayer(){
        long playerId = input.getNum("Enter Player ID to update");
        String playerNewName = input.getStr("Enter Player New Name");
        Player player = new Player(playerNewName);
        player.setId(playerId);
        playerService.updatePlayer(player);
    }


    private static String menu(){
        return
                "\t\t-- Player Menu --\n"+
                        "\t1. Add Player\n"+
                        "\t2. View Players\n"+
                        "\t3. Update Player\n"+
                        "\t4. Remove Player\n" +
                        "\t5. Exit\n";
    }
}
