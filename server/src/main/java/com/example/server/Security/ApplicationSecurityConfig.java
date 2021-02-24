package com.example.server.Security;

import com.example.server.User.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

//This is the place where everything to do with security for this application will be configured
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder; //provider must have passwordEncoder
    private final UserService userService;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    //WebSecurityConfigurerAdapter comes with some web security configs predefined for us
    //We can override some if need be to customize the security configurations
    //For example the configure(http:HttpSecurity) method
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); //wont need this.
        http
                //any requests must be authenticated to authorize use of any requests
                .authorizeRequests()
                //this will white list the following paths with permitAll (anyone can) access
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                //authentication will be carried out by httpBasic
                .and()
                //.httpBasic();
                .formLogin();

    }

    //the configure and DaoAuthenticationProvider methods will allow us to use the UserService
    //that also implements the UserDetailsService
    //this will wires the provider (daoAuthenticationProvider) to the desired service class
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //this will allow use to use the userService class we made to access the user in the database from a client
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        System.out.println(passwordEncoder.encode("password"));
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}