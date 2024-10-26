package com.playarena.app.repository.impl;

import java.util.*;

import com.playarena.app.repository.Repository;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.playarena.app.model.Player;

public class PlayerRepositoryImpl implements Repository<Player> {
    private static final Logger log = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Set<Player> getAll() {
        Set<Player> players = new HashSet<Player>();
        try (Session session = sessionFactory.openSession()) {
            Query<Player> query = session.createQuery("FROM Player", Player.class);
            players.addAll(query.getResultList());
            log.info("[+] Retrieved {} players from the database", players.size());
        } catch (HibernateException e) {
            log.error("[-] Hibernate error while retrieving all players: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[-] Unexpected error while retrieving all players: {}", e.getMessage());
        }
        return players;
    }

    @Override
    public Player add(Player player) {
        try{
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Long id = (Long) session.save(player);
            player.setId(id);
            tx.commit();
            session.close();
            log.info("[+] player created with id {}", id);
            return player;
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
            log.error("[-] Failed removing player, {}", e.getMessage().toUpperCase());
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
            log.error("[-] Failed updating player, {}", e.getMessage().toUpperCase());
        }
    }
}
