package service;

import dto.ActorDTO;
import dto.DirectorDTO;
import dto.MovieDTO;
import entities.Actor;
import entities.Director;
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

    public Actor convertToActorEntity(ActorDTO actorDTO){
        Actor actor = new Actor();

        actor.setId(actorDTO.getId());
        actor.setName(actorDTO.getName());

        return actor;
    }

    public Director convertToDirectorEntity(DirectorDTO directorDTO){
        Director director = new Director();

        director.setId(directorDTO.getId());
        director.setName(directorDTO.getName());

        return director;
    }
}
