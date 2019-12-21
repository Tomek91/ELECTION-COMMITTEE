package pl.com.app.parsers.json;

import pl.com.app.dto.TokenDTO;

import java.util.List;


public class TokenConverter extends JsonConverter<List<TokenDTO>> {
    public TokenConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
