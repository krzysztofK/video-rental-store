package krzysztofk.video.rental;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import krzysztofk.video.rental.api.Film;
import krzysztofk.video.rental.dao.FilmDAO;
import krzysztofk.video.rental.resources.FilmResource;

public class VideoRentalApplication extends Application<VideoRentalConfiguration> {

  private final HibernateBundle<VideoRentalConfiguration> hibernate = new HibernateBundle<VideoRentalConfiguration>(Film.class) {
    @Override
    public PooledDataSourceFactory getDataSourceFactory(VideoRentalConfiguration configuration) {
      return configuration.getDataSourceFactory();
    }
  };

  public static void main(String[] args) throws Exception {
    new VideoRentalApplication().run(args);
  }

  public void run(VideoRentalConfiguration videoRentalConfiguration, Environment environment) throws Exception {
    final FilmDAO filmDAO = new FilmDAO(hibernate.getSessionFactory());
    final FilmResource filmResource = new FilmResource(filmDAO);
    environment.jersey().register(filmResource);
  }

  @Override
  public void initialize(Bootstrap<VideoRentalConfiguration> bootstrap) {
    bootstrap.addBundle(hibernate);
  }
}