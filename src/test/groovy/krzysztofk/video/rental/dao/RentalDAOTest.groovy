package krzysztofk.video.rental.dao

import io.dropwizard.testing.junit.DAOTestRule
import krzysztofk.video.rental.dao.entitites.Film
import krzysztofk.video.rental.api.FilmType
import krzysztofk.video.rental.dao.entitites.Rental
import org.junit.Rule
import spock.lang.Specification

import java.time.ZonedDateTime
import java.util.concurrent.Callable

class RentalDAOTest extends Specification {

    @Rule
    DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(Film).addEntityClass(Rental).build()
    def filmDAO = new FilmDAO(database.sessionFactory)
    def rentalDAO = new RentalDAO(database.sessionFactory)

    def "should add rental"() {
        given:
        def addedFilm = database.inTransaction addFilm(film)
        def rentalToAdd = new Rental(rentalDate: rentalDate, rentedForDays: 10, films: [addedFilm])

        when:
        def addedRental = database.inTransaction addRental(rentalToAdd)

        then:
        addedRental.id != null
        addedRental.rentalDate == rentalDate
        addedRental.rentedForDays == 10
        addedRental.films == [film]
    }

    def "should find rental by id"() {
        given:
        def addedFilm = database.inTransaction addFilm(film)
        def rentalToAdd = new Rental(rentalDate: rentalDate, rentedForDays: 10, films: [addedFilm])
        def addedRental = database.inTransaction addRental(rentalToAdd)

        when:
        def foundRental = rentalDAO.findById(addedRental.id)

        then:
        foundRental.id == addedRental.id
        foundRental.rentalDate == rentalDate
        foundRental.rentedForDays == 10
        foundRental.films == [film]
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
}