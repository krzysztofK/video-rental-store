package krzysztofk.video.rental.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import krzysztofk.video.rental.core.*;
import org.joda.money.Money;

public class PricedFilm {

  private final krzysztofk.video.rental.core.Film film;
  private final Money price;
  private final int bonusPoints;

  @JsonCreator
  public PricedFilm(@JsonProperty("film") krzysztofk.video.rental.core.Film film,
                    @JsonProperty("price") Money price,
                    @JsonProperty("bonusPoints") int bonusPoints) {
    this.film = film;
    this.price = price;
    this.bonusPoints = bonusPoints;
  }

  public krzysztofk.video.rental.core.Film getFilm() {
    return film;
  }

  public Money getPrice() {
    return price;
  }

  public int getBonusPoints() {
    return bonusPoints;
  }
}