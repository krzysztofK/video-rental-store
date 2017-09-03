package krzysztofk.video.rental.core;

import org.joda.money.Money;

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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

  public PricedRental calculatePrice() {
    List<PricedFilm> filmsPrices = calculateFilmsPrices();
    return new PricedRental(id, rentalDate, rentedForDays, filmsPrices, calculateTotalPrice(filmsPrices));
  }

  private List<PricedFilm> calculateFilmsPrices() {
    return films.stream().map(film ->
        new PricedFilm(film, RentalPriceCalculator.calculatePrice(film, rentedForDays))
    ).collect(toList());
  }

  private Money calculateTotalPrice(List<PricedFilm> films) {
    return films.stream().map(PricedFilm::getPrice).reduce(RentalPriceCalculator.zero(), Money::plus);
  }
}