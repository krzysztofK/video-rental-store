package krzysztofk.video.rental.resources;

import io.dropwizard.hibernate.UnitOfWork;
import krzysztofk.video.rental.api.FilmsReturn;
import krzysztofk.video.rental.api.RentalRequest;
import krzysztofk.video.rental.core.PricedReturn;
import krzysztofk.video.rental.core.RentalService;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rentals")
@Produces(MediaType.APPLICATION_JSON)
public class RentalResource {

  private final RentalService rentalService;

  public RentalResource(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @POST
  @UnitOfWork
  public Response addRental(@Valid RentalRequest rentalRequest) {
    return Response.status(Response.Status.CREATED).entity(rentalService.addRental(rentalRequest)).build();
  }

  @POST
  @Path("/{id}/return")
  @UnitOfWork
  public PricedReturn returnFilms(@PathParam("id") Integer rentalId, @Valid FilmsReturn filmsReturn) {
    return rentalService.priceReturn(rentalId, filmsReturn);
  }
}