package pl.com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CandidateResultDTO {
    private CandidateDTO candidateDTO;
    private Integer numberOfVotes;
    private BigDecimal numberOfVotesInPercent;
}
