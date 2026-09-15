package service;

import dto.MovieDTO;
import entities.Movie;

public class ConvertToEntity {

    public Movie convertToMovieEntity(MovieDTO movieDTO){

        Movie movie = new Movie();

        movie.setId(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setVoteAverage(movieDTO.getVoteAverage());
        movie.setPopularity(movieDTO.getPopularity());

        return movie;
    }
}
