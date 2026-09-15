import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.MovieDTO;
import dto.MovieResponseDTO;
import service.ApiReader;

public class Main {
    public static void main(String[] args) {

        ApiReader apiReader = new ApiReader();
        String json = apiReader.getAllDataFromApi();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {

            MovieResponseDTO movieResponseDTO =
                    objectMapper.readValue(json, MovieResponseDTO.class);

            for(MovieDTO movie : movieResponseDTO.getResults()){
                System.out.println("ID: " + movie.getId());
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Release date: " + movie.getReleaseDate());
                System.out.println("Rating: " + movie.getVoteAverage());
                System.out.println("Popularity: " + movie.getPopularity());
                System.out.println("Genres: " + movie.getGenreIds());
                System.out.println("-------------------------");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
