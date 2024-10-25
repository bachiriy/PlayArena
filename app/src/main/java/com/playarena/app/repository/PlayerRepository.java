package com.playarena.app.repository;

import java.util.List;
import java.util.Optional;

import com.playarena.app.model.Player;

public interface PlayerRepository {
    List<Player> getAll();
    Long add(Player player);
    Optional<Player> get(Long id);
    void update(Player player);
    void remove(Player player);
}