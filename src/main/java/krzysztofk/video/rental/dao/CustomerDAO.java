package krzysztofk.video.rental.dao;

import io.dropwizard.hibernate.AbstractDAO;
import krzysztofk.video.rental.core.customers.Customer;
import org.hibernate.SessionFactory;

public class CustomerDAO extends AbstractDAO<Customer> {

  public CustomerDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Customer add(Customer customer) {
    return persist(customer);
  }

  public Customer getById(int id) {
    return get(id);
  }
}