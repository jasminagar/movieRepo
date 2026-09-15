package config;

import entities.Movie;
import org.hibernate.cfg.Configuration;

public class EntityRegistery {
static void registerEntities(Configuration configuration){
    configuration.addAnnotatedClass(Movie.class);

}


}
