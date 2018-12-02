package com.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author bhargavi
 * This class is to configure security for the Mailer Application using Spring security.
 */
@Configuration
@EnableWebSecurity

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter 
{
	
   @Autowired
    private UserDetailsService userDetailsService;
   
   	/**
   	 * 
   	 * @return BCryptPasswordEncoder
   	 * 
   	 * This method returns encoder object so that user password is not store in plain text format 
   	 */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    /**
     * 	In this method we apply security filters for the end points.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/resources/**", "/registration","/forgotpassword").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/welcome")
                    .permitAll()
                    .and()
                .logout().permitAll().logoutSuccessUrl("/login");
                    
    }
    
    /**
     * This method is for user authentication purpose. AuthenticationManagerBuilder is built using userdetailsService and password encoder object
     * 
     * @param auth
     * @throws Exception
     * 
     * 
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}