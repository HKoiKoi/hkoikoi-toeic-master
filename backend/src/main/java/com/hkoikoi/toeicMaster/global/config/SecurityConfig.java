package com.hkoikoi.toeicMaster.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hkoikoi.toeicMaster.global.security.exception.CustomAccessDeniedHandler;
import com.hkoikoi.toeicMaster.global.security.exception.CustomAuthenticationEntryPoint;
import com.hkoikoi.toeicMaster.global.security.jwt.JwtAuthenticationFilter;
import com.hkoikoi.toeicMaster.global.security.oauth2.OAuth2LoginFailureHandler;
import com.hkoikoi.toeicMaster.global.security.oauth2.OAuth2LoginSuccessHandler;
import com.hkoikoi.toeicMaster.global.security.oauth2.OAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final OAuth2UserService oAuth2UserService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler)
			)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/error", "/favicon.ico").permitAll()
				.requestMatchers("/api/v1/auth/**", "/oauth2/**", "/login/**").permitAll()
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oAuth2UserService)
				)
				.successHandler(oAuth2LoginSuccessHandler)
				.failureHandler(oAuth2LoginFailureHandler)
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(List.of("http://localhost:3000", "https://toeic.hkoikoi.dev"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		configuration.setExposedHeaders(List.of("Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
