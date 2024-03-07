package pl.mt.mortalis.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class NotBadWordsValidator implements ConstraintValidator<NotBadWords, String> {
    private Set<String> badWords;

    @Override
    public void initialize(NotBadWords constraintAnnotation) {
        badWords = new BadWordsFileReader().readFile();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !containsBadWords(value);
    }

    private boolean containsBadWords(String text) {
        String textLowerCase = text.toLowerCase();
        return badWords
                .stream()
                .anyMatch(textLowerCase::contains);
    }
}
