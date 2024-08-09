package com.ziyao.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NameOfEnumValidator implements ConstraintValidator<NameOfEnum, CharSequence> {

    private List<String> acceptedNames;

    @Override
    public void initialize(NameOfEnum annotation) {
        acceptedNames = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedNames.contains(value.toString());
    }
}
