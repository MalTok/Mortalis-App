package pl.mt.mortalis.person;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonCredentialsDtoMapper personCredentialsDtoMapper;

    public PersonService(PersonRepository personRepository, PersonCredentialsDtoMapper personCredentialsDtoMapper) {
        this.personRepository = personRepository;
        this.personCredentialsDtoMapper = personCredentialsDtoMapper;
    }

    public Optional<PersonCredentialsDto> findCredentialsByEmail(String email) {
        return personRepository.findByEmail(email)
                .map(personCredentialsDtoMapper::map);
    }
}
