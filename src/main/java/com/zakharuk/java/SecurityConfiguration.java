package com.zakharuk.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by citizenzer0 on 11/28/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDao userDao;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/", "/all", "/all-recommended", "/all-users", "/createuser",
                        "/list-students", "/updateuser", "/new-user").permitAll()
                .antMatchers("/recommend", "/set-prof").hasRole("methodist")
                .antMatchers("/update", "create", "/delete", "/create-full",
                        "/set-prof", "/deleteuser", "/updateuser", "/new-subject").hasRole("admin")
                .antMatchers("/select-add-student", "/add-subject", "/remove-subject").hasRole("student")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        for (User u : userDao.findAll()) {
            auth
                    .inMemoryAuthentication()
                    .withUser(u.getName()).password(u.getPassword()).roles(u.getRole());
        }
    }

    public static Authentication findAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}