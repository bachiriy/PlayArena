package com.playarena.app.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.playarena.app.model.Player;
import com.playarena.app.repository.PlayerRepository;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static final Logger log = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<Player> getAll() {
        List<Player> players = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Query<Player> query = session.createQuery("FROM Player", Player.class);
            players = query.getResultList();
            log.info("[+] Retrieved {} players from the database", players.size());
        } catch (HibernateException e) {
            log.error("[-] Hibernate error while retrieving all players: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[-] Unexpected error while retrieving all players: {}", e.getMessage());
        }
        return players;
    }

    @Override
    public Long add(Player player) {
        try{
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Long id = (Long) session.save(player);
            tx.commit();
            session.close();
            log.info("[+] player created with id {}", id);
            return id;
        } catch (ConstraintViolationException e) {
            log.error("[-] player maybe already exists (" + e.getMessage().toUpperCase() + ")" );
        }
        return null;
    }

    @Override
    public Optional<Player> get(Long id) {
        try{
            Session session = sessionFactory.openSession();
            Player player = session.get(Player.class, id);
            session.close();
            log.info("[+] player retrieved with id {}", id);
            return Optional.ofNullable(player);
        } catch (Exception e) {
            log.error(e.getMessage().toUpperCase());
        }
        return Optional.empty();
    }

    @Override
    public void remove(Player player) {
        try{
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(player);
            tx.commit();
            session.close();
            log.info("[+] player removed with id {}", player.getId());
        } catch (Exception e) {
            log.error(e.getMessage().toUpperCase());
        }
    }

    @Override
    public void update(Player player) {
        try{
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(player);
            tx.commit();
            session.close();
            log.info("[+] player updated with id {}", player.getId());
        } catch (Exception e) {
            log.error(e.getMessage().toUpperCase());
        }
    }
}
