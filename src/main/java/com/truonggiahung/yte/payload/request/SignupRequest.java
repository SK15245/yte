package com.truonggiahung.yte.payload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(max=50)
    private String username;

    @NotBlank
    @Size(min=3,max=100)
    private String password;

    @NotBlank
    @Email
    @Size(max=50)
    private String email;

    private Set<String> roles;

}
