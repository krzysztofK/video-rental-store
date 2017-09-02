package krzysztofk.video.rental.core;

import krzysztofk.video.rental.api.RentalRequest;
import krzysztofk.video.rental.dao.FilmDAO;
import krzysztofk.video.rental.dao.RentalDAO;
import krzysztofk.video.rental.dao.entitites.Rental;

import static java.util.stream.Collectors.toList;

public class RentalService {

  private final RentalDAO rentalDAO;
  private final FilmDAO filmDAO;

  public RentalService(RentalDAO rentalDAO, FilmDAO filmDAO) {
    this.rentalDAO = rentalDAO;
    this.filmDAO = filmDAO;
  }

  public Rental addRental(RentalRequest rentalRequest) {
    return rentalDAO.add(createRental(rentalRequest));
  }

  private Rental createRental(RentalRequest rentalRequest) {
    return new Rental(null,
        rentalRequest.getRentalDate(),
        rentalRequest.getRentedForDays(),
        rentalRequest.getFilms().stream().map(filmDAO::getById).collect(toList()));
  }
}