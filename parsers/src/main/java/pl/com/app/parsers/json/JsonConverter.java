package pl.com.app.parsers.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.LocalDateAdapter;
import pl.com.app.parsers.LocalDateTimeAdapter;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public abstract class JsonConverter<T> {

    private final String jsonFilename;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    public void toJson(final T element) {
        try (FileWriter fileWriter = new FileWriter(jsonFilename)) {
            if (element == null) {
                throw new NullPointerException("ELEMENT IS NULL");
            }
            gson.toJson(element, fileWriter);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.FILE_IO, e.getMessage());
        }
    }

    public Optional<T> fromJson() {
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(jsonFilename), StandardCharsets.UTF_8)) {
            return Optional.of(gson.fromJson(inputStreamReader, type));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.FILE_IO, e.getMessage());
        }
    }
}
