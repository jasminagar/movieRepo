package entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Director {

    @Id
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "directors")
    private List<Movie> movies = new ArrayList<>();
}