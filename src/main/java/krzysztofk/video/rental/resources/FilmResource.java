package krzysztofk.video.rental.resources;

import io.dropwizard.hibernate.UnitOfWork;
import krzysztofk.video.rental.api.films.Film;
import krzysztofk.video.rental.dao.FilmDAO;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static krzysztofk.video.rental.converter.FilmConverter.toApi;
import static krzysztofk.video.rental.converter.FilmConverter.toCore;

@Path("/films")
@Produces(MediaType.APPLICATION_JSON)
public class FilmResource {

  private final FilmDAO filmDAO;

  public FilmResource(FilmDAO filmDAO) {
    this.filmDAO = filmDAO;
  }

  @POST
  @UnitOfWork
  public Response addFilm(@Valid Film film) {
    return Response.status(Response.Status.CREATED).entity(toApi(filmDAO.add(toCore(film)))).build();
  }

  @GET
  @Path("/{id}")
  @UnitOfWork
  public Film getFilm(@PathParam("id") int id) {
    return toApi(filmDAO.getById(id));
  }
}