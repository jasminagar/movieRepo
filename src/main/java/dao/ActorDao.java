package dao;

import config.HibernateConfig;
import entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ActorDao {

    private final EntityManagerFactory emf =
            HibernateConfig.getEntityManagerFactory();

    public Actor getActorById(Long id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Actor.class, id);
        } finally {
            em.close();
        }
    }

    public void saveActor(Actor actor) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(actor);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public List<Actor> findAllActors() {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "select a from Actor a",
                    Actor.class
            ).getResultList();

        } finally {
            em.close();
        }
    }
}