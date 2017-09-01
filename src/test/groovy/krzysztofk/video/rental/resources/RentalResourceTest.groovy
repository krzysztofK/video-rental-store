package krzysztofk.video.rental.resources

import io.dropwizard.testing.junit.ResourceTestRule
import krzysztofk.video.rental.api.Rental
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Entity
import java.time.ZonedDateTime

class RentalResourceTest extends Specification {

    @Shared
    @ClassRule
    ResourceTestRule resources = ResourceTestRule.builder().addResource(new RentalResource()).build()

    def "should add rental"() {
        given:
        def rentalToAdd = new Rental(null, rentalDate, 10, [2, 4, 9])

        when:
        def response = resources.target("/rentals").request().post(Entity.json(rentalToAdd))

        then:
        response.status == 201
        def responseRental = response.readEntity(Rental)
        responseRental.rentalDate.isEqual(rentalDate)
        responseRental.rentedForDays == 10
        responseRental.films == [2, 4, 9]
    }

    ZonedDateTime rentalDate = ZonedDateTime.now()
}