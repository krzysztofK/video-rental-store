package krzysztofk.video.rental.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.money.Money;

public class PricedFilm {

  private final Film film;
  private final Money price;

  @JsonCreator
  public PricedFilm(@JsonProperty("film") Film film, @JsonProperty("price") Money price) {
    this.film = film;
    this.price = price;
  }

  public Film getFilm() {
    return film;
  }

  public Money getPrice() {
    return price;
  }
}
