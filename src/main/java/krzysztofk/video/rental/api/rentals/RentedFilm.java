package krzysztofk.video.rental.api.rentals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class RentedFilm {

  @NotNull
  private final int filmId;
  @NotNull
  private final int rentedForDays;

  @JsonCreator
  public RentedFilm(@JsonProperty("filmId") int filmId, @JsonProperty("rentedForDays") int rentedForDays) {
    this.filmId = filmId;
    this.rentedForDays = rentedForDays;
  }

  public int getFilmId() {
    return filmId;
  }

  public int getRentedForDays() {
    return rentedForDays;
  }
}