package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.ETour;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDTO {
    private Long id;
    private ETour eTour;
    private Integer votes_number;
    private CandidateDTO candidateDTO;
}