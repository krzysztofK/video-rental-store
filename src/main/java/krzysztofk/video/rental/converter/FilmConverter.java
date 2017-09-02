package krzysztofk.video.rental.converter;

import krzysztofk.video.rental.api.Film;

public class FilmConverter {

  public static Film convert(krzysztofk.video.rental.dao.entitites.Film film) {
    return new Film(film.getId(), film.getTitle(), film.getType());
  }

  public static krzysztofk.video.rental.dao.entitites.Film convert(Film film) {
    return new krzysztofk.video.rental.dao.entitites.Film(film.getId(), film.getTitle(), film.getType());
  }
}