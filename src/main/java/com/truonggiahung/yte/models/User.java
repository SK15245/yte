package com.truonggiahung.yte.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tự tạo ID
    private Long id;

    @NotBlank
    @Size(max=30)
    private String username;

    @NotBlank
    @Size(min=3,max=100)
    @JsonIgnore
    private String password;
    
    @NotBlank
    @Email
    @Size(max=50)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER) // hoặc LAZY tuỳ nhu cầu
    @JoinTable(
        name = "user_roles", // tên bảng trung gian
        joinColumns = @JoinColumn(name = "user_id"), // FK về User
        inverseJoinColumns = @JoinColumn(name = "role_id") // FK về Role
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
}

}
