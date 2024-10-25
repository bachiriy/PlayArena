package com.playarena.app;


import java.util.Scanner;

import com.playarena.app.model.Player;
import com.playarena.app.service.PlayerService;
import com.playarena.app.view.Entry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {

        System.out.println("welcome to the app: \n");
        Player player1 = new Player("yakk YAK ? ");
        // Player player2 = new Player(2L, "foo");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PlayerService playerService = (PlayerService) context.getBean("playerService");

        Long id =  playerService.create(player1);


        // Scanner scan = new Scanner(System.in);
        // scan.nextLine();

        System.out.println(playerService.findById(id).getUsername());
        

    }
}
