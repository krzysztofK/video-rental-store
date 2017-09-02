package krzysztofk.video.rental;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import krzysztofk.video.rental.dao.entitites.Film;
import krzysztofk.video.rental.dao.entitites.Rental;
import krzysztofk.video.rental.core.RentalService;
import krzysztofk.video.rental.dao.FilmDAO;
import krzysztofk.video.rental.dao.RentalDAO;
import krzysztofk.video.rental.resources.FilmResource;
import krzysztofk.video.rental.resources.RentalResource;

public class VideoRentalApplication extends Application<VideoRentalConfiguration> {

  private final HibernateBundle<VideoRentalConfiguration> hibernate = new HibernateBundle<VideoRentalConfiguration>(Film.class, Rental.class) {
    @Override
    public PooledDataSourceFactory getDataSourceFactory(VideoRentalConfiguration configuration) {
      return configuration.getDataSourceFactory();
    }
  };

  public static void main(String[] args) throws Exception {
    new VideoRentalApplication().run(args);
  }

  public void run(VideoRentalConfiguration videoRentalConfiguration, Environment environment) throws Exception {
    environment.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    final FilmDAO filmDAO = new FilmDAO(hibernate.getSessionFactory());
    final FilmResource filmResource = new FilmResource(filmDAO);
    final RentalDAO rentalDAO = new RentalDAO(hibernate.getSessionFactory());
    final RentalService rentalService = new RentalService(rentalDAO, filmDAO);
    final RentalResource rentalResource = new RentalResource(rentalService);
    environment.jersey().register(filmResource);
    environment.jersey().register(rentalResource);
  }

  @Override
  public void initialize(Bootstrap<VideoRentalConfiguration> bootstrap) {
    bootstrap.addBundle(hibernate);
  }
}