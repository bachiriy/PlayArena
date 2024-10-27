package com.playarena.app.service;

import com.playarena.app.model.Player;
import com.playarena.app.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class PlayerService {
    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final Repository<Player> playerRepository;

    public PlayerService(Repository<Player> playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player addPlayer(Player player){
        if(player.getTeam().getId() == null) return null;
        return playerRepository.add(player);
    }

    public Player findById(Long id){
        return playerRepository.get(id).orElse(null);
    }

    public Set<Player> getAllPlayers(){
        return playerRepository.getAll();
    }

    public void updatePlayer(Player player, boolean setNewTeam){
        Player foundPlayer = findById(player.getId());
        if (foundPlayer != null) {
            if (!setNewTeam) {
                player.setTeam(foundPlayer.getTeam());
            }
            playerRepository.update(player);
        } else log.error("[-] Player not found");
    }

    public void removePlayer(Long id){
        Player player = findById(id);
        if (player != null) {
            playerRepository.remove(player);
        } else log.error("[-] Player not found");
    }
}
