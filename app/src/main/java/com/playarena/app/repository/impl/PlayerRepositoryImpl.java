package com.playarena.app.repository.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.playarena.app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.playarena.app.model.Player;
import com.playarena.app.repository.PlayerRepository;

public class PlayerRepositoryImpl implements PlayerRepository{
    private static final Logger log = LoggerFactory.getLogger(PlayerRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long save(Player player) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = null;
        log.info("player saved.");

         Long id = null;
         try{
             transaction = session.beginTransaction();
             id = (Long) session.save(player);
             transaction.commit();


         } catch (Exception e) {
             if (transaction != null) {
                 transaction.rollback();
             }
             e.printStackTrace();
         } finally {
             session.close();
         }
         return id;
    }

    @Override
    public Optional<Player> findById(Long id) {
        Transaction transaction = null;
        Session session = HibernateUtil.getCurrentSession();
        try {
            transaction = session.beginTransaction();
            Player player = session.find(Player.class, id);
            transaction.commit();
            return Optional.ofNullable(player);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        } finally {
            session.close();
        }
    }
}
