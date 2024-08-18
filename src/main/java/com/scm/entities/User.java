package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
@Table(name = "users")
public class User implements UserDetails{

    @Id
    private String userId;
    @Column(name = "username", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 6500)
    private String about;
    @Column(length = 6500)
    private String profilePicLink;
    @Column(unique = true)
    private String phoneNumber;
    // Info about account
    private boolean enable=false;
    private boolean emailVerified =false;
    private boolean phoneVerified =false;

    // SELF,GOOGLE,FACEBOOK,GITHUB,LINKEDIN
    @Enumerated(value = EnumType.STRING)
    private Providers provider =Providers.SELF;
    private String providerUserId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();

    // cascade = CascadeType.ALL , if we want that if user deleted then contact of that user automatically deleted
    //  fetch = FetchType.LAZY , if we get user and need not contact, then don't run query for contacts.
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,orphanRemoval = true)
    List<Contact> contact=new ArrayList<>();


    // Methods overrides for UserDetails implementation
 

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        // List of roles
        // Collection of simpleGrantedAuthorities[roles:ADMIN,USER]
       Collection<SimpleGrantedAuthority> roles =  roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles; 
    }


    @Override
    public String getUsername() {
        return this.email;
    }
}
