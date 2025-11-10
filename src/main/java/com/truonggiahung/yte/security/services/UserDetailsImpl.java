package com.truonggiahung.yte.security.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.truonggiahung.yte.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name())) /* thêm "ROLE_" để định dạng 
                                                                                nếu bên ERole không có ROLE_ */
        .collect(Collectors.toList());
        
        return new UserDetailsImpl(
        user.getId(),
        user.getUsername(), 
        user.getEmail(),
        user.getPassword(), 
        authorities);
    }

     // Các method bắt buộc implement UserDetails
    @Override
    public boolean isAccountNonExpired() { return true; } // kiểm tra tài khoản còn hạn hay không

    @Override
    public boolean isAccountNonLocked() { return true; } // kiểm tra tài khoản có bị khóa không

    @Override
    public boolean isCredentialsNonExpired() { return true; } // Kiểm tra thông tin đăng nhập (mật khẩu) còn hạn không

    @Override
    public boolean isEnabled() { return true; } // kiểm tra tài khoản có hoạt động không
}
