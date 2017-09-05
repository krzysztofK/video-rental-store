package krzysztofk.video.rental.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public class RentalRequest {

  @NotNull
  private final ZonedDateTime rentalDate;
  @NotNull
  private final List<FilmToRent> films;
  @NotNull
  private final int customerId;

  @JsonCreator
  public RentalRequest(@JsonProperty("rentalDate") ZonedDateTime rentalDate,
                       @JsonProperty("films") List<FilmToRent> films,
                       @JsonProperty("customerId") int customerId) {
    this.rentalDate = rentalDate;
    this.films = films;
    this.customerId = customerId;
  }

  public ZonedDateTime getRentalDate() {
    return rentalDate;
  }

  public List<FilmToRent> getFilms() {
    return films;
  }

  public int getCustomerId() {
    return customerId;
  }
}