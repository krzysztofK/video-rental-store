package krzysztofk.video.rental.converter;

import krzysztofk.video.rental.api.films.Film;

public class FilmConverter {

  public static Film toApi(krzysztofk.video.rental.core.films.Film film) {
    return new Film(film.getId(), film.getTitle(), film.getType());
  }

  public static krzysztofk.video.rental.core.films.Film toCore(Film film) {
    return new krzysztofk.video.rental.core.films.Film(film.getId(), film.getTitle(), film.getType());
  }
}