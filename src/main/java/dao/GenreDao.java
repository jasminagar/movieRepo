package dao;

import config.HibernateConfig;
import entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GenreDao {

    private final EntityManagerFactory emf =
            HibernateConfig.getEntityManagerFactory();

    public Genre getGenreById(Long id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Genre.class, id);
        } finally {
            em.close();
        }
    }

    public void saveGenre(Genre genre) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(genre);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public List<Genre> findAllGenres() {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "select g from Genre g",
                    Genre.class
            ).getResultList();

        } finally {
            em.close();
        }
    }
}