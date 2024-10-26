package com.playarena.app;

import com.playarena.app.view.Entry;

import com.playarena.app.service.PlayerService;
import com.playarena.app.service.TeamService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        PlayerService playerService = (PlayerService) context.getBean("playerService");
//        TeamService teamService = (TeamService) context.getBean("teamService");
        Entry entry = new Entry(context);
        entry.display();
    }
}
