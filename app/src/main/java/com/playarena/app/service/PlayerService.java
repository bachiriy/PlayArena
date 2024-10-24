package com.playarena.app.service;

import com.playarena.app.model.Player;
import com.playarena.app.repository.PlayerRepository;
import com.playarena.app.repository.impl.PlayerRepositoryImpl;

public class PlayerService {

    private PlayerRepositoryImpl repository;

    public PlayerService(){
        repository = new PlayerRepositoryImpl();
    }

    public Long create(Player player){
        return repository.save(player);
    }

    public Player findById(Long id){
        return repository.findById(id).orElseGet(Player::new);
    }
}
