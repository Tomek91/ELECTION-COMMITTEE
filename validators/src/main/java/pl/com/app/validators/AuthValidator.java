package pl.com.app.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.com.app.dto.AuthDTO;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.ETour;
import pl.com.app.service.ElectionService;
import pl.com.app.service.ResultService;
import pl.com.app.service.TokenService;
import pl.com.app.service.VoterService;

@Component
@RequiredArgsConstructor
public class AuthValidator implements Validator {
    private final TokenService tokenService;
    private final ElectionService electionService;
    private final VoterService voterService;
    private final ResultService resultService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(AuthDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o == null) {
            throw new MyException(ExceptionCode.VALIDATION, "OBJECT IS NULL");
        }

        AuthDTO authDTO = (AuthDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "token", "token", "token is not correct");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pesel", "pesel", "pesel is not correct");

        if (!tokenService.verifyCredentials(authDTO)) {
            errors.reject("credentials", "CREDENTIALS ARE NOT CORRECT");
        } else {
            boolean electionSecondTour = electionService.isActiveElection(ETour.SECOND_TOUR);
            if (!voterService.verifyIsVoterCanVote(electionSecondTour ? ETour.SECOND_TOUR : ETour.FIRST_TOUR, authDTO)){
                errors.reject("verifyIsVoterCanVote", "YOU CAN'T VOTE SECOND TIME");
            }
            if (electionSecondTour){
                if (resultService.isWinnerInFirstTour(authDTO.getPesel())){
                    errors.reject("verifyIsVoterCanVote", "YOU CAN'T VOTE - IN YOUR CONSTITUENCY CANDIDATE WIN IN FIRST TOUR");
                }
            }
        }
    }
}