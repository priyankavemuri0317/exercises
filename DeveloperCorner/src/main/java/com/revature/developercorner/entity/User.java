package com.revature.developercorner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User implements UserDetails {

    // Data members for the User object:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String eMail;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String timeAvailable;

    //Constructor without ID:
    public User(String username, String eMail, String password, Role role, String timeAvailable) {
        this.username = username;
        this.eMail = eMail;
        this.password = password;
        this.role = role;
        this.timeAvailable = timeAvailable;
    }

    // UserDetails methods
    //=====================

    // GetAuthorities method
    // This is an overridden method from UserDetails Interface that will return a list of SimpleGrantedAuthories.
    // SimpleGrantedAuthority is a String representation of the authority given to the object that is used in
    //  authenticating the User for specified actions involving security (login and HTTP requests).
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getRole_name()));
    }

    // IsAccountNonExpired method
    // This method will return if the user account is expired:
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // IsAccountNonLocked method
    // This method will return if the user account is locked:
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // IsCredentialsNonExpired method
    // This method will return if the user account's credentials are expired:
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // IsEnabled method
    // This method will return if the user account is enabled or not:
    @Override
    public boolean isEnabled() {
        return true;
    }
}
