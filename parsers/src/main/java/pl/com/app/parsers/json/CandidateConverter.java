package pl.com.app.parsers.json;

import pl.com.app.dto.CandidateDTO;

import java.util.List;

public class CandidateConverter extends JsonConverter<List<CandidateDTO>> {
    public CandidateConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
