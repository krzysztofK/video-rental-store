package krzysztofk.video.rental.resources;

import krzysztofk.video.rental.api.Film;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/films")
@Produces(MediaType.APPLICATION_JSON)
public class FilmResource {

  @POST
  public Response saveFilm(@Valid Film film) {
    return Response.status(Response.Status.CREATED).entity(film).build();
  }
}