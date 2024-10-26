package com.playarena.app.view;

import com.playarena.app.enums.Difficulty;
import com.playarena.app.enums.Status;
import com.playarena.app.model.Game;
import com.playarena.app.model.Tournament;
import com.playarena.app.service.GameService;
import com.playarena.app.service.impl.TournamentServiceImpl;
import com.playarena.app.util.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class TournamentView {
    private static final Logger log = LoggerFactory.getLogger(TournamentView.class);
    private static InputValidator input = new InputValidator();
    private static TournamentServiceImpl tournamentService;

    public static void display(ApplicationContext context) {
        tournamentService = (TournamentServiceImpl) context.getBean("tournamentService");

        System.out.println(menu());
        int choice = input.getNum("Enter your choice");
        switch (choice) {
            case 1:
                addTournament(context);
                break;
            case 2:
                tournamentService.getAllTournaments().forEach(Tournament::display);
                break;
            case 3:
                updateTournament();
                break;
            case 4:
                removeTournament();
                break;
            case 5:
                log.info("Exiting tournament menu ...");
                return;
            default:
                log.error("[-] Invalid choice.");
                display(context);
                break;
        }

        display(context);
    }

    private static void addTournament(ApplicationContext context) {
        String title = input.getStr("Tournament Title");
        Date startDate = input.getDate("Start Date (DD/MM/YYYY)", false);
        Date endDate = null;
        while (true){
            endDate = input.getDate("End Date (DD/MM/YYYY)", false);
            if (endDate.after(startDate)) {
                break;
            } else log.warn("End date cannot be before start date.");
        }
        int spectatorsCount = input.getNum("Spectators Count");
        int estimatedDuration = input.getNum("Estimated Duration (in minutes)");
        int matchPauseTime = input.getNum("Match Pause Time (in minutes)");
        int ceremonyTime = input.getNum("Ceremony Time (in minutes)");
        Status status =  input.getEnum("Status (SCHEDULED, IN_PROGRESS, COMPLETED, CANCELED)", Status.class);

        Tournament tournament = new Tournament(title, startDate, endDate, spectatorsCount, estimatedDuration, matchPauseTime, ceremonyTime, status);
        GameService gameService = (GameService) context.getBean("gameService");
        boolean assignToGame = input.getYesNo("Do you want to assign this tournament to and existing game? (Y/N: adding new game) : ");
        if (assignToGame) {
            gameService.getAllGames().forEach(Game::display);
            long gameId = input.getNum("Game ID to passing to this tournament: ");
            while (gameService.getGameById(gameId) == null) {
                log.error("No game found with the ID {} try again. ", gameId);
                gameId = input.getNum("Game ID to passing to this tournament: ");
            }
            Game game = new Game();
            game.setId(gameId);
            tournament.setGame(game);
        } else {
            String name = input.getStr("Game Name");
            Difficulty difficulty = input.getEnum("Difficulty (EASY, MEDIUM, HARD)", Difficulty.class);
            int duration = input.getNum("Average Match Duration");
            Game newGame =  new Game(name, difficulty, duration);
            tournament.setGame(newGame);
        }
        tournamentService.addTournament(tournament);
    }

    private static void updateTournament() {
        long tournamentId = input.getNum("Enter Tournament ID to update");
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        if (tournament == null) {
            log.error("[-] Tournament with ID {} does not exist.", tournamentId);
            return;
        }

        String newTitle = input.getStrEvenEmpty("Enter New Tournament Title (leave empty for no change)");
        if (!newTitle.isEmpty()) {
            tournament.setTitle(newTitle);
        }

        boolean updateOtherProps = input.getYesNo("Do You Want To Update Other Properties Of This Tournament? (Y/N) :");
        if (updateOtherProps) {
            Date startDate = input.getDate("Start Date (DD/MM/YYYY)", false);
            Date endDate = null;
            while (true){
                endDate = input.getDate("End Date (DD/MM/YYYY)", false);
                if (endDate.after(startDate)) {
                    break;
                } else log.warn("End date cannot be before start date.");
            }
            int spectatorsCount = input.getNum("Spectators Count");
            int estimatedDuration = input.getNum("Estimated Duration (in minutes)");
            int matchPauseTime = input.getNum("Match Pause Time (in minutes)");
            int ceremonyTime = input.getNum("Ceremony Time (in minutes)");
            Status status =  input.getEnum("Status (SCHEDULED, IN_PROGRESS, COMPLETED, CANCELED)", Status.class);
            tournament.setSpectatorsCount(spectatorsCount);
            tournament.setEstimatedDuration(estimatedDuration);
            tournament.setMatchPauseTime(matchPauseTime);
            tournament.setCeremonyTime(ceremonyTime);
            tournament.setStatus(status);
        }
        tournamentService.updateTournament(tournament);
        log.info("[+] Tournament with ID {} updated successfully.", tournamentId);
    }

    private static void removeTournament() {
        long tournamentId = input.getNum("Enter Tournament ID to remove");
        tournamentService.removeTournament(tournamentId);
        log.info("[+] Tournament with ID {} removed successfully.", tournamentId);
    }

    private static String menu() {
        return
                "\t\t-- Tournament Menu --\n" +
                        "\t1. Add Tournament\n" +
                        "\t2. View Tournaments\n" +
                        "\t3. Update Tournament\n" +
                        "\t4. Remove Tournament\n" +
                        "\t5. Exit\n";
    }
}
