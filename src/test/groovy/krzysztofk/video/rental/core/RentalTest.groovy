package krzysztofk.video.rental.core

import krzysztofk.video.rental.api.FilmType
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import spock.lang.Specification

import java.time.ZonedDateTime

class RentalTest extends Specification {

    def "should calculate prices"() {
        given:
        def film1 = new Film(1, "Film 1", FilmType.OLD_FILM)
        def film2 = new Film(2, "Film 2", FilmType.NEW_RELEASE)
        def rental = rental(5, film1, film2)

        when:
        def pricedRental = rental.calculatePrice()

        then:
        pricedRental.id == rental.id
        pricedRental.rentalDate == rental.rentalDate
        pricedRental.rentedForDays == rental.rentedForDays
        pricedRental.films.size() == 2
        pricedRental.films[0].film == film1
        pricedRental.films[0].price == price(30)
        pricedRental.films[1].film == film2
        pricedRental.films[1].price == price(200)
        pricedRental.totalPrice == price(230)
    }

    private static def rental(int rentedForDays, Film... films) {
        new Rental(1, ZonedDateTime.now(), rentedForDays, films.toList())
    }

    private static def price(BigDecimal amount) {
        Money.of(CurrencyUnit.of("SEK"), amount)
    }
}