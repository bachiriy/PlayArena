package com.playarena.app.model;

import com.playarena.app.enums.Difficulty;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
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

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getAverageMatchDuration() {
        return averageMatchDuration;
    }

    public void setAverageMatchDuration(int averageMatchDuration) {
        this.averageMatchDuration = averageMatchDuration;
    }

    public void display(){
        System.out.println(
                "Game - ID: " + id + " | Name: " + name + " | Difficulty: " + difficulty.toString() + " | AverageMatchDuration: " + averageMatchDuration + "\n"
        );
    }
}
