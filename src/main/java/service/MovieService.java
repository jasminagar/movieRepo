package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Moviedao;
import dto.MovieDTO;
import dto.MovieResponseDTO;
import entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private final ApiReader apiReader;
    private final ObjectMapper objectMapper;
    private final Moviedao moviedao;

    public MovieService() {
        this.apiReader = new ApiReader();

        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();

        this.moviedao = new Moviedao();
    }

    public List<Movie> getAllMoviesFromDatabase(){
        return moviedao.findAllMovies();
    }

    public void fetchAndSaveAllDanishMovies() {
        ConvertToEntity convertToEntity = new ConvertToEntity();

        List<MovieDTO> movies = getAllDanishMovies();

        for (MovieDTO movieDTO : movies) {

            Movie movie = convertToEntity.convertToMovieEntity(movieDTO);

            moviedao.saveMovie(movie);
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


}
