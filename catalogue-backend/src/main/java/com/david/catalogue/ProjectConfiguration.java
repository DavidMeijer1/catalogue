package com.david.catalogue;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.david.catalogue.user.UserRole.ADMINISTRATOR;
import static com.david.catalogue.user.UserRole.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class ProjectConfiguration {
    static final String[] allAccounts = new String[]{ADMINISTRATOR.name(), USER.name()};
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    Function<AuthorizeHttpRequestsConfigurer<?>.AuthorizationManagerRequestMatcherRegistry,
                BiFunction<String, HttpMethod, AuthorizeHttpRequestsConfigurer<?>.AuthorizedUrl>> match = authorization ->
            (pattern, method) -> authorization.requestMatchers(new AntPathRequestMatcher(pattern, method.name()));

    Function<AuthorizeHttpRequestsConfigurer<?>.AuthorizationManagerRequestMatcherRegistry,
            Function<String, AuthorizeHttpRequestsConfigurer<?>.AuthorizedUrl>> matchAll =
            authorization -> pattern -> authorization.requestMatchers(new AntPathRequestMatcher(pattern));

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("user1Pass"))
//                .build();
//
//        UserDetails user2 = User.withUsername("user2")
//                .password(passwordEncoder().encode("user2Pass"))
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("adminPass"))
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2, admin);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("{noop}adminpass").roles("ADMIN")
//                .and()
//                .withUser("user").password("{noop}userpass").roles("USER");
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
                http.cors(Customizer.withDefaults());
                        http.csrf(AbstractHttpConfigurer:: disable);
                        http.authorizeHttpRequests((customizer) -> {
                            customizer.anyRequest().permitAll();
//                            matchAll.apply(customizer).apply("/auth/**").permitAll();
//                            match.apply(customizer).apply("/auth/register", GET).permitAll();
//                            match.apply(customizer).apply("/auth/login", GET).permitAll();
//                            match.apply(customizer).apply("/api/v1/users", GET).permitAll();
//                            match.apply(customizer).apply("/api/v1/users/**", GET).permitAll();
//                            match.apply(customizer).apply("/api/v1/users/update/**", PATCH).hasAnyRole(allAccounts);
//                            match.apply(customizer).apply("/api/v1/users/new/*", POST).permitAll();
//                            match.apply(customizer).apply("/api/v1/books", GET).permitAll();
//                            match.apply(customizer).apply("/api/v1/account-requests", GET).hasRole(ADMINISTRATOR.name());
//                            match.apply(customizer).apply("/api/v1/account-requests", POST).permitAll();
//                            match.apply(customizer).apply("/api/v1/account-requests", POST).permitAll();
                    }
                );
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${app.encrypted-password-storage}") Boolean encryptPasswords) {
        if (encryptPasswords) {
            return new BCryptPasswordEncoder();
        } else {
            return NoOpPasswordEncoder.getInstance();
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.allowed-origins}") List<String> allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PATCH"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Configuration
//    public class WebConfig implements WebMvcConfigurer {
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:5073") // Adjust this to your React app's URL
//                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                    .allowedHeaders("*") // Allow all headers
//                    .allowCredentials(true); // Allow credentials if needed
//        }
//    }



//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.formLogin(Customizer.withDefaults());
//
//        http.authorizeHttpRequests(customizer -> customizer
//                .requestMatchers("/api/v1/**").hasRole("ADMIN")
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                .requestMatchers("/api/user/**").hasRole("USER")
////                .anyRequest().authenticated()
//        );
//
//        return http.build();
//    }
//                        .requestMatchers("/api/v1/**").hasRole("ADMIN")

//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/user/**").hasRole("USER")
//                        .anyRequest().authenticated()

}
