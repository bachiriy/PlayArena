package com.playarena.app.service;

import com.playarena.app.model.Game;
import com.playarena.app.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;

public class GameService {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    private final Repository<Game> gameRepository;

    public GameService(Repository<Game> gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Set<Game> getAllGames() {
        return gameRepository.getAll();
    }

    public Game addGame(Game game) {
        return gameRepository.add(game);
    }

    public void updateGame(Game game) {
        Game foundGame = getGameById(game.getId());
        if (foundGame != null) {
            gameRepository.update(game);
        } else {
            log.error("Game with id {} not found", game.getId());
        }
    }

    public void removeGame(Game game) {
        if (game.getId() != null) {
            gameRepository.remove(game);
        } else {
            log.error("Game with id {} not found", game.getId());
        }
    }

    public Game getGameById(Long id) {
        return gameRepository.get(id).orElse(null);
    }
}
