package com.bolsadeideas.springboot.diabeticdiary.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideas.springboot.diabeticdiary.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideas.springboot.diabeticdiary.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/sign-up/**", "/css/**", "/js/**", "/images/**").permitAll()
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
			.permitAll()
		.and()
		.logout().permitAll();
		
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
		
		builder.userDetailsService(this.userDetailsService)
		.passwordEncoder(this.passwordEncoder);
		
	}
	
}
