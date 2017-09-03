package krzysztofk.video.rental.core;

import krzysztofk.video.rental.api.FilmsReturn;
import org.joda.money.Money;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "RENTALS")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private ZonedDateTime rentalDate;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "rental_id")
  private List<FilmRental> films;

  public Rental() {
  }

  public Rental(Integer id,
                ZonedDateTime rentalDate,
                List<FilmRental> films) {
    this.id = id;
    this.rentalDate = rentalDate;
    this.films = films;
  }

  public Integer getId() {
    return id;
  }

  public ZonedDateTime getRentalDate() {
    return rentalDate;
  }

  public List<FilmRental> getFilms() {
    return films;
  }

  public PricedRental calculatePrice() {
    List<PricedFilm> pricedFilms = calculateFilmsPrices();
    return new PricedRental(id, rentalDate, pricedFilms, calculateTotalPrice(pricedFilms));
  }

  private List<PricedFilm> calculateFilmsPrices() {
    return films.stream().map(FilmRental::calculatePrice).collect(toList());
  }

  private Money calculateTotalPrice(List<PricedFilm> films) {
    return films.stream().map(PricedFilm::getPrice).reduce(RentalPriceCalculator.zero(), Money::plus);
  }

  public PricedReturn calculateReturnPrice(FilmsReturn filmsReturn) {
    return null;
  }
}