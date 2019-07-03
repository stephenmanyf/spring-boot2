/**
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-spring-security-example/
 * MySQL	: https://www.onlinetutorialspoint.com/spring-boot/spring-boot-security-mysql-database-integration-example.html
 */
package com.stephen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stephen.repository.UserRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity //required if disabled security auto-configuration
@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	// Create 2 users for demo
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//				.withUser("user").password("{noop}password").roles("USER")
//				.and()
//				.withUser("admin").password("{noop}password").roles("USER", "ADMIN");
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	// Secure the endpoints with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//HTTP Basic authentication
			.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
			.and()
			.csrf().disable()
			.formLogin().disable();
	}
	
	//To be changed to a real encoder
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				return charSequence.toString();
			}
			
			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return true;
			}
		};
	}
}
