package krzysztofk.video.rental.converter;

import krzysztofk.video.rental.api.Film;

public class FilmConverter {

  public static Film toApi(krzysztofk.video.rental.core.Film film) {
    return new Film(film.getId(), film.getTitle(), film.getType());
  }

  public static krzysztofk.video.rental.core.Film toCore(Film film) {
    return new krzysztofk.video.rental.core.Film(film.getId(), film.getTitle(), film.getType());
  }
}