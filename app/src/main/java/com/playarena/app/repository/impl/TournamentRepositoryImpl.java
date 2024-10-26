package com.playarena.app.repository.impl;

import com.playarena.app.model.Tournament;
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

public class TournamentRepositoryImpl implements Repository<Tournament> {
    private final Logger log = LoggerFactory.getLogger(TournamentRepositoryImpl.class);
    private final SessionFactory sessionFactory;

    public TournamentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Tournament> getAll() {
        Set<Tournament> tournaments = new HashSet<>();
        try (Session session = sessionFactory.openSession()) {
            Query<Tournament> query = session.createQuery("FROM Tournament", Tournament.class);
            tournaments.addAll(query.getResultList());
            log.info("[+] Retrieved {} tournaments from the database", tournaments.size());
        } catch (HibernateException e) {
            log.error("[-] Hibernate error while retrieving all tournaments: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[-] Unexpected error while retrieving all tournaments: {}", e.getMessage());
        }
        return tournaments;
    }

    @Override
    public Optional<Tournament> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Tournament tournament = session.get(Tournament.class, id);
            log.info("[+] Tournament retrieved with id {}", id);
            return Optional.ofNullable(tournament);
        } catch (Exception e) {
            log.error("[-] Error retrieving tournament with id {}: {}", id, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Tournament add(Tournament tournament) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Long id = (Long) session.save(tournament);
            tournament.setId(id);
            tx.commit();
            log.info("[+] Tournament created with id {}", id);
            return tournament;
        } catch (ConstraintViolationException e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("[-] Tournament may already exist: {}", e.getMessage());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("[-] Error adding tournament: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void remove(Tournament tournament) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Tournament foundTournament = session.find(Tournament.class, tournament.getId());
            if (foundTournament != null) {
                session.delete(foundTournament);
                tx.commit();
                log.info("[+] Tournament removed with id {}", tournament.getId());
            } else {
                log.warn("[-] Tournament with id {} not found", tournament.getId());
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("[-] Failed removing tournament with ID {}: {}", tournament.getId(), e.getMessage());
        }
    }

    @Override
    public void update(Tournament tournament) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(tournament);
            tx.commit();
            log.info("[+] Tournament updated with id {}", tournament.getId());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("[-] Error updating tournament with id {}: {}", tournament.getId(), e.getMessage());
        }
    }
}
