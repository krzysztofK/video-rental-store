package krzysztofk.video.rental.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.money.Money;

import java.io.IOException;

public class MoneySerialization {

  private static class MoneySerializer extends StdSerializer<Money> {
    private MoneySerializer() {
      super(Money.class);
    }

    @Override
    public void serialize(Money value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
      jgen.writeString(value.toString());
    }
  }

  private static class MoneyDeserializer extends StdDeserializer<Money> {
    private MoneyDeserializer() {
      super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      return Money.parse(jp.readValueAs(String.class));
    }
  }

  public static SimpleModule serializationModule() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Money.class, new MoneySerialization.MoneySerializer());
    module.addDeserializer(Money.class, new MoneySerialization.MoneyDeserializer());
    return module;
  }
}
