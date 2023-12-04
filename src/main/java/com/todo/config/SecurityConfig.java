package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    @Autowired
//    AuthenticationSuccessHandler successHandler;

//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    OAuthLoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth -> {
                    auth.antMatchers("/")
                            .authenticated();

                })

                .oauth2Login(oath2 -> {
                    //oath2.loginPage("/login").permitAll();
                    oath2.successHandler(oAuth2LoginSuccessHandler);
                })

                .build();



















//                http.csrf().disable()
//                .authorizeRequests()
////                .antMatchers("/registration/**","/login/**").permitAll()
//
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login()
//                        .loginPage("/").successHandler(oAuth2LoginSuccessHandler);

//                .userService(customOAuth2UserService);

//
//
//
//
//
////                .and()
////                .formLogin().loginPage("/login").successHandler(successHandler)
////                .and().csrf().disable()
////                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
////                .and().oauth2Login().loginPage("/login").successHandler(successHandler);
//      return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }
}
















