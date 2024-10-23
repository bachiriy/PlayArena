package com.playarena.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Player(){
        
    }

    public Player(String name){
        this.name = name;
    }

    public void setId(Long id){this.id = id;}
    public void setName(String name){this.name = name;}
    
    public Long getId(){return this.id;}
    public String getName(){return this.name;}
}
