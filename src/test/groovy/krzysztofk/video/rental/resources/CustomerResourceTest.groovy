package krzysztofk.video.rental.resources

import io.dropwizard.testing.junit.ResourceTestRule
import krzysztofk.video.rental.api.customers.Customer
import krzysztofk.video.rental.dao.CustomerDAO
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Entity

class CustomerResourceTest extends Specification {

    @Shared
    def customerDAO = Mock(CustomerDAO)

    @Shared
    @ClassRule
    ResourceTestRule resources = ResourceTestRule.builder().addResource(new CustomerResource(customerDAO)).build()

    def setupSpec() {
        customerDAO.add(_) >> { krzysztofk.video.rental.core.customers.Customer customer ->
            new krzysztofk.video.rental.core.customers.Customer(id: 1, name: customer.name, surname: customer.surname, bonusPoints: customer.bonusPoints)
        }
        customerDAO.getById(100) >> customer100
    }

    def "should add customer resource"() {
        given:
        def customerToAdd = new Customer(null, "krzysztof", "K", null)

        when:
        def response = resources.target("customers").request().post(Entity.json(customerToAdd))

        then:
        response.status == 201
        def responseCustomer = response.readEntity(Customer)
        responseCustomer.id == 1
        responseCustomer.name == "krzysztof"
        responseCustomer.surname == "K"
        responseCustomer.bonusPoints == 0
    }

    def "should get customer by id"() {
        when:
        def response = resources.target("/customers/100").request().get()

        then:
        response.status == 200
        def responseCustomer = response.readEntity(Customer)
        responseCustomer.id == customer100.id
        responseCustomer.name == customer100.name
        responseCustomer.surname == customer100.surname
        responseCustomer.bonusPoints == customer100.bonusPoints

    }

    private static def customer100 =
            new krzysztofk.video.rental.core.customers.Customer(
                    id: 100,
                    name: "Some Name",
                    surname: "Some Surname",
                    bonusPoints: 50)
}