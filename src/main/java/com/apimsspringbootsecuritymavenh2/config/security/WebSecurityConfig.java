/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.apimsspringbootsecuritymavenh2.config.filter.JWTAuthenticationFilter;
import com.apimsspringbootsecuritymavenh2.config.filter.JWTAuthorizationFilter;

/**
 * Classe com as configuracoes de seguranca.
 * 
 * @author eloi
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final String[] AUTH_WHITELIST_H2 = { "/h2", "/h2/**", "/h2/login.do?**", "/h2/header.jsp?**",
			"/h2/query.jsp?**", "/h2/help.jsp?**", "/h2/tables.do?**", "/h2/stylesheet.css" };

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().antMatchers(AUTH_WHITELIST_H2).permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll().antMatchers(HttpMethod.POST, "/v1/users")
				.permitAll().anyRequest().authenticated().and()

				// filtra requisições de login
				.addFilterBefore(new JWTAuthenticationFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				// filtra outras requisições para verificar a presença do JWT no header
				.addFilterBefore(new JWTAuthorizationFilter(authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				// permit frame options
				.headers().frameOptions().sameOrigin();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

}
