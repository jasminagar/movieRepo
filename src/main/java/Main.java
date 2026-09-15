import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import config.HibernateConfig;
import dto.MovieDTO;
import dto.MovieResponseDTO;
import entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import service.ApiReader;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Movie movie = new Movie();

        movie.setId(223456L);
        movie.setTitle("Test Movie");
        movie.setReleaseDate(LocalDate.of(2025, 1, 1));
        movie.setVoteAverage(8.5);
        movie.setPopularity(100.0);

        em.getTransaction().begin();

        em.persist(movie);

        em.getTransaction().commit();

        em.close();
        emf.close();


//        ApiReader apiReader = new ApiReader();
//        String json = apiReader.getAllDataFromApi();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        try {
//
//            MovieResponseDTO movieResponseDTO =
//                    objectMapper.readValue(json, MovieResponseDTO.class);
//
//            for(MovieDTO movie : movieResponseDTO.getResults()){
//                System.out.println("ID: " + movie.getId());
//                System.out.println("Title: " + movie.getTitle());
//                System.out.println("Release date: " + movie.getReleaseDate());
//                System.out.println("Rating: " + movie.getVoteAverage());
//                System.out.println("Popularity: " + movie.getPopularity());
//                System.out.println("Genres: " + movie.getGenreIds());
//                System.out.println("-------------------------");
//            }
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
