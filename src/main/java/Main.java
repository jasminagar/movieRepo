import dao.Moviedao;
import entities.Actor;
import entities.Director;
import entities.Movie;
import service.MovieService;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MovieService movieService = new MovieService();
        Moviedao moviedao = new Moviedao();


        /*
         * HENT FILM FRA TMDb
         *
         * Fjern //, hvis du vil hente film fra API'et.
         * Lad den være kommenteret ud, mens du tester CRUD.
         */

        // movieService.fetchAndSaveAllDanishMovies();


        /*
         * CREATE
         * Opret og gem en ny film i databasen.
         */

        Movie testMovie = new Movie();

        testMovie.setId(999999L);
        testMovie.setTitle("Test Movie");
        testMovie.setReleaseDate(
                LocalDate.of(2026, 9, 17)
        );
        testMovie.setVoteAverage(8.0);
        testMovie.setPopularity(50.0);

        moviedao.saveMovie(testMovie);

        System.out.println("Filmen er blevet gemt");


        /*
         * UPDATE
         * Opdater filmens titel og udgivelsesdato.
         */

        moviedao.updateMovie(
                999999L,
                "Updated Movie Title",
                LocalDate.of(2027, 1, 10)
        );

        System.out.println("Filmen er blevet opdateret");


        /*
         * READ
         * Find filmen i databasen.
         */

        Movie updatedMovie =
                moviedao.findMovieById(999999L);

        if (updatedMovie != null) {

            System.out.println();
            System.out.println("===== UPDATED MOVIE =====");

            System.out.println(
                    "ID: " + updatedMovie.getId()
            );

            System.out.println(
                    "Title: " + updatedMovie.getTitle()
            );

            System.out.println(
                    "Release date: "
                            + updatedMovie.getReleaseDate()
            );
        }


        /*
         * HENT OG UDSKRIV ALLE FILM
         */

        List<Movie> movies =
                movieService.getAllMoviesFromDatabase();

        System.out.println();
        System.out.println("===== ALL MOVIES =====");

        for (Movie movie : movies) {

            System.out.println();
            System.out.println(
                    "Movie: " + movie.getTitle()
            );

            System.out.println(
                    "Release date: "
                            + movie.getReleaseDate()
            );


            /*
             * ACTORS
             */

            System.out.println("Actors:");

            if (movie.getActors() != null
                    && !movie.getActors().isEmpty()) {

                for (Actor actor : movie.getActors()) {
                    System.out.println(
                            " - " + actor.getName()
                    );
                }

            } else {
                System.out.println(" - No actors found");
            }


            /*
             * DIRECTORS
             */

            System.out.println("Directors:");

            if (movie.getDirectors() != null
                    && !movie.getDirectors().isEmpty()) {

                for (Director director
                        : movie.getDirectors()) {

                    System.out.println(
                            " - " + director.getName()
                    );
                }

            } else {
                System.out.println(
                        " - No director found"
                );
            }
        }


        /*
         * DELETE
         *
         * Fjern // foran linjen, når du vil teste,
         * at filmen kan slettes.
         */

        // moviedao.deleteMovie(999999L);
        // System.out.println("Filmen er blevet slettet");
    }
}