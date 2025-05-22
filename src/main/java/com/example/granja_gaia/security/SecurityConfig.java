package com.example.granja_gaia.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers(
                                "/api/usuarios/login",
                                "/api/usuarios/registro/cliente",
                                "/api/usuarios/registro/admin",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/api/eventos/all",
                                "/api/eventos/**",
                                "/api/productos/listar",
                                "/api/productos/populares",
                                "/api/productos/**",
                                "/api/categorias/listar"
                        ).permitAll()

                        // Rutas protegidas para clientes autenticados
                        .requestMatchers(HttpMethod.GET, "/api/clientes/perfil").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/clientes/usuario/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/carrito/pedido/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/carrito/detalle/**").authenticated()


                        // Rutas protegidas para administradores
                        .requestMatchers("/api/admin/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, "/api/clientes").hasAuthority("admin")
                        //.requestMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAuthority("admin")  // Solo admin
                        .requestMatchers(HttpMethod.POST, "/api/eventos/crear").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/eventos/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/api/productos/crear").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH, "/api/productos/**/popular").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, "/api/inscripciones-eventos/all").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/inscripciones-eventos/**").hasAuthority("admin")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
