package config;

import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import org.hibernate.cfg.Configuration;

public class EntityRegistery {

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(Director.class);
        configuration.addAnnotatedClass(Genre.class);
    }
}