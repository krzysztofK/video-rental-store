package krzysztofk.video.rental.api.rentals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public class FilmsRental {

  @NotNull
  private final ZonedDateTime rentalDate;
  @NotNull
  private final List<RentedFilm> films;
  @NotNull
  private final int customerId;

  @JsonCreator
  public FilmsRental(@JsonProperty("rentalDate") ZonedDateTime rentalDate,
                     @JsonProperty("films") List<RentedFilm> films,
                     @JsonProperty("customerId") int customerId) {
    this.rentalDate = rentalDate;
    this.films = films;
    this.customerId = customerId;
  }

  public ZonedDateTime getRentalDate() {
    return rentalDate;
  }

  public List<RentedFilm> getFilms() {
    return films;
  }

  public int getCustomerId() {
    return customerId;
  }
}