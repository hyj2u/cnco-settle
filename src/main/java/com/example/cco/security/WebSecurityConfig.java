package com.example.cco.security;


import com.example.cco.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final MemberRepository memberRepository;
    private final RefreshTknRepository refreshTknRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
        .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors().configurationSource(corsConfigurationSource()).and()
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login", "/settle/secret", "/img/**", "/auth/signup", "/health" ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(authenticationManager-> authenticationManager.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
                .addFilter(new JwtAuthenticationFilter(jwtTokenProvider, authenticationManager(), memberRepository,refreshTknRepository, passwordEncoder()))
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
