package com.AuthNode.auth.web.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private static final String ADMIN = "admin";
    private static final String USER = "user";

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeHttpRequests()

                // Rutas de autenticación (auth)
                .requestMatchers(HttpMethod.DELETE, "/api/auth/d/*").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/auth/all").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/auth/all/pageable").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.POST, "/api/auth/add").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/all/v0").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/auth").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.PUT, "/api/auth/a/{id}").hasAnyRole(ADMIN, USER)

                // Rutas de espacios de parqueo (space)
                .requestMatchers(HttpMethod.GET, "/api/space/all").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/space/all/pageable").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/space/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.POST, "/api/space/add").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.PUT, "/api/space/update/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.DELETE, "/api/space/delete/{id}").hasAnyRole(ADMIN, USER)

                // Rutas de reservas de espacios (reserva)
                .requestMatchers(HttpMethod.GET, "/api/reserva/all").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/reserva/all/pageable").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/reserva/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.POST, "/api/reserva/add").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.PUT, "/api/reserva/update/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.DELETE, "/api/reserva/delete/{id}").hasAnyRole(ADMIN, USER)

                // Rutas de los comentarios (comment)
                .requestMatchers(HttpMethod.GET, "/api/comment/all").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/comment/all/pageable").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/comment/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.POST, "/api/comment/add").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.PUT, "/api/comment/update/{id}").hasRole(ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/api/comment/delete/{id}").hasRole(ADMIN)

                // Rutas de parqueaderos (parking)
                .requestMatchers(HttpMethod.GET, "/api/parking/all").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/parking/all/pageable").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/parking/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.POST, "/api/parking/add").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.PUT, "/api/parking/update/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.DELETE, "/api/parking/delete/{id}").hasAnyRole(ADMIN, USER)

                // Rutas de vehículos (vehiculo)
                .requestMatchers(HttpMethod.GET, "/api/vehiculo/all").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/vehiculo/all/pageable").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/vehiculo/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.POST, "/api/vehiculo/add").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.PUT, "/api/vehiculo/update/{id}").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.DELETE, "/api/vehiculo/delete/{id}").hasAnyRole(ADMIN, USER)

                // Cualquier otra solicitud debe estar autenticada
                .anyRequest().authenticated().and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
