package com.unpontdev.comparator.config;

import com.unpontdev.comparator.controler.ErrorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * Main security configuration.
 * Handle access to pages, login and registration
 * and handling of errors
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private ErrorController errorController;

    @Autowired
    private DataSource dataSource;

    /**
     * Taking care of authentication
     * @param auth - get authority credits
     * @throws Exception - if methode receives errors
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery("select email as principal, password as credentails, true from member where email=?")
                    .authoritiesByUsernameQuery("select member_email as principal, role_name as role from member_roles where member_email=?")
                    .passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()) +". autentication error!");
        }
    }

    /**
     * Password encrypt
     * @return encrypted pass
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO Auto-generated method stub
        return new BCryptPasswordEncoder();
    }

    /**
     * Main configuration for app behaviour
     * @param http - https address of pages
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/register", "/login", "/despre", "/termeni").permitAll()
                    .antMatchers("/mainPage")
                    .hasAnyRole("MEMBER, ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/account")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .and()
                    .exceptionHandling().accessDeniedHandler(errorController);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()) +". Not authorized action was recorded.");
        }
    }
}
