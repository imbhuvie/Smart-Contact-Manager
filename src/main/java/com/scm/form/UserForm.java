package com.scm.form;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 3,max = 30, message = "3-30 characters allowed")
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 10, max = 10, message = "only 10 characters")
    private String contact;
    @NotBlank
    @Size(min = 8,max = 20,message = "8-20 characters allowed")
    private String password;
    @NotBlank
    private String about;
}
