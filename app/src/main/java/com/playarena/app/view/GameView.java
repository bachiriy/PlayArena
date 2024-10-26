package com.playarena.app.view;

import com.playarena.app.enums.Difficulty;
import com.playarena.app.model.Game;
import com.playarena.app.service.GameService;
import com.playarena.app.util.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class GameView {
    private static final Logger log = LoggerFactory.getLogger(GameView.class);
    private static InputValidator input = new InputValidator();
    private static GameService gameService;

    public static void display(ApplicationContext context) {
        gameService = (GameService) context.getBean("gameService");

        System.out.println(menu());
        int choice = input.getNum("Enter your choice");

        switch (choice) {
            case 1:
                Game game = getGame();
                if (gameService.addGame(game) != null) {
                    log.info("[+] Game added successfully.");
                } else {
                    log.error("[-] Game already exists or could not be added.");
                }
                break;
            case 2:
                gameService.getAllGames().forEach(Game::display);
                break;
            case 3:
                updateGame();
                break;
            case 4:
                removeGame();
                break;
            case 5:
                log.info("Exiting Game Menu.");
                return;
            default:
                log.warn("Invalid choice, please try again.");
                display(context);
                break;
        }
    }

    private static Game getGame() {
        String name = input.getStr("Game Name");
        Difficulty difficulty = input.getEnum("Difficulty (EASY, MEDIUM, HARD)", Difficulty.class);
        int duration = input.getNum("Average Match Duration");
        return new Game(name, difficulty, duration);
    }

    private static void updateGame() {
        long gameId = input.getNum("Enter Game ID to update");
        input.cleanBuffer();
        Game game = getGame();
        game.setId(gameId);
        gameService.updateGame(game);
    }

    private static void removeGame() {
        long gameId = input.getNum("Enter Game ID to remove");
        Game game = new Game();
        game.setId(gameId);
        gameService.removeGame(game);
    }

    private static String menu() {
        return
                "\t\t-- Game Menu --\n" +
                        "\t1. Add Game\n" +
                        "\t2. View Games\n" +
                        "\t3. Update Game\n" +
                        "\t4. Remove Game\n" +
                        "\t5. Exit\n";
    }
}
