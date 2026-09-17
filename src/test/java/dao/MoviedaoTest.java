package dao;

import config.HibernateConfig;
import entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MoviedaoTest {

    private static EntityManagerFactory emf;
    private Moviedao moviedao;

    @BeforeAll
    static void setUpEmf() {

        emf = HibernateConfig.getEntityManagerFactory();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.createQuery(
                    "DELETE FROM Movie"
            ).executeUpdate();

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @BeforeEach
    void setUpMovieDao() {
        moviedao = new Moviedao();
    }

    @AfterAll
    static void closeEmf() {
        emf.close();
    }

    @Test
    void saveMovieShouldSaveMovie() {

        Movie movie = new Movie();

        movie.setId(999991L);
        movie.setTitle("Test Movie");
        movie.setReleaseDate(
                LocalDate.of(2025, 1, 1)
        );
        movie.setVoteAverage(8.5);
        movie.setPopularity(100.0);

        moviedao.saveMovie(movie);

        Movie savedMovie =
                moviedao.findMovieById(999991L);

        assertNotNull(savedMovie);

        assertEquals(
                Long.valueOf(999991L),
                savedMovie.getId()
        );

        assertEquals(
                "Test Movie",
                savedMovie.getTitle()
        );

        assertEquals(
                LocalDate.of(2025, 1, 1),
                savedMovie.getReleaseDate()
        );

        assertEquals(
                8.5,
                savedMovie.getVoteAverage()
        );

        assertEquals(
                100.0,
                savedMovie.getPopularity()
        );

        moviedao.deleteMovie(999991L);
    }

    @Test
    void findMovieByIdShouldReturnMovie() {

        Movie movie = new Movie();

        movie.setId(999992L);
        movie.setTitle("Find Test");
        movie.setReleaseDate(
                LocalDate.of(2024, 5, 10)
        );
        movie.setVoteAverage(7.5);
        movie.setPopularity(80.0);

        moviedao.saveMovie(movie);

        Movie foundMovie =
                moviedao.findMovieById(999992L);

        assertNotNull(foundMovie);

        assertEquals(
                Long.valueOf(999992L),
                foundMovie.getId()
        );

        assertEquals(
                "Find Test",
                foundMovie.getTitle()
        );

        moviedao.deleteMovie(999992L);
    }

    @Test
    void findAllMoviesShouldReturnMovies() {

        Movie movie1 = new Movie();

        movie1.setId(999993L);
        movie1.setTitle("Movie One");
        movie1.setReleaseDate(
                LocalDate.of(2024, 1, 1)
        );
        movie1.setVoteAverage(7.0);
        movie1.setPopularity(50.0);

        Movie movie2 = new Movie();

        movie2.setId(999994L);
        movie2.setTitle("Movie Two");
        movie2.setReleaseDate(
                LocalDate.of(2024, 2, 1)
        );
        movie2.setVoteAverage(8.0);
        movie2.setPopularity(60.0);

        moviedao.saveMovie(movie1);
        moviedao.saveMovie(movie2);

        List<Movie> movies =
                moviedao.findAllMovies();

        assertNotNull(movies);
        assertTrue(movies.size() >= 2);

        moviedao.deleteMovie(999993L);
        moviedao.deleteMovie(999994L);
    }

    @Test
    void updateMovieShouldUpdateTitleAndReleaseDate() {

        Movie movie = new Movie();

        movie.setId(999995L);
        movie.setTitle("Old Title");
        movie.setReleaseDate(
                LocalDate.of(2023, 1, 1)
        );
        movie.setVoteAverage(6.0);
        movie.setPopularity(40.0);

        moviedao.saveMovie(movie);

        moviedao.updateMovie(
                999995L,
                "New Title",
                LocalDate.of(2025, 1, 1)
        );

        Movie updatedMovie =
                moviedao.findMovieById(999995L);

        assertNotNull(updatedMovie);

        assertEquals(
                "New Title",
                updatedMovie.getTitle()
        );

        assertEquals(
                LocalDate.of(2025, 1, 1),
                updatedMovie.getReleaseDate()
        );

        // Disse værdier skal forblive uændrede
        assertEquals(
                6.0,
                updatedMovie.getVoteAverage()
        );

        assertEquals(
                40.0,
                updatedMovie.getPopularity()
        );

        moviedao.deleteMovie(999995L);
    }

    @Test
    void deleteMovieShouldDeleteMovie() {

        Movie movie = new Movie();

        movie.setId(999996L);
        movie.setTitle("Delete Test");
        movie.setReleaseDate(
                LocalDate.of(2022, 1, 1)
        );
        movie.setVoteAverage(5.0);
        movie.setPopularity(20.0);

        moviedao.saveMovie(movie);

        Movie savedMovie =
                moviedao.findMovieById(999996L);

        assertNotNull(savedMovie);

        moviedao.deleteMovie(999996L);

        Movie deletedMovie =
                moviedao.findMovieById(999996L);

        assertNull(deletedMovie);
    }
}