package com.wipro.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wipro.security.JwtFilter;
import com.wipro.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityCOnfig {

	   @Autowired
	    private CustomUserDetailsService customUserDetailsService;
	   @Autowired
	   private JwtFilter jwtFilter;
	   
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http.authorizeHttpRequests(authz ->
				authz
				.requestMatchers(HttpMethod.POST,"/students").permitAll()
				.requestMatchers("/students/**").authenticated()
				.anyRequest().permitAll()
				)
		//.formLogin(form -> form
		//.defaultSuccessUrl("/dashboard", true)
		//.permitAll())
		.csrf(csrf -> csrf.disable())
		.sessionManagement( sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		;
		return http.build();
	}
	
	
	@Bean
	public UserDetailsService userDetailService() {
//		UserDetails	user =User.withUsername("vj")
//							  .password(passwordEncoder.encode("159"))
//							  .roles("USER")
//							  .build();
//		UserDetails	admin =User.withUsername("inba")
//				               .password(passwordEncoder.encode("0039"))
//				               .roles("ADMIN")
//				               .build();
//		return new InMemoryUserDetailsManager(user,admin);
		return new CustomUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
	    //authProvider.setUserDetailsService(userDetailService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(authenticationProvider()));
	}
}
