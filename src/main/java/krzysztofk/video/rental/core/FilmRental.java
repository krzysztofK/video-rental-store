package krzysztofk.video.rental.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FILM_RENTALS")
public class FilmRental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private int rentedForDays;
  @ManyToOne
  private Film film;

  public FilmRental(int rentedForDays, Film film) {
    this.rentedForDays = rentedForDays;
    this.film = film;
  }

  public FilmRental() {
  }

  public PricedFilm calculatePrice() {
    return new PricedFilm(film, RentalPriceCalculator.calculatePrice(film.getType(), rentedForDays));
  }

  public int getRentedForDays() {
    return rentedForDays;
  }

  public Film getFilm() {
    return film;
  }
}