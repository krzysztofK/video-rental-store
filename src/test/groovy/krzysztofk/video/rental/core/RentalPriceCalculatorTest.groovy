package krzysztofk.video.rental.core

import krzysztofk.video.rental.api.FilmType
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import spock.lang.Specification

import java.time.ZonedDateTime

import static krzysztofk.video.rental.core.RentalPriceCalculator.calculatePrice

class RentalPriceCalculatorTest extends Specification {

    def "should calculate price for new release film"() {
        expect:
        calculatePrice(newRelease(), rentalForDays) == price(expectedPrice)

        where:
        rentalForDays | expectedPrice
        1             | 40
        2             | 80
        5             | 200
    }

    def "should calculate price for regular films"() {
        expect:
        calculatePrice(regularFilm(), rentalForDays) == price(expectedPrice)

        where:
        rentalForDays | expectedPrice
        1             | 30
        2             | 30
        3             | 30
        4             | 60
        5             | 90
        6             | 120
    }

    def "should calculate price for old films"() {
        expect:
        calculatePrice(oldFilm(), rentalForDays) == price(expectedPrice)

        where:
        rentalForDays | expectedPrice
        1             | 30
        2             | 30
        3             | 30
        4             | 30
        5             | 30
        6             | 60
        7             | 90
        8             | 120
    }

    private static def Rental rental(int rentedForDays, Film... films) {
        new Rental(201, ZonedDateTime.now(), rentedForDays, films.toList())
    }

    private static def Film newRelease() {
        new Film(100, "someTitle", FilmType.NEW_RELEASE)
    }

    private static def Film regularFilm() {
        new Film(101, "someTitle", FilmType.REGULAR_FILM)
    }

    private static def Film oldFilm() {
        new Film(102, "someTitle", FilmType.OLD_FILM)
    }

    private static def price(BigDecimal amount) {
        Money.of(CurrencyUnit.of("SEK"), amount)
    }
}