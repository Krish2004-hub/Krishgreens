package com.springboot_practice.Spring.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mysql.cj.protocol.AuthenticationProvider;
import com.springboot_practice.Spring.security.JwtFilter;
import com.springboot_practice.Spring.services.CustomerUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
        		 .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
                .requestMatchers("/api/users/**").authenticated()
                .requestMatchers("/").permitAll()
                .anyRequest().permitAll())
            .formLogin(form -> form.permitAll().defaultSuccessUrl("/dashboard"))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        	.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    		

        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder()
//                .username("zack")
//                .password(passwordEncoder.encode("zack123"))
//                .roles("USER")
//                .build();
//        
//        UserDetails admin = User.builder()
//                .username("adam")
//                .password(passwordEncoder.encode("adam123"))
//                .roles("ADMIN")
//                .build();
//        
//        return new InMemoryUserDetailsManager(user, admin);
    	return new CustomerUserDetailService();
    }
    @Bean
   public DaoAuthenticationProvider authenticationProvider(){
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(userDetailService(null));
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Bean
	public ProviderManager authenticationManager() {
	   return new ProviderManager(List.of(authenticationProvider()));
	}
	
}
