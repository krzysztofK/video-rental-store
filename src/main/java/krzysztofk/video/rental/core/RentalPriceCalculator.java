package krzysztofk.video.rental.core;

import krzysztofk.video.rental.api.FilmType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class RentalPriceCalculator {

  private static final Money PREMIUM_PRICE = Money.of(CurrencyUnit.of("SEK"), 40);
  private static final Money BASE_PRICE = Money.of(CurrencyUnit.of("SEK"), 30);

  public static Money calculatePrice(FilmType filmType, int rentedForDays) {
    switch (filmType) {
      case NEW_RELEASE:
        return PREMIUM_PRICE.multipliedBy(rentedForDays);
      case REGULAR_FILM:
        return calculateBasicPrice(rentedForDays, 3);
      case OLD_FILM:
        return calculateBasicPrice(rentedForDays, 5);
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

  public static Money calculateSurcharge(FilmType filmType, int rentedForDays, int returnedAfterDays) {
    return calculatePrice(filmType, returnedAfterDays).minus(calculatePrice(filmType, rentedForDays));
  }


  static Money zero() {
    return Money.of(CurrencyUnit.of("SEK"), 0);
  }
}