package krzysztofk.video.rental.core

import krzysztofk.video.rental.api.FilmType
import spock.lang.Specification

class BonusPointsCalculatorTest extends Specification {

    def "should calculate bonus points by film type"() {
        expect:
        BonusPointsCalculator.calculateBonusPoints(filmType) == expectedBonusPoints

        where:
        filmType              | expectedBonusPoints
        FilmType.OLD_FILM     | 1
        FilmType.REGULAR_FILM | 1
        FilmType.NEW_RELEASE  | 2
    }
}
