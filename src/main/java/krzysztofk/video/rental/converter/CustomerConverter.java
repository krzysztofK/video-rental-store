package krzysztofk.video.rental.converter;

import krzysztofk.video.rental.api.Customer;

import static java.util.Optional.ofNullable;

public class CustomerConverter {

  public static Customer toApi(krzysztofk.video.rental.core.Customer customer) {
    return new Customer(
        customer.getId(),
        customer.getName(),
        customer.getSurname(),
        customer.getBonusPoints());
  }

  public static krzysztofk.video.rental.core.Customer toCore(Customer customer) {
    return new krzysztofk.video.rental.core.Customer(
        customer.getName(),
        customer.getSurname(),
        ofNullable(customer.getBonusPoints()).orElse(0));
  }
}
