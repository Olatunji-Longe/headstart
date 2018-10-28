package com.quadbaze.headstart.configs;

import com.quadbaze.headstart.domain.enums.RoleType;
import com.quadbaze.headstart.utils.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 5:19 PM
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationUserDetailsService")
    private UserDetailsService authenticationUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() // Permit All Actuator Endpoint Requests just for this demo
            .antMatchers(ResourcePath.CONTEXT_PERMITTED_PATHS.toArray(new String[]{})).permitAll() // No Authentication required for the static resources and base paths
            .antMatchers("/books/**").hasAnyAuthority(
                RoleType.ROLE_USER_ADMIN.name(),
                RoleType.ROLE_USER_CUSTOMER.name(),
                RoleType.ROLE_BOOTSTRAPPED_USER.name()
            ) // Authorize these User Roles for /books/**
            .anyRequest().authenticated()
        .and().formLogin()
            .loginPage("/login").permitAll()
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/books")
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("remember-me")
            .logoutSuccessUrl("/login?logout").permitAll()
        .and().exceptionHandling();

        //Configure same origin for iframes
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
