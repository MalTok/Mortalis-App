package pl.mt.mortalis;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Gender {
    MALE("Mężczyzna"),
    FEMALE("Kobieta");

    private final String plDisplayName;

    Gender(String plDisplayName) {
        this.plDisplayName = plDisplayName;
    }

    public static Optional<Gender> findGenderByName(String name) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.name().equals(name))
                .findFirst();
    }
}
