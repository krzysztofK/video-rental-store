package krzysztofk.video.rental;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import krzysztofk.video.rental.core.Customer;
import krzysztofk.video.rental.core.Film;
import krzysztofk.video.rental.core.FilmRental;
import krzysztofk.video.rental.core.Rental;
import krzysztofk.video.rental.core.RentalService;
import krzysztofk.video.rental.dao.CustomerDAO;
import krzysztofk.video.rental.dao.FilmDAO;
import krzysztofk.video.rental.dao.RentalDAO;
import krzysztofk.video.rental.resources.CustomerResource;
import krzysztofk.video.rental.resources.FilmResource;
import krzysztofk.video.rental.resources.RentalResource;

import static krzysztofk.video.rental.api.MoneySerialization.serializationModule;

public class VideoRentalApplication extends Application<VideoRentalConfiguration> {

  private final HibernateBundle<VideoRentalConfiguration> hibernate = new HibernateBundle<VideoRentalConfiguration>(Film.class, Rental.class, FilmRental.class, Customer.class) {
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
    SimpleModule module = serializationModule();
    environment.getObjectMapper().registerModule(module);
    final FilmDAO filmDAO = new FilmDAO(hibernate.getSessionFactory());
    final FilmResource filmResource = new FilmResource(filmDAO);
    final RentalDAO rentalDAO = new RentalDAO(hibernate.getSessionFactory());
    final RentalService rentalService = new RentalService(rentalDAO, filmDAO);
    final RentalResource rentalResource = new RentalResource(rentalService);
    final CustomerDAO customerDAO = new CustomerDAO(hibernate.getSessionFactory());
    final CustomerResource customerResource = new CustomerResource(customerDAO);
    environment.jersey().register(filmResource);
    environment.jersey().register(rentalResource);
    environment.jersey().register(customerResource);
  }

  @Override
  public void initialize(Bootstrap<VideoRentalConfiguration> bootstrap) {
    bootstrap.addBundle(hibernate);
  }
}