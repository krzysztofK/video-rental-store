package krzysztofk.video.rental.dao

import io.dropwizard.testing.junit.DAOTestRule
import krzysztofk.video.rental.core.films.Film
import krzysztofk.video.rental.api.films.FilmType
import krzysztofk.video.rental.core.rentals.FilmRental
import krzysztofk.video.rental.core.rentals.Rental
import org.junit.Rule
import spock.lang.Specification

import java.time.ZonedDateTime
import java.util.concurrent.Callable

class RentalDAOTest extends Specification {

    @Rule
    DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(Film).addEntityClass(FilmRental).addEntityClass(Rental).build()
    def filmDAO = new FilmDAO(database.sessionFactory)
    def rentalDAO = new RentalDAO(database.sessionFactory)

    def "should add rental"() {
        given:
        def addedFilm = database.inTransaction addFilm(film)
        def rentalToAdd = new Rental(rentalDate: rentalDate, films: [filmRental(addedFilm)])

        when:
        def addedRental = database.inTransaction addRental(rentalToAdd)

        then:
        addedRental.id != null
        addedRental.rentalDate == rentalDate
        addedRental.films.size() == 1
        addedRental.films[0].film == addedFilm
        addedRental.films[0].rentedForDays == 10
    }

    def "should find rental by id"() {
        given:
        def addedFilm = database.inTransaction addFilm(film)
        def rentalToAdd = new Rental(rentalDate: rentalDate, films: [filmRental(addedFilm)])
        def addedRental = database.inTransaction addRental(rentalToAdd)

        when:
        def foundRental = rentalDAO.findById(addedRental.id)

        then:
        foundRental.id == addedRental.id
        foundRental.rentalDate == rentalDate
        foundRental.films.size() == 1
        foundRental.films[0].film == addedFilm
        foundRental.films[0].rentedForDays == 10
    }

    private ZonedDateTime rentalDate = ZonedDateTime.now()

    def addFilm(Film film) {
        new Callable<Film>() {
            @Override
            Film call() throws Exception {
                return filmDAO.add(film)
            }
        }
    }

    private def addRental(Rental rental) {
        new Callable<Rental>() {
            @Override
            Rental call() throws Exception {
                return rentalDAO.add(rental)
            }
        }
    }

    def film = new Film(title: "Some film", type: FilmType.OLD_FILM)

    def filmRental(Film film) { new FilmRental(10, film) }
}