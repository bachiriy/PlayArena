package com.playarena.app.model;
import java.util.Date;
import java.util.List;

import com.playarena.enums.Status;

public class Tournament {
    private Long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private int spectatorsCount;
    private List<Team> teams;
    private int estimatedDuration;
    private int matchPauseTime;
    private int ceremonyTime;
    private Status status;
    private Game game;
}
