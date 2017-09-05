package krzysztofk.video.rental.resources

import io.dropwizard.testing.junit.ResourceTestRule
import krzysztofk.video.rental.api.FilmToRent
import krzysztofk.video.rental.api.FilmsReturn
import krzysztofk.video.rental.api.RentalRequest
import krzysztofk.video.rental.core.Film
import krzysztofk.video.rental.core.FilmSurcharge
import krzysztofk.video.rental.core.PricedFilm
import krzysztofk.video.rental.core.PricedRental
import krzysztofk.video.rental.core.PricedReturn
import krzysztofk.video.rental.core.RentalService
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Entity
import java.time.ZonedDateTime

import static krzysztofk.video.rental.api.FilmType.NEW_RELEASE
import static krzysztofk.video.rental.api.FilmType.OLD_FILM
import static krzysztofk.video.rental.api.FilmType.REGULAR_FILM
import static krzysztofk.video.rental.api.MoneySerialization.serializationModule

class RentalResourceTest extends Specification {

    @Shared
    def rentalService = Mock(RentalService)

    @Shared
    @ClassRule
    ResourceTestRule resources = ResourceTestRule.builder().addResource(new RentalResource(rentalService)).build()

    @Shared
    ZonedDateTime rentalDate = ZonedDateTime.now()

    @Shared
    def rentalToAdd = new RentalRequest(
            rentalDate,
            [
                    new FilmToRent(2, 7),
                    new FilmToRent(4, 3),
                    new FilmToRent(9, 5)])

    @Shared
    def addedRental = new PricedRental(
            1,
            rentalDate,
            [
                    new PricedFilm(new Film(2, "some title", REGULAR_FILM), Money.of(CurrencyUnit.EUR, 2), 1),
                    new PricedFilm(new Film(4, "some title", OLD_FILM), Money.of(CurrencyUnit.EUR, 1), 1),
                    new PricedFilm(new Film(9, "some title", NEW_RELEASE), Money.of(CurrencyUnit.EUR, 3), 2)],
            Money.of(CurrencyUnit.EUR, 6),
            4)

    @Shared
    def filmsReturn = new FilmsReturn(
            ZonedDateTime.now(),
            [2, 4])

    @Shared
    def pricedReturn = new PricedReturn(
            [
                    new FilmSurcharge(new Film(2, "some title", REGULAR_FILM), 3, Money.of(CurrencyUnit.EUR, 10)),
                    new FilmSurcharge(new Film(4, "some title", REGULAR_FILM), 1, Money.of(CurrencyUnit.EUR, 5))
            ],
            Money.of(CurrencyUnit.EUR, 15)
    )

    def setupSpec() {
        resources.objectMapper.registerModule(serializationModule())
        rentalService.addRental(_) >> addedRental
        rentalService.priceReturn(1, _) >> pricedReturn
    }

    def "should add rental"() {
        when:
        def response = resources.target("/rentals").request().post(Entity.json(rentalToAdd))

        then:
        response.status == 201
        def responseRental = response.readEntity(PricedRental)
        responseRental.rentalDate.isEqual(rentalDate)
        responseRental.films*.film.id == [2, 4, 9]
        responseRental.films*.price*.amount == [2.0, 1.0, 3.0]
        responseRental.totalPrice == Money.of(CurrencyUnit.EUR, 6)
        responseRental.totalBonusPoints == 4
    }

    def "should calculate return"() {
        when:
        def response = resources.target("/rentals/1/return").request().post(Entity.json(filmsReturn))

        then:
        response.status == 200
        def responseReturn = response.readEntity(PricedReturn)
        responseReturn.surcharges*.film.id == [2, 4]
        responseReturn.surcharges*.extraDays == [3, 1]
        responseReturn.surcharges*.surcharge*.amount == [10.0, 5.0]
        responseReturn.totalLateCharge == Money.of(CurrencyUnit.EUR, 15)
    }
}