package krzysztofk.video.rental.api.rentals.returns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import krzysztofk.video.rental.core.films.Film;
import org.joda.money.Money;

public class FilmSurcharge {

  private final Film film;
  private final int extraDays;
  private final Money surcharge;

  @JsonCreator
  public FilmSurcharge(@JsonProperty("film") Film film,
                       @JsonProperty("extraDays") int extraDays,
                       @JsonProperty("surcharge") Money surcharge) {
    this.film = film;
    this.extraDays = extraDays;
    this.surcharge = surcharge;
  }

  public Film getFilm() {
    return film;
  }

  public Money getSurcharge() {
    return surcharge;
  }

  public int getExtraDays() {
    return extraDays;
  }
}