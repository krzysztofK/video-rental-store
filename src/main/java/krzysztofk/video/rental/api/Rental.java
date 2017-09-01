package krzysztofk.video.rental.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public class Rental {

  private final Integer id;
  @NotNull
  private final ZonedDateTime rentalDate;
  @NotNull
  private final int rentedForDays;
  @NotNull
  private final List<Integer> films;

  @JsonCreator
  public Rental(@JsonProperty("id") Integer id,
                @JsonProperty("rentalDate") ZonedDateTime rentalDate,
                @JsonProperty("rentedForDays") int rentedForDays,
                @JsonProperty("films") List<Integer> films) {
    this.id = id;
    this.rentalDate = rentalDate;
    this.rentedForDays = rentedForDays;
    this.films = films;
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

  public List<Integer> getFilms() {
    return films;
  }
}