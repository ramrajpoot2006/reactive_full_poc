package com.accenture.next.customerapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.accenture.next.customerapi.validator.EnumValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumPattern {
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  Class<? extends Enum<?>> targetClassType();
  String message() default "";
}
