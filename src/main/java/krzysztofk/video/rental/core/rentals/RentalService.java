package krzysztofk.video.rental.core.rentals;

import krzysztofk.video.rental.api.rentals.RentedFilm;
import krzysztofk.video.rental.api.rentals.returns.FilmsReturn;
import krzysztofk.video.rental.api.rentals.PricedRental;
import krzysztofk.video.rental.api.rentals.returns.PricedReturn;
import krzysztofk.video.rental.api.rentals.FilmsRental;
import krzysztofk.video.rental.dao.CustomerDAO;
import krzysztofk.video.rental.dao.FilmDAO;
import krzysztofk.video.rental.dao.RentalDAO;

import static java.util.stream.Collectors.toList;

public class RentalService {

  private final RentalDAO rentalDAO;
  private final FilmDAO filmDAO;
  private final CustomerDAO customerDAO;

  public RentalService(RentalDAO rentalDAO, FilmDAO filmDAO, CustomerDAO customerDAO) {
    this.rentalDAO = rentalDAO;
    this.filmDAO = filmDAO;
    this.customerDAO = customerDAO;
  }

  public PricedRental addRental(FilmsRental filmsRental) {
    PricedRental addedRental = rentalDAO.add(createRental(filmsRental)).calculatePrice();
    giveCustomerBonusPoints(filmsRental.getCustomerId(), addedRental.getTotalBonusPoints());
    return addedRental;
  }

  private Rental createRental(FilmsRental filmsRental) {
    return new Rental(null,
        filmsRental.getRentalDate(),
        filmsRental.getFilms().stream().map(this::createFilmRental).collect(toList()));
  }

  private FilmRental createFilmRental(RentedFilm filmToRent) {
    return new FilmRental(filmToRent.getRentedForDays(), filmDAO.getById(filmToRent.getFilmId()));
  }

  private void giveCustomerBonusPoints(int customerId, int bonusPoints) {
    customerDAO.getById(customerId).addBonusPoints(bonusPoints);
  }

  public PricedReturn priceReturn(int rentalId, FilmsReturn filmsReturn) {
    return rentalDAO.findById(rentalId).calculateReturnPrice(filmsReturn);
  }
}