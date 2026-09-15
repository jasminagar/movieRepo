package service;

import dto.MovieDTO;
import entities.Movie;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConvertToEntityTest {

    @Test
    void  convertToMovieEntityShouldConvertAllFields(){
        //Arrange
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(123L);
        movieDTO.setTitle("Inception");
        movieDTO.setReleaseDate(LocalDate.of(2010, 7, 16));
        movieDTO.setVoteAverage(8.4);
        movieDTO.setPopularity(95.5);
        movieDTO.setGenreIds(List.of(28, 878));

        ConvertToEntity convertToEntity = new ConvertToEntity();

        //Act
        Movie movie = convertToEntity.convertToMovieEntity(movieDTO);

        //Assert
        assertEquals(123L, movie.getId());
        assertEquals("Inception", movie.getTitle());
        assertEquals(LocalDate.of(2010, 7, 16), movie.getReleaseDate());
        assertEquals(8.4, movie.getVoteAverage());
        assertEquals(95.5, movie.getPopularity());

    }
}
