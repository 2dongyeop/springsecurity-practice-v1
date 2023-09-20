package io.dongvelop.springsecuritypractice.common.config;

import io.dongvelop.springsecuritypractice.common.authority.JwtAuthenticationFilter;
import io.dongvelop.springsecuritypractice.common.authority.TokenProvider;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(it -> it.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(
                        it -> it.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).anonymous()
                                .requestMatchers(new AntPathRequestMatcher("/api/member/signup")).anonymous()
                                .requestMatchers(new AntPathRequestMatcher("/api/member/login")).anonymous()
//                                .anyRequest().hasRole("MEMBER"))
                                .anyRequest().permitAll())
                .addFilterBefore(
                        new JwtAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JESESSIONID")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/")
                        .addLogoutHandler(((request, response, authentication) -> {

                            final HttpSession session = request.getSession();
                            if (session != null) {
                                session.invalidate();
                            }
                        }))
                        .logoutSuccessHandler(((request, response, authentication) -> response.sendRedirect("/")))
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
