package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserConfig {
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoauthenticationProvider = new DaoAuthenticationProvider();
		daoauthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoauthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoauthenticationProvider;
	}
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	        return httpSecurity.csrf(csrf -> csrf.disable())
	                .authorizeHttpRequests(auth ->auth
	                        .requestMatchers("/home","/img/**","/js/**","/css/**","/signup","/","/do_register","/about","/error","/forgot","/send-otp","/logout").permitAll()
	                        .anyRequest().authenticated())
	                .authenticationProvider(authenticationProvider())
	                .httpBasic(Customizer.withDefaults())
	                .formLogin(form -> form
	                        .loginPage("/signin")
	                        .loginProcessingUrl("/doLogin")
	                        .defaultSuccessUrl("/user/index")
	                    .permitAll())
	                .logout(logout -> logout
	                        .logoutUrl("/logout")
	                        .logoutSuccessUrl("/signin?logout")
	                        .permitAll())
	                .build();
	    }
}
