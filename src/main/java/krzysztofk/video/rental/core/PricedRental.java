package krzysztofk.video.rental.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.money.Money;

import java.time.ZonedDateTime;
import java.util.List;

public class PricedRental {

  private final Integer id;
  private final ZonedDateTime rentalDate;
  private final List<PricedFilm> films;
  private final Money totalPrice;
  private final int totalBonusPoints;

  @JsonCreator
  public PricedRental(@JsonProperty("id") Integer id,
                      @JsonProperty("rentalDate") ZonedDateTime rentalDate,
                      @JsonProperty("films") List<PricedFilm> films,
                      @JsonProperty("totalPrice") Money totalPrice,
                      @JsonProperty("totalBonusPointa") int totalBonusPoints) {
    this.id = id;
    this.rentalDate = rentalDate;
    this.films = films;
    this.totalPrice = totalPrice;
    this.totalBonusPoints = totalBonusPoints;
  }

  public Integer getId() {
    return id;
  }

  public ZonedDateTime getRentalDate() {
    return rentalDate;
  }

  public List<PricedFilm> getFilms() {
    return films;
  }

  public Money getTotalPrice() {
    return totalPrice;
  }

  public int getTotalBonusPoints() {
    return totalBonusPoints;
  }
}