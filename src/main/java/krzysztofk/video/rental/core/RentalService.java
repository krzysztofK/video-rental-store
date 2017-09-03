package krzysztofk.video.rental.core;

import krzysztofk.video.rental.api.FilmToRent;
import krzysztofk.video.rental.api.RentalRequest;
import krzysztofk.video.rental.dao.FilmDAO;
import krzysztofk.video.rental.dao.RentalDAO;

import static java.util.stream.Collectors.toList;

public class RentalService {

  private final RentalDAO rentalDAO;
  private final FilmDAO filmDAO;

  public RentalService(RentalDAO rentalDAO, FilmDAO filmDAO) {
    this.rentalDAO = rentalDAO;
    this.filmDAO = filmDAO;
  }

  public PricedRental addRental(RentalRequest rentalRequest) {
    return rentalDAO.add(createRental(rentalRequest)).calculatePrice();
  }

  private Rental createRental(RentalRequest rentalRequest) {
    return new Rental(null,
        rentalRequest.getRentalDate(),
        rentalRequest.getFilms().stream().map(this::createFilmRental).collect(toList()));
  }

  private FilmRental createFilmRental(FilmToRent filmToRent) {
    return new FilmRental(filmToRent.getRentedForDays(), filmDAO.getById(filmToRent.getFilmId()));
  }
}