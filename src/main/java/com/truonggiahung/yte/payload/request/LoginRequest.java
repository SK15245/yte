package com.truonggiahung.yte.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @Size(max=50)
    private String username;
    
    @NotBlank
    @Size(min=3,max=100)
    private String password;
}
