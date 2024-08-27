package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        // authorizedClientRegistrationId :Basically it is provider type like google,
        // github or
        // linkedin etc.
        logger.info(authorizedClientRegistrationId); // to check the provider on console for testing purposes only

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " = " + value);
        });
        User user = new User();

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            user.setName(oauthUser.getName());
            user.setEmail(oauthUser.getAttribute("email"));
            user.setProfilePicLink(oauthUser.getAttribute("picture"));
            user.setAbout("Account Created using login with google.");
            user.setEmailVerified(true);
            user.setProvider(Providers.GOOGLE);
            user.setProviderUserId(user.getName());

        }
        // if provider is github
        else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            String email = (oauthUser.getAttribute("email") != null) ? oauthUser.getAttribute("email")
                    : oauthUser.getAttribute("login") + "@github.com";
            user.setEmail(email);
            user.setProfilePicLink(oauthUser.getAttribute("avatar_url"));
            user.setName(oauthUser.getAttribute("name"));
            user.setAbout("This is github account : "+oauthUser.getAttribute("bio"));
            user.setEmailVerified(false);
            user.setProvider(Providers.GITHUB);
            user.setProviderUserId(oauthUser.getAttribute("id").toString());

        }

        // before redirecting we save data to database.
        // generating userId
        String userId = UUID.randomUUID().toString();
        // add userId to the user
        user.setUserId(userId);
        // Encode the password
        user.setPassword(passwordEncoder.encode("password"));

        // set the user role
        user.setRoleList(List.of(AppConstant.ROLE_USER));
        // logger.Info(user.getProvider().toString());
        user.setPhoneNumber(null);
        user.setEnable(true);
        user.setPhoneVerified(false);

        // Save this user to DB
        User user3 = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (user3 == null) {
            userRepository.save(user);
            logger.info(user.getName() + " saved successfully");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
