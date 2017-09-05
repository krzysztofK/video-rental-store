package krzysztofk.video.rental.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static krzysztofk.video.rental.core.BonusPointsCalculator.calculateBonusPoints;

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

  public int getRentedForDays() {
    return rentedForDays;
  }

  public Film getFilm() {
    return film;
  }

  PricedFilm calculatePrice() {
    return new PricedFilm(film, RentalPriceCalculator.calculatePrice(film.getType(), rentedForDays), calculateBonusPoints(film.getType()));
  }

  FilmSurcharge calculateSurchargeIfLateReturn(int returnedAfterDays) {
    if (returnedAfterDays > rentedForDays) {
      return calculateSurcharge(returnedAfterDays);
    } else {
      return zeroSurcharge();
    }
  }

  private FilmSurcharge zeroSurcharge() {
    return new FilmSurcharge(film, 0, RentalPriceCalculator.zero());
  }

  private FilmSurcharge calculateSurcharge(int returnedAfterDays) {
    return new FilmSurcharge(
        film,
        returnedAfterDays - rentedForDays,
        RentalPriceCalculator.calculateSurcharge(film.getType(), rentedForDays, returnedAfterDays));
  }
}