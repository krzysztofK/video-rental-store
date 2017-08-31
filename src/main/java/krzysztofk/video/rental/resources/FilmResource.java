package krzysztofk.video.rental.resources;

import krzysztofk.video.rental.api.Film;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/films")
@Produces(MediaType.APPLICATION_JSON)
public class FilmResource {

  @POST
  public Film saveFilm(@Valid Film film) {
    return film;
  }
}