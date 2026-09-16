package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Moviedao;
import dto.MovieDTO;

import java.util.List;

public class MovieService {
    private final ApiReader apiReader;
    private final ObjectMapper objectMapper;
    private final Moviedao moviedao;

    public MovieService(ApiReader apiReader, ObjectMapper objectMapper, Moviedao moviedao) {
        this.apiReader = apiReader;
        this.objectMapper = objectMapper;
        this.moviedao = moviedao;
    }

    public void getAllDanishMoviesAndSave(){
        List<MovieDTO> allMovies = apiReader.getAllDataFromApi()
    }


}
