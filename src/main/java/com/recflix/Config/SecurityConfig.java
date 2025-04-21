package com.recflix.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Phân quyền theo URL
                        .requestMatchers("/login", "/static/**", "/js/**", "/css/**", "/img/**").permitAll() // Không yêu cầu đăng nhập
                        .requestMatchers("/admin/**").hasRole("ADMIN") 
                        .requestMatchers("/user/**").hasRole("USER")   
                        .anyRequest().authenticated() 
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") 
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            
                            String redirectUrl = authentication.getAuthorities().stream()
                                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")) ? "/admin/home_admin" : "/user/home_user";
                            response.sendRedirect(redirectUrl); 
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
