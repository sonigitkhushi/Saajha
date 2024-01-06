package com.backend.saajha.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private Long id;
    private String authority;
    public UserRole(){
        super();
    }
    public UserRole(String authority){
        this.authority = authority;
    }
    public UserRole(Long id, String authority){
        this.id = id;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Object getEmail() {
        return null;
    }
    public void setPassword(String hashedNewPassword) {
    }
    public void setEmail(String newEmail) {
    }
}
