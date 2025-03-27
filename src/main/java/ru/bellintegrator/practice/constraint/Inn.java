package ru.bellintegrator.practice.constraint;

import javax.validation.*;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {Inn.InnValidator.class})
public @interface Inn {
    /**
     * @return the error message template
     */
    String message() default "{constraint.Inn.message}";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default { };

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default { };

    class InnValidator implements ConstraintValidator<Inn, String> {
        private Inn constraint;

        @Override
        public void initialize(Inn constraint) {
            this.constraint = constraint;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.length() != 10 && value.length() != 12 || !value.matches("\\d+"))
                return false;

            Integer[] numbers = value.chars().mapToObj(c -> Character.digit((char) c, 10)).toArray(Integer[]::new);

            if (numbers.length == 10) {
                Integer n9 = (2*numbers[0] + 4*numbers[1] + 10*numbers[2] +
                        3*numbers[3] + 5*numbers[4] +  9*numbers[5] +
                        4*numbers[6] + 6*numbers[7] +  8*numbers[8]) % 11 % 10;

                return numbers[9].equals(n9);
            }
            else if (numbers.length == 12) {
                Integer n10 = (7*numbers[0] + 2*numbers[1] + 4*numbers[2] +
                        10*numbers[3] + 3*numbers[4] + 5*numbers[5] +
                        9*numbers[6] + 4*numbers[7] + 6*numbers[8] +
                        8*numbers[9]) % 11 % 10;

                Integer n11 = (3*numbers[0] +  7*numbers[1] + 2*numbers[2] +
                        4*numbers[3] + 10*numbers[4] + 3*numbers[5] +
                        5*numbers[6] +  9*numbers[7] + 4*numbers[8] +
                        6*numbers[9] +  8*numbers[10]) % 11 % 10;

                return numbers[11].equals(n11) && numbers[10].equals(n10);
            }

            return false;
        }
    }
}
