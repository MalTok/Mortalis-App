package pl.mt.mortalis.person.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.person.Person;
import pl.mt.mortalis.person.PersonRole;
import pl.mt.mortalis.person.dto.PersonCredentialsDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonCredentialsDtoMapper {
    public PersonCredentialsDto mapEntityToCredentialsDto(Person person) {
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
