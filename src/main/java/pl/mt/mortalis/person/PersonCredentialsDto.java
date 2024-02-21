package pl.mt.mortalis.person;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PersonCredentialsDto {
    private final String email;
    private final String password;
    private final Set<String> roles;
}
