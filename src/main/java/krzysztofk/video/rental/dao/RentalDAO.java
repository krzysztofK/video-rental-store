package krzysztofk.video.rental.dao;

import io.dropwizard.hibernate.AbstractDAO;
import krzysztofk.video.rental.core.Rental;
import org.hibernate.SessionFactory;

public class RentalDAO extends AbstractDAO<Rental> {

  public RentalDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Rental add(Rental rental) {
    return persist(rental);
  }

  public Rental findById(Integer id) {
    return get(id);
  }
}