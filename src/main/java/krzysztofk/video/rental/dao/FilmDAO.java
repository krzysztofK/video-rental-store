package krzysztofk.video.rental.dao;

import io.dropwizard.hibernate.AbstractDAO;
import krzysztofk.video.rental.core.Film;
import org.hibernate.SessionFactory;

public class FilmDAO extends AbstractDAO<Film> {

  public FilmDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Film add(Film film) {
    return persist(film);
  }

  public Film getById(int id) {
    return get(id);
  }
}
