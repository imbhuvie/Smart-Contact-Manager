package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstant;
import com.scm.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    // @Autowired
    // PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");
        // before redirecting we save data to database.
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        // to get name.
        logger.info(user.getName());
        // to get all the attributes.
        user.getAttributes().forEach((key, value) -> {
            logger.info("{} => {}", key, value);
        });
        // to get authorities
        logger.info(user.getAuthorities().toString());

        // creating a user to save in DB
        User user2 = new User();

        // generating userId
        String userId = UUID.randomUUID().toString();
        // add userId to the user
        user2.setUserId(userId);
        // Encode the password
        // user2.setPassword(passwordEncoder.encode("password"));
        user2.setPassword("password");

        // set the user role
        user2.setRoleList(List.of(AppConstant.ROLE_USER));
        // logger.Info(user.getProvider().toString());
        user2.setName(user.getName());
        user2.setEmail(user.getAttribute("email"));
        user2.setProfilePicLink(user.getAttribute("picture"));
        user2.setAbout("Account Created using login with google.");
        user2.setPhoneNumber(null);
        user2.setEnable(true);
        user2.setEmailVerified(true);
        user2.setPhoneVerified(false);
        user2.setProvider(Providers.GOOGLE);
        user2.setProviderUserId(user.getName());
        

        // Save this user to DB
        User user3 = userRepository.findByEmail(user.getAttribute("email")).orElse(null);
        if(user3==null){
            userRepository.save(user2);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
