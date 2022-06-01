package com.revature.developercorner.configuration;

import com.revature.developercorner.security.SessionFilter;
import com.revature.developercorner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

// ApplicationSecurityConfig Class
// This class will handle the security configuration for the HTTP Requests the users send. The APIs will be set to
//  allow or reject access. This class will also apply filters to Requests as they are passed along the chain
//  of filters.
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    // Data members:
    private final UserService userService;
    private final SessionFilter sessionFilter;
    private final PasswordEncoder passwordEncoder;

    // ApplicationSecurityConfig Constructor:
    @Autowired
    public ApplicationSecurityConfig(UserService userService, SessionFilter sessionFilter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.sessionFilter = sessionFilter;
        this.passwordEncoder = passwordEncoder;
    }

    // Configure method
    // Overridden method of WebSecurityConfigurerAdapter that is passed an HttpSecurity object. This handles the
    //  filtering of requests and configuration of the HttpSecurity object.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }
                )
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/register", "/**")
                .permitAll()
//                .antMatchers("/users/**")
//                .hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated();

        http.addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // AuthenticationManager Bean
    // AuthenticationManager is a class that manages the authentication modules that an application uses. Whenever
    //  a request is made to resources requiring authentication, this will call the Authenticate method to get an
    //  Authorization instance to use in subsequent requests. Returns a new AuthenticationManager:
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Configure method
    // Overridden method that takes an AuthenticationManagerBuilder object and sets the Provider to an instance of
    //  DaoAuthenticationProvider. The AuthenticationManagerBuilder is a helper class that is used to help ease the
    //  setup of the UserDetailsService, AuthenticationProvider, and other dependencies into one solid object that
    //  is used for security purposes:
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // Spring Security's DaoAuthenticationProvider Bean
    // A Simple Authentication Provider that uses a DAO to retrieve user information from a relational database.
    // This will create a new instance of DaoAuthenticationProvider, sets the PasswordEncoder and UserDetailsService
    //  of Provider, and returns that for the AuthenticationManagerBuilder above:
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
