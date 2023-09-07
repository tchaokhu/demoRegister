package com.example.demo.security.config;

import com.example.demo.appuser.AppUserService;
import com.example.demo.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

   private final AppUserService appUserService;
   private final BCryptPasswordEncoder bCryptPasswordEncoder;

   @Bean
   protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf().disable()
              .authorizeRequests()
              .requestMatchers("/api/v*/registration/**")
              .permitAll()
              .anyRequest()
              .authenticated().and().authenticationProvider(daoAuthenticationProvider())
              .formLogin();
      return http.build();
   }


   @Bean
   public DaoAuthenticationProvider daoAuthenticationProvider() {
      DaoAuthenticationProvider provider =
              new DaoAuthenticationProvider();
      provider.setPasswordEncoder(bCryptPasswordEncoder);
      provider.setUserDetailsService(appUserService);
      return provider;
   }
}