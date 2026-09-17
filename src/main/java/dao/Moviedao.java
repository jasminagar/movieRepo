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

    public double getAverageRating(){
        EntityManager em = emf.createEntityManager();

        try{
            Double avg = em.createQuery("select avg(m.voteAverage) from Movie m",
                    Double.class)
                    .getSingleResult();

            return avg;
        } finally {
            em.close();
        }

    }

    public List<Movie> getTop10HighestRated() {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                            "select m from Movie m " +
                                    "order by m.voteAverage desc",
                            Movie.class
                    )
                    .setMaxResults(10)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public List<Movie> getTop10LowestRated() {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                            "select m from Movie m " +
                                    "order by m.voteAverage asc",
                            Movie.class
                    )
                    .setMaxResults(10)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public List<Movie> getTop10MostPopular() {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                            "select m from Movie m " +
                                    "order by m.popularity desc",
                            Movie.class
                    )
                    .setMaxResults(10)
                    .getResultList();

        } finally {
            em.close();
        }
    }

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

    public Movie findMovieById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Movie.class, id);
        } finally {
            em.close();
        }
    }

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