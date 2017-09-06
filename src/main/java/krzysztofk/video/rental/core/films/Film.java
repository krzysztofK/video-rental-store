package krzysztofk.video.rental.core.films;

import krzysztofk.video.rental.api.films.FilmType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FILMS")
public class Film {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private FilmType type;

  public Film() {
  }

  public Film(Integer id, String title, FilmType type) {
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