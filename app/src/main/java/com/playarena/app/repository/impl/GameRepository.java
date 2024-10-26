package com.playarena.app.repository.impl;

import com.playarena.app.model.Game;
import com.playarena.app.repository.Repository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class GameRepository implements Repository<Game> {
    @Override
    public Set<Game> getAll() {
        return Collections.emptySet();
    }

    @Override
    public Optional<Game> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Game add(Game game) {
        return null;
    }

    @Override
    public void remove(Game game) {

    }

    @Override
    public void update(Game game) {

    }
}
