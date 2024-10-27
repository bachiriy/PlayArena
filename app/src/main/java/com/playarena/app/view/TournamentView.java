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

import java.util.ArrayList;
import java.util.Date;

public class TournamentView {
    private static final Logger log = LoggerFactory.getLogger(TournamentView.class);
    private static InputValidator input = new InputValidator();
    private static TournamentServiceImpl tournamentService;

    public static void display(ApplicationContext context) {
        tournamentService = (TournamentServiceImpl) context.getBean("tournamentService");

        System.out.println(menu());
        int choice = input.getNum("Enter your choice");
        input.cleanBuffer();
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
                assignToTeam(context);
                break;
                case 6:
                detachFromTeam(context);
                break;
            case 7:
                calculateEstimatedDuration();
                break;    
            case 8:
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
        // int estimatedDuration = input.getNum("Estimated Duration (in minutes)");
        int matchPauseTime = input.getNum("Match Pause Time (in minutes)");
        int ceremonyTime = input.getNum("Ceremony Time (in minutes)");
        input.cleanBuffer();
        Status status =  input.getEnum("Status (SCHEDULED, IN_PROGRESS, COMPLETED, CANCELED)", Status.class);

        Tournament tournament = new Tournament(title, startDate, endDate, spectatorsCount, matchPauseTime, ceremonyTime, status);
        GameService gameService = (GameService) context.getBean("gameService");
        boolean assignToGame = input.getYesNo("Do you want to assign this tournament to and existing game? (Y/N: adding new game) : ");
        if (assignToGame) {
            gameService.getAllGames().forEach(Game::display);
            long gameId = input.getNum("Game ID to assign to this tournament: ");
            
            while (true) {
                Game foundGmae = gameService.getGameById(gameId);
                if (foundGmae != null) {
                    tournament.setGame(foundGmae);
                    break;
                } else {
                    log.error("No game found with the ID {} try again. ", gameId);
                    gameId = input.getNum("Try agin, Game ID to assign to this tournament: ");
                }
            }
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
            // int estimatedDuration = input.getNum("Estimated Duration (in minutes)");
            int matchPauseTime = input.getNum("Match Pause Time (in minutes)");
            int ceremonyTime = input.getNum("Ceremony Time (in minutes)");
            Status status =  input.getEnum("Status (SCHEDULED, IN_PROGRESS, COMPLETED, CANCELED)", Status.class);
            tournament.setSpectatorsCount(spectatorsCount);
            // tournament.setEstimatedDuration(estimatedDuration);
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

    private static void assignToTeam(ApplicationContext context){
        ArrayList<Long> teamIds = new ArrayList<>();
        long tournamentId = input.getNum("Enter Tournament ID : ");
        long firstTeamId = input.getNum("Enter Team ID to assign to the Tournament: ");
        teamIds.add(firstTeamId);
        input.cleanBuffer();
        while (input.getYesNo("Add other Team to the Tournament? (Y/N) :")) {
            long otherTeamId = input.getNum("Enter Team ID to assign to the Tournament: ");
            teamIds.add(otherTeamId);
            input.cleanBuffer();
        }
        tournamentService.assignToTeam(context, tournamentId, teamIds);
    }
    private static void detachFromTeam(ApplicationContext context){
        long tournamentId = input.getNum("Enter Tournament ID : ");
        long teamId = input.getNum("Enter Team ID to detach from this Tournament: ");
        tournamentService.detachFromTeam(context, tournamentId, teamId);
    }

    private static void calculateEstimatedDuration(){
        long tournamentId = input.getNum("Enter tournament ID to calculate estimated duration : ");
        tournamentService.getEstimatedTournamentDuration(tournamentId);
    }

    private static String menu() {
        return
                "\t\t-- Tournament Menu --\n" +
                        "\t1. Add Tournament\n" +
                        "\t2. View Tournaments\n" +
                        "\t3. Update Tournament\n" +
                        "\t4. Remove Tournament\n" +
                        "\t5. Assign new Teams to Tournament\n" +
                        "\t6. Detach Tournament from team\n" +
                        "\t7. Calculate Estimated Duration of Tournament\n" +
                        "\t8. Exit\n";
    }
}
