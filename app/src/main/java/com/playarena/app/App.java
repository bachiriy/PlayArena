package com.playarena.app;


import java.util.Scanner;

import com.playarena.app.model.Player;
import com.playarena.app.service.PlayerService;
import com.playarena.app.view.Entry;

public class App {
    public static void main(String[] args) {
        System.out.println("welcome to the app: \n");
        Player player1 = new Player("anas");
        // Player player2 = new Player(2L, "foo");

        PlayerService service = new PlayerService();
        Long id =  service.create(player1);


        Scanner scan = new Scanner(System.in);
        scan.nextLine();

        System.out.println(service.findById(id).getName());
        

    }
}
