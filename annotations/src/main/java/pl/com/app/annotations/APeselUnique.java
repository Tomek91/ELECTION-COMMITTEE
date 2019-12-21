package pl.com.app.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeselUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface APeselUnique {

    String message() default "PESEL UNIQUE VALIDATION ERROR";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
