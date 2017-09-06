package krzysztofk.video.rental.api.rentals.returns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public class FilmsReturn {

  @NotNull
  private final ZonedDateTime returnDate;
  @NotNull
  private final List<Integer> films;

  @JsonCreator
  public FilmsReturn(@JsonProperty("returnDate") ZonedDateTime returnDate, @JsonProperty("films") List<Integer> films) {
    this.returnDate = returnDate;
    this.films = films;
  }

  public ZonedDateTime getReturnDate() {
    return returnDate;
  }

  public List<Integer> getFilms() {
    return films;
  }
}