package com.wipro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityCOnfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http.authorizeHttpRequests(authz ->
				authz
				.requestMatchers(HttpMethod.POST,"/students").permitAll()
				.requestMatchers("/students/**").authenticated()
				.anyRequest().permitAll()
				
				)
		.formLogin(form -> form
			    .defaultSuccessUrl("/dashboard", true)
			    .permitAll())
		.csrf(csrf -> csrf.disable())
		;
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailService(PasswordEncoder passwordEncoder) {
		UserDetails	user =User.withUsername("vj")
							  .password(passwordEncoder.encode("159"))
							  .roles("USER")
							  .build();
		UserDetails	admin =User.withUsername("inba")
				  .password(passwordEncoder.encode("0039"))
				  .roles("ADMIN")
				  .build();
		return new InMemoryUserDetailsManager(user,admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
