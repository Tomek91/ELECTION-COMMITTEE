package pl.com.app.parsers.json;

import pl.com.app.dto.ConstituencyDTO;

import java.util.List;


public class ConstituencyConverter extends JsonConverter<List<ConstituencyDTO>> {
    public ConstituencyConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
