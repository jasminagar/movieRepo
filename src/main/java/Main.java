import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import config.HibernateConfig;
import dao.Moviedao;
import dto.MovieDTO;
import dto.MovieResponseDTO;
import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import service.ApiReader;
import service.MovieService;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MovieService movieService = new MovieService();

        // movieService.testMovieDetails(Long.valueOf(1503074));


        /*
         * 1. Hent film fra TMDb og gem dem i databasen
         */

        movieService.fetchAndSaveAllDanishMovies();


        /*
         * 2. Hent alle film fra databasen
         */

        List<Movie> movies =
                movieService.getAllMoviesFromDatabase();

        System.out.println("===== MOVIES =====");

        for (Movie movie : movies) {

            System.out.println();

            System.out.println(
                    "Movie: " + movie.getTitle()
            );


            /*
             * Actors
             */

            System.out.println("Actors:");

            for (Actor actor : movie.getActors()) {

                System.out.println(
                        " - " + actor.getName()
                );
            }


            /*
             * Directors
             */

            System.out.println("Director:");

            if (movie.getDirectors() != null
                    && !movie.getDirectors().isEmpty()) {

                for (Director director
                        : movie.getDirectors()) {

                    System.out.println(
                            " - " + director.getName()
                    );
                }

                if (movie.getGenres() != null && !movie.getGenres().isEmpty()){
                    for (Genre genre : movie.getGenres()){
                        System.out.println(" - " + genre.getName());
                    }
                }

            } else {
                System.out.println(
                        " - No director found"
                );
            }
        }


//        EntityManagerFactory emf =
//                HibernateConfig.getEntityManagerFactory();
//
//        EntityManager em = emf.createEntityManager();
//
//        MovieService movieService = new MovieService();
//
//        List<Movie> movies =
//                movieService.getAllMoviesFromDatabase();
//
//        for (Movie movie : movies) {
//
//            System.out.println(
//                    movie.getId() + " | " +
//                            movie.getTitle() + " | " +
//                            movie.getReleaseDate() + " | " +
//                            movie.getVoteAverage() + " | " +
//                            movie.getPopularity()
//            );
//        }


        /*
         * Dette kald var tidligere skrevet to gange.
         * Derfor skal dette andet kald være udkommenteret.
         */

        // movieService.fetchAndSaveAllDanishMovies();


//        Movie movie = new Movie();
//
//        movie.setId(223456L);
//        movie.setTitle("Test Movie");
//
//        movie.setReleaseDate(
//                LocalDate.of(2025, 1, 1)
//        );
//
//        movie.setVoteAverage(8.5);
//        movie.setPopularity(100.0);
//
//        em.getTransaction().begin();
//
//        em.persist(movie);
//
//        em.getTransaction().commit();
//
//        em.close();
//        emf.close();


//        ApiReader apiReader = new ApiReader();
//
//        String json =
//                apiReader.getAllDataFromApi();
//
//        ObjectMapper objectMapper =
//                new ObjectMapper();
//
//        objectMapper.registerModule(
//                new JavaTimeModule()
//        );
//
//        try {
//
//            MovieResponseDTO movieResponseDTO =
//                    objectMapper.readValue(
//                            json,
//                            MovieResponseDTO.class
//                    );
//
//            for (MovieDTO movie
//                    : movieResponseDTO.getResults()) {
//
//                System.out.println(
//                        "ID: " + movie.getId()
//                );
//
//                System.out.println(
//                        "Title: " + movie.getTitle()
//                );
//
//                System.out.println(
//                        "Release date: "
//                                + movie.getReleaseDate()
//                );
//
//                System.out.println(
//                        "Rating: "
//                                + movie.getVoteAverage()
//                );
//
//                System.out.println(
//                        "Popularity: "
//                                + movie.getPopularity()
//                );
//
//                System.out.println(
//                        "Genres: "
//                                + movie.getGenreIds()
//                );
//
//                System.out.println(
//                        "-------------------------"
//                );
//            }
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }
}