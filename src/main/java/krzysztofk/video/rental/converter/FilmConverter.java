package krzysztofk.video.rental.converter;

import krzysztofk.video.rental.api.Film;

public class FilmConverter {

  public static Film convert(krzysztofk.video.rental.core.Film film) {
    return new Film(film.getId(), film.getTitle(), film.getType());
  }

  public static krzysztofk.video.rental.core.Film convert(Film film) {
    return new krzysztofk.video.rental.core.Film(film.getId(), film.getTitle(), film.getType());
  }
}