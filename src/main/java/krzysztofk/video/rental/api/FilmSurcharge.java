package krzysztofk.video.rental.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import krzysztofk.video.rental.core.*;
import org.joda.money.Money;

public class FilmSurcharge {

  private final krzysztofk.video.rental.core.Film film;
  private final int extraDays;
  private final Money surcharge;

  @JsonCreator
  public FilmSurcharge(@JsonProperty("film") krzysztofk.video.rental.core.Film film,
                       @JsonProperty("extraDays") int extraDays,
                       @JsonProperty("surcharge") Money surcharge) {
    this.film = film;
    this.extraDays = extraDays;
    this.surcharge = surcharge;
  }

  public krzysztofk.video.rental.core.Film getFilm() {
    return film;
  }

  public Money getSurcharge() {
    return surcharge;
  }

  public int getExtraDays() {
    return extraDays;
  }
}