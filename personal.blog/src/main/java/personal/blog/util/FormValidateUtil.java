package personal.blog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import personal.blog.form.FormAlert;

public class FormValidateUtil {

    public static final Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static final <T> List<FormAlert> validate(T validateObj) {

        /* 将类型装载效验 */
        Set<ConstraintViolation<T>> set = getValidator().validate(validateObj);

        List<FormAlert> resultList = new ArrayList<FormAlert>();
        if (set.isEmpty()) {
            return resultList;
        }

        for (ConstraintViolation<T> constraintViolation : set) {
            FormAlert fa = new FormAlert();
            fa.setName(constraintViolation.getPropertyPath().toString());
            fa.setValue(constraintViolation.getMessage());
            resultList.add(fa);
        }

        return resultList;
    }
}
