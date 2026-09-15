package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    private Long id;

    private String title;
    private LocalDate releaseDate;
    private double voteAverage;
    private double popularity;
}