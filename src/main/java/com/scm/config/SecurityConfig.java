package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.service.implimentation.SecurityCustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    // Here we use filterchain to unblock the login and signup form. becoz without
    // this we can't access any endpoint even for login and signup.

    // getting object of OAuthAuthenticationSuccessHandler for successfull authentication using OAuth2(google,github,.....)
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        /*  secure and unsecure url are defined here, When unauthorised url hit then it
         give default login page. */
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**")
                    .authenticated()
                    .anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLogin -> {
            // your login page.
            formLogin.loginPage("/login")
                    // authentication page where data from login page accepted.
                    .loginProcessingUrl("/authenticate")
                    // after login the page on which it redirects
                    .successForwardUrl("/user/dashboard")
                    // if error happen then page to redirect.
                    .failureForwardUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
            // // what to do when authentication failed
            // .failureHandler(new AuthenticationFailureHandler() {

            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // // your code here
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationFailure'");
            // }

            // })
            // // what to do when the authentication successful
            // .successHandler(new AuthenticationSuccessHandler() {

            // @Override
            // public void onAuthenticationSuccess(HttpServletRequest request,
            // HttpServletResponse response,
            // Authentication authentication) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // // your code here.
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationSuccess'");
            // }

            // })

            ;

        });

        // Google authentication configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });


        // To disable CSRF token for logout beacause it check for POST request and then
        // check CSRF token which we are not using so, for testing we disable it.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // To Logout user
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });
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