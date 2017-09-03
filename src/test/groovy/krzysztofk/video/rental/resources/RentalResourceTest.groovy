package krzysztofk.video.rental.resources

import io.dropwizard.testing.junit.ResourceTestRule
import krzysztofk.video.rental.api.FilmType
import krzysztofk.video.rental.api.RentalRequest
import krzysztofk.video.rental.core.Film
import krzysztofk.video.rental.core.PricedFilm
import krzysztofk.video.rental.core.PricedRental
import krzysztofk.video.rental.core.RentalService
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Entity
import java.time.ZonedDateTime

import static krzysztofk.video.rental.api.MoneySerialization.serializationModule

class RentalResourceTest extends Specification {

    @Shared
    def rentalService = Mock(RentalService)

    @Shared
    @ClassRule
    ResourceTestRule resources = ResourceTestRule.builder().addResource(new RentalResource(rentalService)).build()

    def setupSpec() {
        resources.objectMapper.registerModule(serializationModule())
        rentalService.addRental(_) >> { RentalRequest rentalRequest ->
            new PricedRental(1,
                    rentalRequest.rentalDate,
                    rentalRequest.rentedForDays,
                    rentalRequest.films.collect { new PricedFilm(new Film(it, "title", FilmType.REGULAR_FILM), Money.of(CurrencyUnit.EUR, 1)) },
                    Money.of(CurrencyUnit.EUR, 10))
        }
    }

    def "should add rental"() {
        given:
        def rentalToAdd = new RentalRequest(rentalDate, 10, [2, 4, 9])

        when:
        def response = resources.target("/rentals").request().post(Entity.json(rentalToAdd))

        then:
        response.status == 201
        def responseRental = response.readEntity(PricedRental)
        responseRental.rentalDate.isEqual(rentalDate)
        responseRental.rentedForDays == 10
        responseRental.films*.film.id == [2, 4, 9]
        responseRental.films*.price*.amount == [1.0, 1.0, 1.0]
        responseRental.totalPrice == Money.of(CurrencyUnit.EUR, 10)
    }

    ZonedDateTime rentalDate = ZonedDateTime.now()
}