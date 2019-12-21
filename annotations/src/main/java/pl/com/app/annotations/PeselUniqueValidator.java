package pl.com.app.annotations;

import lombok.RequiredArgsConstructor;
import pl.com.app.service.VoterService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class PeselUniqueValidator implements ConstraintValidator<APeselUnique, String> {
   private final VoterService voterService;

   public void initialize(APeselUnique constraint) {
   }

   public boolean isValid(String pesel, ConstraintValidatorContext context) {
      if (pesel == null || voterService.isNotUniquePesel(pesel)) {
         return false;
      }
      return true;
   }
}
