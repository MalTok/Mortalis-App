package pl.mt.mortalis.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/pics/**").permitAll()
                .requestMatchers("/scripts/**").permitAll()
                .requestMatchers("/styles/**").permitAll()
                .requestMatchers("/administracja/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/administracja/moderacja/**").hasAuthority("ROLE_MODERATOR")
                .requestMatchers("/").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/szukaj").permitAll()
                .requestMatchers("/wszystkie").permitAll()
                .requestMatchers("/strona/**").permitAll()
                .requestMatchers("/nekrolog/**").permitAll()
                .requestMatchers("/kondolencje/**").permitAll()
                .requestMatchers("/zapal-swieczke/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated());
        http.formLogin(login -> login.loginPage("/login")
                .permitAll());
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name()))
                .logoutSuccessUrl("/").permitAll()
        );
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
