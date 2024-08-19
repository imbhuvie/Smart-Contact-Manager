package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.service.implimentation.SecurityCustomUserDetailsService;

@Configuration
public class SecurityConfig {
    // Here we use filterchain to unblock the login and signup form. becoz without this we can't access any endpoint even for login and signup.

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
// secure and unsecure url are defined here.
            httpSecurity.authorizeHttpRequests(authorize->{authorize
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().permitAll();
            });
//            when unauthorised url hit then it give default login page
            httpSecurity.formLogin(Customizer.withDefaults());
            return httpSecurity.build();
        }



    // InMemoryUserDetailsManager :here we store user details static not DB used.
    // @Bean
    // public UserDetailsService userDetailsService() {
    // // Implement your own UserDetailsManager implementation here.
    // UserDetails user =
    // User.withDefaultPasswordEncoder().username("bhuvie").password("B@123").build();
    // return new InMemoryUserDetailsManager(user);
    // }


    
    
    @Autowired
    SecurityCustomUserDetailsService userDetailsService;
    
    // Now we get user details from database and login
    @Bean
    public AuthenticationProvider authenticationProvider() {
        //
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // object of user details service
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        // object of password encoder
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // To get object of password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}