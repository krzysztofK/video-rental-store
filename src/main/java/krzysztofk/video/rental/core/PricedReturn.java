package krzysztofk.video.rental.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.money.Money;

import java.util.List;

public class PricedReturn {

  private final List<FilmSurcharge> surcharges;
  private final Money totalLateCharge;

  @JsonCreator
  public PricedReturn(@JsonProperty("surcharges") List<FilmSurcharge> surcharges,
                      @JsonProperty("totalLateCharge") Money totalLateCharge) {
    this.surcharges = surcharges;
    this.totalLateCharge = totalLateCharge;
  }

  public List<FilmSurcharge> getSurcharges() {
    return surcharges;
  }

  public Money getTotalLateCharge() {
    return totalLateCharge;
  }
}