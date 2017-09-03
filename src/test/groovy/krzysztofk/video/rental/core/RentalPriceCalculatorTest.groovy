package krzysztofk.video.rental.core

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import spock.lang.Specification

import static krzysztofk.video.rental.api.FilmType.*
import static krzysztofk.video.rental.core.RentalPriceCalculator.calculatePrice
import static krzysztofk.video.rental.core.RentalPriceCalculator.calculateSurcharge

class RentalPriceCalculatorTest extends Specification {

    def "should calculate price for new release film"() {
        expect:
        calculatePrice(NEW_RELEASE, rentalForDays) == price(expectedPrice)

        where:
        rentalForDays | expectedPrice
        1             | 40
        2             | 80
        5             | 200
    }

    def "should calculate price for regular films"() {
        expect:
        calculatePrice(REGULAR_FILM, rentalForDays) == price(expectedPrice)

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
        calculatePrice(OLD_FILM, rentalForDays) == price(expectedPrice)

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

    def "should calculate surcharge"() {
        expect:
        calculateSurcharge(OLD_FILM, rentalForDays, returnAfterDays) == price(expectedSurcharge)

        where:
        rentalForDays | returnAfterDays || expectedSurcharge
        1             | 5               || 0
        1             | 6               || 30
        3             | 7               || 60
        5             | 7               || 60
        5             | 9               || 120
    }

    private static def price(BigDecimal amount) {
        Money.of(CurrencyUnit.of("SEK"), amount)
    }
}