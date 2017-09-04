package krzysztofk.video.rental.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String surname;
  private int bonusPoints;

  public Customer(String name, String surname, int bonusPoints) {
    this.name = name;
    this.surname = surname;
    this.bonusPoints = bonusPoints;
  }

  public Customer() {
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

  public int getBonusPoints() {
    return bonusPoints;
  }
}