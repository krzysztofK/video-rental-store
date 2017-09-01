package krzysztofk.video.rental.dao

import io.dropwizard.testing.junit.DAOTestRule
import krzysztofk.video.rental.api.Film
import krzysztofk.video.rental.api.FilmType
import org.junit.Rule
import spock.lang.Specification

import java.util.concurrent.Callable

class FilmDAOTest extends Specification {

    @Rule
    DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(Film).build()
    def filmDAO = new FilmDAO(database.sessionFactory)

    def "should add film"() {
        given:
        def filmToAdd = new Film(title: "Some film", type: FilmType.OLD_FILM)

        when:
        def addedFilm = database.inTransaction addFilm(filmToAdd)

        then:
        addedFilm.id != null
        addedFilm.title == "Some film"
        addedFilm.type == FilmType.OLD_FILM
    }

    def "should find film by id"() {
        given:
        def addedFilm = database.inTransaction addFilm(new Film(title: "Some film", type: FilmType.OLD_FILM))

        when:
        def foundFilm = filmDAO.getById(addedFilm.id)

        then:
        foundFilm.id == addedFilm.id
        foundFilm.title == addedFilm.title
    }

    def addFilm(Film film) {
        new Callable<Film>() {
            @Override
            Film call() throws Exception {
                return filmDAO.add(film)
            }
        }
    }
}
