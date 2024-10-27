package com.playarena.app.view;

import com.playarena.app.model.Team;
import com.playarena.app.service.TeamService;
import com.playarena.app.util.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class TeamView {
    private static final Logger log = LoggerFactory.getLogger(TeamView.class);
    private static InputValidator input = new InputValidator();
    private static TeamService teamService;

    public static void display(ApplicationContext context){
        teamService = (TeamService) context.getBean("teamService");

        while (true) {
            System.out.println(menu());
            int choice = input.getNum("Enter your choice");
            input.cleanBuffer();

            switch (choice) {
                case 1:
                    Team team = teamService.addTeam(getTeam());
                    if (team != null) team.display(); else log.error("Team already exists.");
                    break;
                case 2:
                    teamService.getAllTeams().forEach(Team::display);
                    break;
                case 3:
                    updateTeam();
                    break;
                case 4:
                    removeTeam();
                    break;
                case 5:
                    assignPlayerToTeam(context);
                    break;
                case 6:
                    detachPlayerFromTeam(context);
                    break;
                case 7:
                    log.info("Exiting Team Menu.");
                    return;
                default:
                    log.warn("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static Team getTeam() {
        String teamName = input.getStr("Team Name");
        Team team = new Team();
        team.setName(teamName);
        return team;
    }

    private static void updateTeam() {
        long teamId = input.getNum("Enter Team ID to update");
        input.cleanBuffer();
        String newName = input.getStr("Enter new Team Name");
        Team team = new Team(newName);
        team.setId(teamId);
        teamService.updateTeam(team);
    }

    private static void removeTeam() {
        long teamId = input.getNum("Enter Team ID to remove");
        Team team = new Team();
        team.setId(teamId);
        teamService.removeTeam(team);
    }

    private static void assignPlayerToTeam(ApplicationContext context){
        long teamId = input.getNum("Enter team ID : ");
        long playerId = input.getNum("Enter player ID : ");
        teamService.addNewPlayerToTeam(context, teamId, playerId);
    }

    private static void detachPlayerFromTeam(ApplicationContext context){
        long playerId = input.getNum("Enter player ID : ");
        teamService.detachPlayerFromTeam(context, playerId);
    }

    private static String menu() {
        return
                "\t\t-- Team Menu --\n" +
                        "\t1. Add Team\n" +
                        "\t2. View Teams\n" +
                        "\t3. Update Team\n" +
                        "\t4. Remove Team\n" +
                        "\t5. Assign player to team\n" +
                        "\t6 Detach player from team\n" +
                        "\t7. Exit\n";
    }
}
