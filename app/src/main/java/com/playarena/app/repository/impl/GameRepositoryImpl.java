package com.playarena.app.repository.impl;

import com.playarena.app.model.Game;
import com.playarena.app.repository.Repository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameRepositoryImpl implements Repository<Game> {
    private static final Logger log = LoggerFactory.getLogger(GameRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    public GameRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Game> getAll() {
        Set<Game> games = new HashSet<>();
        try (Session session = sessionFactory.openSession()) {
            Query<Game> query = session.createQuery("FROM Game", Game.class);
            games.addAll(query.getResultList());
            log.info("[+] Retrieved {} games from the database", games.size());
        } catch (HibernateException e) {
            log.error("[-] Hibernate error while retrieving all games: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[-] Unexpected error while retrieving all games: {}", e.getMessage());
        }
        return games;
    }

    @Override
    public Optional<Game> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Game game = session.get(Game.class, id);
            session.close();
            if (game != null) {
                log.info("[+] Game retrieved with id {}", id);
            } else log.error("[-] Game with id not found {}", id);
            return Optional.ofNullable(game);
        } catch (Exception e) {
            log.error("[-] Failed retrieving game with id {}: {}", id, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Game add(Game game) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Long id = (Long) session.save(game);
            game.setId(id);
            tx.commit();
            log.info("[+] Game created with id {}", id);
            return game;
        } catch (ConstraintViolationException e) {
            log.error("[-] Game maybe already exists: {}", e.getMessage().toUpperCase());
        } catch (Exception e) {
            log.error("[-] Failed adding game: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void remove(Game game) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(game);
            tx.commit();
            log.info("[+] Game removed with id {}", game.getId());
        } catch (Exception e) {
            log.error("[-] Failed removing game with id {}: {}", game.getId(), e.getMessage().toUpperCase());
        }
    }

    @Override
    public void update(Game game) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(game);
            tx.commit();
            log.info("[+] Game updated with id {}", game.getId());
        } catch (Exception e) {
            log.error("[-] Failed updating game with id {}: {}", game.getId(), e.getMessage().toUpperCase());
        }
    }
}
