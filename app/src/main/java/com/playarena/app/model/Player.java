package com.playarena.app.model;

public class Player {
    private Long id;
    private String name;

    public Player(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(Long id){this.id = id;}
    public void setName(String name){this.name = name;}
    
    public Long getId(){return this.id;}
    public String getName(){return this.name;}

    public String print(){
        return "\nID: " + this.id + "\n" +
        "Name: " + this.name;
    }
    public void display(){
        System.out.println(print());
    }
}
