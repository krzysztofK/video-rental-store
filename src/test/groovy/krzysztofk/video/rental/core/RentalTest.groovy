package krzysztofk.video.rental.core

import krzysztofk.video.rental.api.FilmType
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
        pricedRental.films[0].film == filmRental1.@film
        pricedRental.films[0].price == price(30)
        pricedRental.films[1].film == filmRental2.@film
        pricedRental.films[1].price == price(200)
        pricedRental.totalPrice == price(230)
    }

    private static def filmRental(int filmId, FilmType type, int rentedForDays) {
        new FilmRental(rentedForDays, new Film(filmId, "someTitle", type))
    }

    private static def rental(FilmRental... films) {
        new Rental(1, ZonedDateTime.now(), films.toList())
    }

    private static def price(BigDecimal amount) {
        Money.of(CurrencyUnit.of("SEK"), amount)
    }
}