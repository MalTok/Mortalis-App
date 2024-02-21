package pl.mt.mortalis.person;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final PersonService personService;

    public DefaultUserDetailsService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personService.findCredentialsByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
    }

    private UserDetails createUserDetails(PersonCredentialsDto credentials) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(credentials.getEmail())
                .password(credentials.getPassword())
                .authorities(credentials.getRoles().toArray(String[]::new))
                .build();
    }
}