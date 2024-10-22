package com.playarena.app.model;

import com.playarena.enums.Difficulty;

public class Game {
    private Long id;
    private String name;
    private Difficulty difficulty;
    private int averageMatchDuration;

    public Game(){

    }

    public Game(Long id, String name, Difficulty difficulty, int averageMatchDuration){
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.averageMatchDuration = averageMatchDuration;
    }

    public Game(String name, Difficulty difficulty, int averageMatchDuration){
        this.name = name;
        this.difficulty = difficulty;
        this.averageMatchDuration = averageMatchDuration;
    }

    public void setId(Long id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setDifficulty(Difficulty difficulty){this.difficulty = difficulty;}
    public void setAverageMatchDuration(int averageMatchDuration){this.averageMatchDuration = averageMatchDuration;}

    public Long getId(){return this.id;}
    public String getName(){return this.name;}
    public Difficulty getDifficulty(){return this.difficulty;}
    public int getAverageMatchDuration(){return this.averageMatchDuration;}

}
