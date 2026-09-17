
        package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ActorDao;
import dao.DirectorDao;
import dao.GenreDao;
import dao.Moviedao;
import dto.*;
import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MovieService {

    private final ApiReader apiReader;
    private final ObjectMapper objectMapper;
    private final Moviedao moviedao;
    private final ActorDao actorDao;
    private final DirectorDao directorDao;
    private final GenreDao genreDao;

    public MovieService() {
        this.apiReader = new ApiReader();

        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();

        this.moviedao = new Moviedao();
        this.actorDao = new ActorDao();
        this.directorDao = new DirectorDao();
        this.genreDao = new GenreDao();
    }

    public double getAverageRating() {
        return moviedao.getAverageRating();
    }

    public List<Movie> getTop10HighestRated() {
        return moviedao.getTop10HighestRated();
    }

    public List<Movie> getTop10LowestRated() {
        return moviedao.getTop10LowestRated();
    }

    public List<Movie> getTop10MostPopular() {
        return moviedao.getTop10MostPopular();
    }

    public void testMovieDetails(Long movieId) {

        String json = apiReader.getMovieDetails(movieId);

        try {
            MovieDetailsDTO details =
                    objectMapper.readValue(
                            json,
                            MovieDetailsDTO.class
                    );

            System.out.println("Title: " + details.getTitle());
            System.out.println("Credits: " + details.getCredits());

            if (details.getCredits() != null) {

                System.out.println(
                        "Cast: " +
                                details.getCredits().getCast().size()
                );

                System.out.println(
                        "Crew: " +
                                details.getCredits().getCrew().size()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> getAllMoviesFromDatabase() {
        return moviedao.findAllMovies();
    }

    public List<GenreDTO> getAllGenres() {

        String json = apiReader.getGenres();

        try {
            GenreResponseDTO response =
                    objectMapper.readValue(
                            json,
                            GenreResponseDTO.class
                    );

            return response.getGenres();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not convert genre JSON to DTO",
                    e
            );
        }
    }

    public void fetchAndSaveAllDanishMovies() {

        ConvertToEntity convertToEntity = new ConvertToEntity();

        List<MovieDTO> movies = getAllDanishMovies();
        List<GenreDTO> genres = getAllGenres();

        ExecutorService executor =
                Executors.newFixedThreadPool(5);

        try {

            List<Future<MovieDetailsDTO>> futures = new ArrayList<>();

            for (MovieDTO movieDTO : movies) {

                Callable<MovieDetailsDTO> task = () -> {

                    String json =
                            apiReader.getMovieDetails(movieDTO.getId());

                    return objectMapper.readValue(
                            json,
                            MovieDetailsDTO.class
                    );
                };

                futures.add(executor.submit(task));
            }

            for (int i = 0; i < movies.size(); i++) {

                MovieDTO movieDTO = movies.get(i);

                Movie movie =
                        convertToEntity.convertToMovieEntity(movieDTO);

                MovieDetailsDTO details =
                        futures.get(i).get();

                addActors(movie, details);

                addDirector(movie, details);

                addGenres(movie, movieDTO, genres);

                moviedao.saveMovie(movie);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not fetch movie details",
                    e
            );

        } finally {
            executor.shutdown();
        }
    }

    public void addGenres(
            Movie movie,
            MovieDTO movieDTO,
            List<GenreDTO> genres) {

        if (movieDTO.getGenreIds() == null) {
            return;
        }

        for (Integer genreId : movieDTO.getGenreIds()) {

            for (GenreDTO genreDTO : genres) {

                if (genreDTO.getId().equals(genreId.longValue())) {

                    Genre genre =
                            genreDao.getGenreById(
                                    genreDTO.getId()
                            );

                    if (genre == null) {

                        genre = new Genre();

                        genre.setId(
                                genreDTO.getId()
                        );

                        genre.setName(
                                genreDTO.getName()
                        );

                        genreDao.saveGenre(genre);
                    }

                    movie.getGenres().add(genre);

                    break;
                }
            }
        }
    }

    public List<MovieDTO> getAllDanishMovies() {

        List<MovieDTO> movies = new ArrayList<>();

        int page = 1;
        int totalPages;

        do {
            String json = apiReader.getAllDataFromApi(page);

            try {
                MovieResponseDTO response =
                        objectMapper.readValue(
                                json,
                                MovieResponseDTO.class
                        );

                movies.addAll(response.getResults());

                totalPages = response.getTotalPages();
                page++;

            } catch (Exception e) {

                throw new RuntimeException(
                        "Could not convert TMDb JSON to DTO",
                        e
                );
            }

        } while (page <= totalPages);

        return movies;
    }

    public void addDirector(
            Movie movie,
            MovieDetailsDTO details) {

        if (details.getCredits() == null ||
                details.getCredits().getCrew() == null) {
            return;
        }

        for (CrewMemberDTO crewMember :
                details.getCredits().getCrew()) {

            if ("Director".equals(crewMember.getJob())) {

                Director director =
                        directorDao.getDirectorById(
                                crewMember.getId()
                        );

                if (director == null) {

                    director = new Director();

                    director.setId(crewMember.getId());
                    director.setName(crewMember.getName());

                    directorDao.saveDirector(director);
                }

                movie.getDirectors().add(director);
            }
        }
    }

    public void addGenre(Movie movie, MovieDetailsDTO movieDetailsDTO){

    }


    public void addActors(
            Movie movie,
            MovieDetailsDTO details) {

        if (details.getCredits() == null ||
                details.getCredits().getCast() == null) {
            return;
        }

        for (ActorDTO actorDTO :
                details.getCredits().getCast()) {

            Actor actor =
                    actorDao.getActorById(actorDTO.getId());

            if (actor == null) {

                actor = new Actor();

                actor.setId(actorDTO.getId());
                actor.setName(actorDTO.getName());

                actorDao.saveActor(actor);

                actor =
                        actorDao.getActorById(
                                actorDTO.getId()
                        );
            }

            movie.getActors().add(actor);
        }
    }
}

