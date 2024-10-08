package com.accenture.next.customerapi.validator;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.util.Strings;

import com.accenture.next.customerapi.annotation.EnumPattern;

public class EnumValidator implements ConstraintValidator<EnumPattern, String> {

  private Set<String> allowedValues;

  @Override
  public void initialize(EnumPattern targetEnum) {
    Class<? extends Enum> enumSelected = targetEnum.targetClassType();
    allowedValues = (Set<String>) EnumSet.allOf(enumSelected).stream()
        .map(Object::toString).collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (Strings.isBlank(value)) {
      return true;
    }
    return allowedValues.contains(value);
  }

}
