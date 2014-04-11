package ch.steponline.service;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 09.04.14
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class ValidatorService {
    private static Validator validator;

    public ValidatorService() {}

    public static <E> Set<ConstraintViolation<E>> validate(E object) {
        Set<ConstraintViolation<E>> constraintViolations =
                getValidator().validate(object);
        return constraintViolations;
    }

    private static Validator getValidator() {
        if (validator==null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        return validator;
    }
}
