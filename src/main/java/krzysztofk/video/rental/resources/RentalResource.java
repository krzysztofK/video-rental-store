package krzysztofk.video.rental.resources;

import io.dropwizard.hibernate.UnitOfWork;
import krzysztofk.video.rental.api.Rental;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rentals")
@Produces(MediaType.APPLICATION_JSON)
public class RentalResource {

  @POST
  @UnitOfWork
  public Response saveRental(@Valid Rental rental) {
    return Response.status(Response.Status.CREATED).entity(rental).build();
  }
}