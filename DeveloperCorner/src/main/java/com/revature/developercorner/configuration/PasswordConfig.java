package com.revature.developercorner.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// PasswordConfig Class
// This class will handle the BCryptPasswordEncoder for encrypting User objects' passwords.
@Configuration
public class PasswordConfig {

    // PasswordEncoder Bean
    // This will return the BCryptPasswordEncoder with the strength of 10 to ensure it will generate strong
    //  passwords for the newly created and/or updated User objects:
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Returns the password encoder with the strength of 10:
        return new BCryptPasswordEncoder(10);
    }
}
