package krzysztofk.video.rental.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "FILMS")
public class Film {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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