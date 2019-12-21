package pl.com.app.parsers.json;

import pl.com.app.dto.VoterDTO;

import java.util.List;


public class VoterConverter extends JsonConverter<List<VoterDTO>> {
    public VoterConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
