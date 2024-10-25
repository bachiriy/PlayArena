package com.playarena.app.service;

import com.playarena.app.model.Player;
import com.playarena.app.repository.PlayerRepository;

import java.util.List;

public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Long create(Player player){
        return playerRepository.add(player);
    }

    public Player findById(Long id){
        return playerRepository.get(id).orElseGet(Player::new);
    }

    public List<Player> findAll(){
        return null;
    }
}
