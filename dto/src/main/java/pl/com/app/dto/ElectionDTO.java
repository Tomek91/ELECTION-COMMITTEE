package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.ETour;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectionDTO {
    private Long id;
    @NotNull(message = "ElectionDateFrom must not be null")
    @FutureOrPresent(message = "ElectionDateFrom is not correct")
    private LocalDateTime electionDateFrom;
    @FutureOrPresent(message = "ElectionDateTo is not correct")
    private LocalDateTime electionDateTo;
    @NotNull(message = "ETour must not be null")
    private ETour eTour;
}
