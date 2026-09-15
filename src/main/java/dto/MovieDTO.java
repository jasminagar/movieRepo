package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    private Long id;

    private String title;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("vote_average")
    private double voteAverage;

    private double popularity;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
}