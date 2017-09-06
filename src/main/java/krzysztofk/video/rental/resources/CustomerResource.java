package krzysztofk.video.rental.resources;

import io.dropwizard.hibernate.UnitOfWork;
import krzysztofk.video.rental.api.customers.Customer;
import krzysztofk.video.rental.dao.CustomerDAO;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static krzysztofk.video.rental.converter.CustomerConverter.toCore;
import static krzysztofk.video.rental.converter.CustomerConverter.toApi;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

  private final CustomerDAO customerDAO;

  public CustomerResource(CustomerDAO customerDAO) {
    this.customerDAO = customerDAO;
  }

  @POST
  @UnitOfWork
  public Response addCustomer(@Valid Customer customer) {
    return Response.status(Response.Status.CREATED).entity(toApi(customerDAO.add(toCore(customer)))).build();
  }

  @GET
  @Path("/{id}")
  @UnitOfWork
  public Customer getCustomerById(@PathParam("id") int id) {
    return toApi(customerDAO.getById(id));
  }
}
