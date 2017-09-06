package krzysztofk.video.rental.core;

import krzysztofk.video.rental.api.FilmToRent;
import krzysztofk.video.rental.api.FilmsReturn;
import krzysztofk.video.rental.api.PricedRental;
import krzysztofk.video.rental.api.PricedReturn;
import krzysztofk.video.rental.api.RentalRequest;
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

  public PricedRental addRental(RentalRequest rentalRequest) {
    PricedRental addedRental = rentalDAO.add(createRental(rentalRequest)).calculatePrice();
    giveCustomerBonusPoints(rentalRequest.getCustomerId(), addedRental.getTotalBonusPoints());
    return addedRental;
  }

  private Rental createRental(RentalRequest rentalRequest) {
    return new Rental(null,
        rentalRequest.getRentalDate(),
        rentalRequest.getFilms().stream().map(this::createFilmRental).collect(toList()));
  }

  private FilmRental createFilmRental(FilmToRent filmToRent) {
    return new FilmRental(filmToRent.getRentedForDays(), filmDAO.getById(filmToRent.getFilmId()));
  }

  private void giveCustomerBonusPoints(int customerId, int bonusPoints) {
    customerDAO.getById(customerId).addBonusPoints(bonusPoints);
  }

  public PricedReturn priceReturn(int rentalId, FilmsReturn filmsReturn) {
    return rentalDAO.findById(rentalId).calculateReturnPrice(filmsReturn);
  }
}