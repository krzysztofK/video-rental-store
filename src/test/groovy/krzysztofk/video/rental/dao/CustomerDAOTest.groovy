package krzysztofk.video.rental.dao

import io.dropwizard.testing.junit.DAOTestRule
import krzysztofk.video.rental.core.customers.Customer
import org.junit.Rule
import spock.lang.Specification

import java.util.concurrent.Callable

class CustomerDAOTest extends Specification {

    @Rule
    DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(Customer).build()
    def customerDAO = new CustomerDAO(database.sessionFactory)

    def "should add customer"() {
        given:
        def customerToAdd = new Customer("krzysztof", "K", 0)

        when:
        def addedCustomer = database.inTransaction addCustomer(customerToAdd)

        then:
        addedCustomer.id != null
        addedCustomer.name == "krzysztof"
        addedCustomer.surname == "K"
        addedCustomer.bonusPoints == 0
    }

    def "should get customer by id"() {
        given:
        def customerToAdd = new Customer("krzysztof", "K", 0)
        def addedCustomer = database.inTransaction addCustomer(customerToAdd)

        when:
        def foundCustomer = customerDAO.getById(addedCustomer.id)

        then:
        foundCustomer.id == addedCustomer.id
        foundCustomer.name == "krzysztof"
    }

    def addCustomer(Customer customer) {
        new Callable<Customer>() {
            @Override
            Customer call() throws Exception {
                return customerDAO.add(customer)
            }
        }
    }
}