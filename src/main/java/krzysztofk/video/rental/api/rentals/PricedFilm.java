package krzysztofk.video.rental.api.rentals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import krzysztofk.video.rental.core.films.Film;
import org.joda.money.Money;

public class PricedFilm {

  private final Film film;
  private final Money price;
  private final int bonusPoints;

  @JsonCreator
  public PricedFilm(@JsonProperty("film") Film film,
                    @JsonProperty("price") Money price,
                    @JsonProperty("bonusPoints") int bonusPoints) {
    this.film = film;
    this.price = price;
    this.bonusPoints = bonusPoints;
  }

  public Film getFilm() {
    return film;
  }

  public Money getPrice() {
    return price;
  }

  public int getBonusPoints() {
    return bonusPoints;
  }
}