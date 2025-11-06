package com.truonggiahung.yte.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> role;

    public JwtResponse(String token,Long id,String username,String email, List<String> role){
        this.token=token;
        this.email=email;
        this.id=id;
        this.role=role;
        this.username=username;
    }
}
