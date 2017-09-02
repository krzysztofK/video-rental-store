package krzysztofk.video.rental.resources

import io.dropwizard.testing.junit.ResourceTestRule
import krzysztofk.video.rental.dao.entitites.Film
import krzysztofk.video.rental.api.FilmType
import krzysztofk.video.rental.api.RentalRequest
import krzysztofk.video.rental.core.RentalService
import krzysztofk.video.rental.dao.entitites.Rental
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Entity
import java.time.ZonedDateTime

class RentalResourceTest extends Specification {

    @Shared
    def rentalService = Mock(RentalService)

    @Shared
    @ClassRule
    ResourceTestRule resources = ResourceTestRule.builder().addResource(new RentalResource(rentalService)).build()

    def setupSpec() {
        rentalService.addRental(_) >> { RentalRequest rentalRequest ->
            new Rental(id: 1,
                    rentalDate: rentalRequest.rentalDate,
                    rentedForDays: rentalRequest.rentedForDays,
                    films: rentalRequest.films.collect { new Film(it, "title", FilmType.REGULAR_FILM) })
        }
    }

    def "should add rental"() {
        given:
        def rentalToAdd = new RentalRequest(rentalDate, 10, [2, 4, 9])

        when:
        def response = resources.target("/rentals").request().post(Entity.json(rentalToAdd))

        then:
        response.status == 201
        def responseRental = response.readEntity(Rental)
        responseRental.rentalDate.isEqual(rentalDate)
        responseRental.rentedForDays == 10
        responseRental.films*.id == [2, 4, 9]
    }

    ZonedDateTime rentalDate = ZonedDateTime.now()
}