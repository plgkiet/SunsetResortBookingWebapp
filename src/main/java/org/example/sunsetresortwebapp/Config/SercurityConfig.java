//package org.example.sunsetresortwebapp.Config;
//
//
//import org.example.sunsetresortwebapp.Services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SercurityConfig {
//    @Autowired
//    private final UserService userService;
//    public SercurityConfig(UserService userService) {
//        this.userService = userService;
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userService;
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(formLogin ->{
//                    formLogin
//                            .loginPage("/signin")
//                            .loginProcessingUrl("/req-signin")
//                            .usernameParameter("email")
//                            .passwordParameter("password")
//                            .defaultSuccessUrl("/homepage",true)
//                            .permitAll();
//                })
//                .authorizeHttpRequests(registry ->{
//                    registry.requestMatchers("/register","/signup","/css/**" , "/js/**" , "/images/**", "/signin" ,"/homepage", "/req/register").permitAll();
//                    registry.anyRequest().authenticated();
//                })
//                .build();
//    }
//}
