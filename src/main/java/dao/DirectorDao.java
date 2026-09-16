package dao;

import config.HibernateConfig;
import entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DirectorDao {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public Director getDirectorById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Director.class, id);
        } finally {
            em.close();
        }
    }

    public void saveDirector(Director director) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(director);

            em.getTransaction().commit();

        } catch (Exception e) {

            e.printStackTrace();

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public List<Director> findAllDirectors(){
        EntityManager em = emf.createEntityManager();

        try{
            return em.createQuery("select d from Director d", Director.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}

