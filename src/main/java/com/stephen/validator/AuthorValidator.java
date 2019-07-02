/**
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-validation-example/
 */
package com.stephen.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class AuthorValidator implements ConstraintValidator<Author, String>{
	List<String> authors = Arrays.asList("Santideva", "Marie Kondo", "Martin Fowler", "stephen");
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return authors.contains(value);
	}
}
