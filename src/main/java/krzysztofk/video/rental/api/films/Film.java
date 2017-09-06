package krzysztofk.video.rental.api.films;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class Film {

  private final Integer id;
  @NotNull
  private final String title;
  @NotNull
  private final FilmType type;

  @JsonCreator
  public Film(@JsonProperty("id") Integer id, @JsonProperty("title") String title, @JsonProperty("type") FilmType type) {
    this.id = id;
    this.title = title;
    this.type = type;
  }

  public Integer getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public FilmType getType() {
    return type;
  }
}