package krzysztofk.video.rental.core.rentals;

import krzysztofk.video.rental.api.films.FilmType;

public class BonusPointsCalculator {

  private static final int NEW_RELEASE_BONUS = 2;
  private static final int OTHER_FILMS_BONUS = 1;

  public static int calculateBonusPoints(FilmType filmType) {
    switch (filmType) {
      case NEW_RELEASE:
        return NEW_RELEASE_BONUS;
      default:
        return OTHER_FILMS_BONUS;
    }
  }
}
