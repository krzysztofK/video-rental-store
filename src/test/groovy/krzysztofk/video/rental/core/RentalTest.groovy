package krzysztofk.video.rental.core

import krzysztofk.video.rental.api.films.FilmType
import krzysztofk.video.rental.api.rentals.returns.FilmsReturn
import krzysztofk.video.rental.core.films.Film
import krzysztofk.video.rental.core.rentals.FilmRental
import krzysztofk.video.rental.core.rentals.Rental
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import spock.lang.Specification

import java.time.ZonedDateTime

class RentalTest extends Specification {

    def "should calculate prices"() {
        given:
        def filmRental1 = filmRental(1, FilmType.OLD_FILM, 4)
        def filmRental2 = filmRental(2, FilmType.NEW_RELEASE, 5)
        def rental = rental(filmRental1, filmRental2)

        when:
        def pricedRental = rental.calculatePrice()

        then:
        pricedRental.id == rental.id
        pricedRental.rentalDate == rental.rentalDate
        pricedRental.films.size() == 2
        pricedRental.films[0].film == filmRental1.film
        pricedRental.films[0].price == price(30)
        pricedRental.films[1].film == filmRental2.film
        pricedRental.films[1].price == price(200)
        pricedRental.totalPrice == price(230)
        pricedRental.totalBonusPoints == 3
    }

    def "should calculate return price"() {
        given:
        def lateReturnedFilm = filmRental(1, FilmType.OLD_FILM, 5)
        def onTimeReturnedFilm = filmRental(2, FilmType.NEW_RELEASE, 6)
        def notReturnedFilm = filmRental(3, FilmType.NEW_RELEASE, 2)
        def rental = rental(lateReturnedFilm, onTimeReturnedFilm, notReturnedFilm)
        def returnDate = rentalDate.plusHours(125) // above 5 days

        when:
        def pricedReturn = rental.calculateReturnPrice(new FilmsReturn(returnDate, [1, 2]))

        then:
        pricedReturn.surcharges.size() == 2
        pricedReturn.surcharges[0].film.id == 1
        pricedReturn.surcharges[0].extraDays == 1
        pricedReturn.surcharges[0].surcharge == price(30)
        pricedReturn.surcharges[1].film.id == 2
        pricedReturn.surcharges[1].extraDays == 0
        pricedReturn.surcharges[1].surcharge == price(0)
        pricedReturn.totalLateCharge == price(30)
    }

    private static def filmRental(int filmId, FilmType type, int rentedForDays) {
        new FilmRental(rentedForDays, new Film(filmId, "someTitle", type))
    }

    private static def rental(FilmRental... films) {
        new Rental(1, rentalDate, films.toList())
    }

    private static def price(BigDecimal amount) {
        Money.of(CurrencyUnit.of("SEK"), amount)
    }

    private static ZonedDateTime rentalDate = ZonedDateTime.now()
}