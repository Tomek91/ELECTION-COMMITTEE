package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private Long id;
    @NotNull(message = "token is not correct")
    private String token;
    @Future(message = "expirationDate is not correct")
    private LocalDateTime expirationDate;
    private VoterDTO voterDTO;
}
