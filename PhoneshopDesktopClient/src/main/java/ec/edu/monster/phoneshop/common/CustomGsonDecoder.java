package ec.edu.monster.phoneshop.common;

import com.google.gson.*;
import feign.gson.GsonDecoder;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class CustomGsonDecoder extends GsonDecoder {
    public CustomGsonDecoder(){
        super(new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM-dd")
                                .toFormatter();

                        return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), formatter);
                    }
                })
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext)
                        -> new JsonPrimitive(localDate.toString()))
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                                .optionalStart()
                                .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
                                .optionalEnd()
                                .toFormatter();

                        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), formatter);
                    }
                }).registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext)
                        -> new JsonPrimitive(localDateTime.toString())).create());
    }
}
