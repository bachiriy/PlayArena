package com.playarena.app.repository;

import java.util.Optional;

import com.playarena.app.model.Player;

public interface PlayerRepository {
    Long save(Player player);
    Optional<Player> findById(Long id);
}