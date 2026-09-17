package dao;

import config.HibernateConfig;
import entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class Moviedao {

    EntityManagerFactory emf =
            HibernateConfig.getEntityManagerFactory();

    // CREATE – gemmer en ny film
    public void saveMovie(Movie movie) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(movie);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // READ – finder en film ud fra dens ID
    public Movie findMovieById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Movie.class, id);
        } finally {
            em.close();
        }
    }

    // READ – henter alle film fra databasen
    public List<Movie> findAllMovies() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT m FROM Movie m",
                    Movie.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    // UPDATE – ændrer filmens titel og udgivelsesdato
    public void updateMovie(
            Long id,
            String newTitle,
            LocalDate newReleaseDate
    ) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Movie movie = em.find(Movie.class, id);

            if (movie != null) {
                movie.setTitle(newTitle);
                movie.setReleaseDate(newReleaseDate);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // DELETE – sletter en film ud fra dens ID
    public void deleteMovie(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Movie movie = em.find(Movie.class, id);

            if (movie != null) {
                em.remove(movie);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}