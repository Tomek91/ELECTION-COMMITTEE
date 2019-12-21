package pl.com.app.parsers.json;

import pl.com.app.dto.PoliticalPartyDTO;

import java.util.List;


public class PoliticalPartyConverter extends JsonConverter<List<PoliticalPartyDTO>> {
    public PoliticalPartyConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
