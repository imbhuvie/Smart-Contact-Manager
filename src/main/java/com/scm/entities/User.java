package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    private String userId;
    @Column(name = "username", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
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
    @Enumerated
    private Providers provider =Providers.SELF;
    private String providerUserId;

    // cascade = CascadeType.ALL , if we want that if user deleted then contact of that user automatically deleted
    //  fetch = FetchType.LAZY , if we get user and need not contact, then don't run query for contacts.
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,orphanRemoval = true)
    List<Contact> contact=new ArrayList<>();
}
