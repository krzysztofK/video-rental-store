package krzysztofk.video.rental.resources

import io.dropwizard.testing.junit.ResourceTestRule
import krzysztofk.video.rental.api.films.Film
import krzysztofk.video.rental.api.films.FilmType
import krzysztofk.video.rental.dao.FilmDAO
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Entity

class FilmResourceTest extends Specification {

    @Shared
    def filmDAO = Mock(FilmDAO)

    @Shared
    @ClassRule
    ResourceTestRule resources = ResourceTestRule.builder().addResource(new FilmResource(filmDAO)).build()

    def setupSpec() {
        filmDAO.add(_) >> { krzysztofk.video.rental.core.films.Film film ->
            new krzysztofk.video.rental.core.films.Film(id: 1, title: film.title, type: film.type)
        }
        filmDAO.getById(100) >> film100()
    }

    def "should add film resource"() {
        given:
        def filmToAdd = new Film(null, "title", FilmType.NEW_RELEASE)

        when:
        def response = resources.target("/films").request().post(Entity.json(filmToAdd))

        then:
        response.status == 201
        def responseFilm = response.readEntity(Film)
        responseFilm.id == 1
        responseFilm.title == "title"
        responseFilm.type == FilmType.NEW_RELEASE
    }

    def "should find film by id"() {
        when:
        def response = resources.target("/films/100").request().get()

        then:
        response.status == 200
        def responseFilm = response.readEntity(Film)
        responseFilm.id == film100().id
        responseFilm.title == film100().title
        responseFilm.type == film100().type
    }

    def film100() {
        new krzysztofk.video.rental.core.films.Film(id: 100, title: "film 100", type: FilmType.REGULAR_FILM)
    }
}