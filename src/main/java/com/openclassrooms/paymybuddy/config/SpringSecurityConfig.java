package com.openclassrooms.paymybuddy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.openclassrooms.paymybuddy.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        /**
         * Configure la filter chain de Spring Security
         * 
         * Défini les règles d'autorisation:
         * - Ouvre toutes les requetes à /user/** sans autorisation (signup, signin,..)
         * - exige une authentification avec un role USER pour les endpoints /profil/**
         * - Exige une authentification pour toutes les autres requetes
         * 
         * Active le login via formulaire et l'authentification HTTP Basique avec les
         * paramètres par défaut.
         * 
         * @param http l'objet {@link HttpSecurity} à configurer
         * @return les filters configurés {@link SecurityFilterChain}
         * @throws Exception si une erreur se produit pendant la configuration
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/login", "/css/**", "/assets/**").permitAll()
                                                .anyRequest().hasRole("USER"))
                                .formLogin(form -> form
                                                .loginPage("/login") // GET
                                                .loginProcessingUrl("/login") // POST (important)
                                                .defaultSuccessUrl("/transfert", true)
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout=true")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .httpBasic(Customizer.withDefaults());

                return http.build();
        }

        /**
         * Configures in-memory user details service with a default test user.
         * 
         * Creates a user with:
         * - Username: "user"
         * - Password: "user" (encoded with BCrypt)
         * - Role: "USER"
         * 
         * @return the {@link UserDetailsService} containing user authentication details
         */
        /*
         * @Bean
         * public UserDetailsService users() {
         * UserDetails user = User.builder()
         * .username("user")
         * .password(passwordEncoder()
         * .encode("user"))
         * .roles("USER")
         * .build();
         * return new InMemoryUserDetailsManager(user);
         * }
         */

        /**
         * Provides a BCrypt password encoder bean for secure password hashing.
         * 
         * @return a new instance of {@link BCryptPasswordEncoder}
         */
        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http,
                        BCryptPasswordEncoder bCryptPasswordEncoder)
                        throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                                .passwordEncoder(bCryptPasswordEncoder);
                return authenticationManagerBuilder.build();
        }

}
