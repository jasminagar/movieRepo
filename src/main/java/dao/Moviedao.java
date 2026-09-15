package dao;

import config.HibernateConfig;
import entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class Moviedao {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void saveMovie(Movie movie){
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }

    public Movie findMovieById(Long id){
        EntityManager em = emf.createEntityManager();

        try{
            return em.find(Movie.class, id);
        } finally {
            em.close();
        }
    }

    public List<Movie> findAllMovies(){
        EntityManager em = emf.createEntityManager();

        try{
            return em.createQuery("select m from Movie m", Movie.class)
                    .getResultList();
        }finally {
            em.close();
        }
    }

    public void updateMovie(Long id, String newTitle, LocalDate newReleaseDate, double newVoteAverage, double newPopularity){
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            Movie movie = em.find(Movie.class, id);

            if (movie != null) {
                movie.setTitle(newTitle);
                movie.setReleaseDate(newReleaseDate);
                movie.setVoteAverage(newVoteAverage);
                movie.setPopularity(newPopularity);
            }

            em.getTransaction().commit();
            } finally {
            em.close();
        }
    }

    public void deleteMovie(Long id){
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();

            Movie movie = em.find(Movie.class, id);

            if(movie != null){
                em.remove(movie);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
