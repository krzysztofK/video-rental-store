package krzysztofk.video.rental.core;

import krzysztofk.video.rental.dao.entitites.Film;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class RentalPriceCalculator {

  private static final Money PREMIUM_PRICE = Money.of(CurrencyUnit.of("SEK"), 40);
  private static final Money BASE_PRICE = Money.of(CurrencyUnit.of("SEK"), 30);

  public static Money calculatePrice(Film film, int rentalForDays) {
    switch (film.getType()) {
      case NEW_RELEASE:
        return PREMIUM_PRICE.multipliedBy(rentalForDays);
      case REGULAR_FILM:
        return calculateBasicPrice(rentalForDays, 3);
      case OLD_FILM:
        return calculateBasicPrice(rentalForDays, 5);
      default:
        throw new UnsupportedOperationException();
    }
  }

  private static Money calculateBasicPrice(int rentalForDays, int daysWithConstPrice) {
    if (rentalForDays <= daysWithConstPrice) {
      return BASE_PRICE;
    } else {
      return BASE_PRICE.plus(BASE_PRICE.multipliedBy(rentalForDays - daysWithConstPrice));
    }
  }
}