package krzysztofk.video.rental.resources;

import io.dropwizard.hibernate.UnitOfWork;
import krzysztofk.video.rental.api.Film;
import krzysztofk.video.rental.dao.FilmDAO;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/films")
@Produces(MediaType.APPLICATION_JSON)
public class FilmResource {

  private final FilmDAO filmDAO;

  public FilmResource(FilmDAO filmDAO) {
    this.filmDAO = filmDAO;
  }

  @POST
  @UnitOfWork
  public Response saveFilm(@Valid Film film) {
    return Response.status(Response.Status.CREATED).entity(filmDAO.add(film)).build();
  }
}