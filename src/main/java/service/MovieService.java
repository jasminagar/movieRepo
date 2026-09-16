
        package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ActorDao;
import dao.DirectorDao;
import dao.Moviedao;
import dto.*;
import entities.Actor;
import entities.Director;
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

    public MovieService() {
        this.apiReader = new ApiReader();

        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();

        this.moviedao = new Moviedao();
        this.actorDao = new ActorDao();
        this.directorDao = new DirectorDao();
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

    public void fetchAndSaveAllDanishMovies() {

        ConvertToEntity convertToEntity = new ConvertToEntity();

        List<MovieDTO> movies = getAllDanishMovies();

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

