package com.scm.service.implimentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.repository.UserRepository;
@Service
public class SecurityCustomUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load our username, password.
        return  userRepository.findById(username).orElseThrow(()->new UsernameNotFoundException("Could not find User.")); 


    }

}
