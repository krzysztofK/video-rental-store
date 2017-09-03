package krzysztofk.video.rental.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.money.Money;

import java.time.ZonedDateTime;
import java.util.List;

public class PricedRental {

  private final Integer id;
  private final ZonedDateTime rentalDate;
  private final int rentedForDays;
  private final List<PricedFilm> films;
  private final Money totalPrice;

  @JsonCreator
  public PricedRental(@JsonProperty("id") Integer id,
                      @JsonProperty("rentalDate") ZonedDateTime rentalDate,
                      @JsonProperty("rentedForDays") int rentedForDays,
                      @JsonProperty("films") List<PricedFilm> films,
                      @JsonProperty("totalPrice") Money totalPrice) {
    this.id = id;
    this.rentalDate = rentalDate;
    this.rentedForDays = rentedForDays;
    this.films = films;
    this.totalPrice = totalPrice;
  }

  public Integer getId() {
    return id;
  }

  public ZonedDateTime getRentalDate() {
    return rentalDate;
  }

  public int getRentedForDays() {
    return rentedForDays;
  }

  public List<PricedFilm> getFilms() {
    return films;
  }

  public Money getTotalPrice() {
    return totalPrice;
  }
}