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


    // STATISTICS – beregner den gennemsnitlige rating
    public double getAverageRating() {

        EntityManager em =
                emf.createEntityManager();

        try {
            Double average = em.createQuery(
                            "SELECT AVG(m.voteAverage) FROM Movie m",
                            Double.class
                    )
                    .getSingleResult();

            if (average == null) {
                return 0.0;
            }

            return average;

        } finally {
            em.close();
        }
    }


    // STATISTICS – henter de 10 højest vurderede film
    public List<Movie> getTop10HighestRated() {

        EntityManager em =
                emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Movie m " +
                                    "ORDER BY m.voteAverage DESC",
                            Movie.class
                    )
                    .setMaxResults(10)
                    .getResultList();

        } finally {
            em.close();
        }
    }


    // STATISTICS – henter de 10 lavest vurderede film
    public List<Movie> getTop10LowestRated() {

        EntityManager em =
                emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Movie m " +
                                    "ORDER BY m.voteAverage ASC",
                            Movie.class
                    )
                    .setMaxResults(10)
                    .getResultList();

        } finally {
            em.close();
        }
    }


    // STATISTICS – henter de 10 mest populære film
    public List<Movie> getTop10MostPopular() {

        EntityManager em =
                emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Movie m " +
                                    "ORDER BY m.popularity DESC",
                            Movie.class
                    )
                    .setMaxResults(10)
                    .getResultList();

        } finally {
            em.close();
        }
    }


    // CREATE – gemmer en ny film
    public void saveMovie(Movie movie) {

        EntityManager em =
                emf.createEntityManager();

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

        EntityManager em =
                emf.createEntityManager();

        try {
            return em.find(
                    Movie.class,
                    id
            );

        } finally {
            em.close();
        }
    }


    // READ – henter alle film fra databasen
    public List<Movie> findAllMovies() {

        EntityManager em =
                emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Movie m",
                            Movie.class
                    )
                    .getResultList();

        } finally {
            em.close();
        }
    }


    /*
     * SEARCH
     * Finder alle film, hvor titlen indeholder søgeteksten.
     * Søgningen ignorerer store og små bogstaver.
     */
    public List<Movie> searchMoviesByTitle(
            String searchString
    ) {

        EntityManager em =
                emf.createEntityManager();

        try {
            if (searchString == null
                    || searchString.isBlank()) {

                return List.of();
            }

            return em.createQuery(
                            "SELECT m FROM Movie m " +
                                    "WHERE LOWER(m.title) " +
                                    "LIKE LOWER(:searchPattern)",
                            Movie.class
                    )
                    .setParameter(
                            "searchPattern",
                            "%" + searchString.trim() + "%"
                    )
                    .getResultList();

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

        EntityManager em =
                emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Movie movie =
                    em.find(Movie.class, id);

            if (movie != null) {
                movie.setTitle(newTitle);

                movie.setReleaseDate(
                        newReleaseDate
                );
            }

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }


    // DELETE – sletter en film ud fra dens ID
    public void deleteMovie(Long id) {

        EntityManager em =
                emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Movie movie =
                    em.find(Movie.class, id);

            if (movie != null) {
                em.remove(movie);
            }

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
}