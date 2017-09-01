package krzysztofk.video.rental.dao.entitites;

import krzysztofk.video.rental.api.Film;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "RENTALS")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private ZonedDateTime rentalDate;
  private int rentedForDays;
  @ManyToMany
  @JoinTable(name = "RENTALS_FILMS", joinColumns = @JoinColumn(name = "rental_id"), inverseJoinColumns = @JoinColumn(name = "film_id"))
  private List<Film> films;

  public Rental() {
  }

  public Rental(Integer id,
                ZonedDateTime rentalDate,
                int rentedForDays,
                List<Film> films) {
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

  public List<Film> getFilms() {
    return films;
  }
}