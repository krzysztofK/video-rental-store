package krzysztofk.video.rental.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class Customer {

  private final Integer id;
  @NotNull
  private final String name;
  @NotNull
  private final String surname;
  private final Integer bonusPoints;

  @JsonCreator
  public Customer(@JsonProperty("id") Integer id,
                  @JsonProperty("name") String name,
                  @JsonProperty("surname") String surname,
                  @JsonProperty("bonusPoints") Integer bonusPoints) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.bonusPoints = bonusPoints;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public Integer getBonusPoints() {
    return bonusPoints;
  }
}
