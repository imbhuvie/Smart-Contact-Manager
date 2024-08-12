package com.scm.form;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Component
public class UserForm {
    private String name;
    private String email;
    private String contact;
    private String password;
    private String about;
}
