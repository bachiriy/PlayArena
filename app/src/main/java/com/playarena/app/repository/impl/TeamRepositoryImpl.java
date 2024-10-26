package com.playarena.app.repository.impl;

import com.playarena.app.model.Team;
import com.playarena.app.repository.Repository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TeamRepositoryImpl implements Repository<Team> {
    private static final Logger log = LoggerFactory.getLogger(TeamRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    public TeamRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Team> getAll() {
        Set<Team> teams = new HashSet<Team>();
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery("FROM Team ", Team.class);
            teams.addAll(query.getResultList());
            log.info("[+] Retrieved {} teams from the database", teams.size());
        } catch (HibernateException e) {
            log.error("[-] Hibernate error while retrieving all teams: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[-] Unexpected error while retrieving all teams: {}", e.getMessage());
        }
        return teams;
    }

    @Override
    public Optional<Team> get(Long id) {
        try{
            Session session = sessionFactory.openSession();
            Team team = session.get(Team.class, id);
            session.close();
            log.info("[+] team retrieved with id {}", id);
            return Optional.ofNullable(team);
        } catch (Exception e) {
            log.error(e.getMessage().toUpperCase());
        }
        return Optional.empty();
    }

    @Override
    public Team add(Team team) {
        Transaction tx = null;
        try{
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Long id = (Long) session.save(team);
            team.setId(id);
            tx.commit();
            session.close();
            log.info("[+] team created with id");
            return team;
        } catch (ConstraintViolationException e) {
            assert tx != null;
            tx.rollback();
            log.error("[-] team may already exist ({})", e.getMessage().toUpperCase());
        }
        return null;
    }

    @Override
    public void remove(Team team) {
        try{
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Team foundTeam = session.find(Team.class, team.getId());
            if (foundTeam != null) {
                session.delete(foundTeam);
                tx.commit();
                session.close();
                log.info("[+] team removed with id {}", team.getId());
            }
        } catch (Exception e) {
            log.error("Failed removing team wit ID {}, {}", team.getId(), e.getMessage().toUpperCase());
        }
    }

    @Override
    public void update(Team team) {
        try{
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(team);
            tx.commit();
            session.close();
            log.info("[+] team updated with id {}", team.getId());
        } catch (Exception e) {
            log.error(e.getMessage().toUpperCase());
        }
    }
}
