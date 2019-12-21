package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.com.app.model.enums.EEducation;
import pl.com.app.model.enums.EGender;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoterDTO {
    private Long id;
    //@APeselUnique()
    @PESEL(message = "PESEL is not correct")
    private String pesel;
    @NotNull(message = "Gender is not correct")
    private EGender eGender;
    private EEducation eEducation;
    private ConstituencyDTO constituencyDTO;
    private Boolean isVoteInFirstTour;
    private Boolean isVoteInSecondTour;
}
