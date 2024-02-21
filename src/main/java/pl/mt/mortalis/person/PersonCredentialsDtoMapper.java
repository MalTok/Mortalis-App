package pl.mt.mortalis.person;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonCredentialsDtoMapper {
    public PersonCredentialsDto map(Person person) {
        String email = person.getEmail();
        String password = person.getPassword();
        Set<String> roles = person.getRoles()
                .stream()
                .map(PersonRole::getRole)
                .map(Enum::name)
                .collect(Collectors.toSet());
        return new PersonCredentialsDto(email, password, roles);
    }
}
