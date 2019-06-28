package com.yanya.springmvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanya.springmvc.service.CustomerService;
import com.yanya.springmvc.service.MerchantService;

@Configuration
@Order(1)
@EnableWebSecurity
@ComponentScan({"com.yanya.springmvc"})
public class SecurityConfig {


	@Configuration
	@Order(1)
	public static class MerchantWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
		
		@Autowired
		MerchantService merchantService;
	
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		    auth.userDetailsService(merchantService);
		  
		}
		
		@Override
		public void configure(WebSecurity web) throws Exception {
		    web.ignoring().antMatchers("/robots.txt");
		    web.ignoring().antMatchers("/static/png/**");
		    web.ignoring().antMatchers("/static/svg/**");
		  
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
	          http
              .antMatcher("/sell/**")                               
	              .authorizeRequests()
								  .antMatchers("/sell/forgotPassword").permitAll()
								  .antMatchers("/sell/submitForgotPassword").permitAll()
								  .antMatchers("/sell/verifyPasswordCode").permitAll()
								  .antMatchers("/sell/changePassword").permitAll()
		              .antMatchers("/search/api/getSearchResult").access("hasRole('ROLE_MERCHANT')")
		              .antMatchers("/sell/home").access("hasRole('ROLE_MERCHANT')")
		              .antMatchers("/sell/status").access("hasRole('ROLE_MERCHANT')")
								  .antMatchers("/sell/appointments").access("hasRole('ROLE_MERCHANT')")
								  .antMatchers("/sell/uploadProduct").access("hasRole('ROLE_MERCHANT')")
								  .antMatchers("/sell/uploadProduct/**").access("hasRole('ROLE_MERCHANT')")
								  .antMatchers("/sell/appointment/**").access("hasRole('ROLE_MERCHANT')")
								  .antMatchers("/sell").permitAll()
              	  .antMatchers("/sell/register/auth").permitAll()
              	  .antMatchers("/sell/register/**").permitAll()
                  .anyRequest().hasRole("MERCHANT")
                  .and()
              .formLogin()
  				 .loginPage("/sell")
  				 .defaultSuccessUrl("/sell/home")
  				 .and()
              .exceptionHandling().accessDeniedPage("/sell")
                 .and()
	  		  .rememberMe()
	  			.tokenValiditySeconds(2419200)
	  			.key("SpringSecured")
	  		  .and()
  				.requiresChannel()
  					//.antMatchers("/").requiresInsecure()
  					.antMatchers("/*").requiresSecure()
	  			.and()
  		  	    .csrf()
  		  	       .ignoringAntMatchers("/sell/uploadProduct");
  				


			
		}
		
		@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
		@Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
		      return super.authenticationManagerBean();
	    }
		
	}
	
	
	@Configuration
	@Order(2)
	public static class UserLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Autowired
		CustomerService customerService;
	
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		    auth.userDetailsService(customerService);
		}
		
		@Override
		public void configure(WebSecurity web) throws Exception {
		    web.ignoring().antMatchers("/robots.txt");
		    web.ignoring().antMatchers("/static/png/**");
		    web.ignoring().antMatchers("/static/svg/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
							.antMatchers("/home").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/cart").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/reviewOrder").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/search").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/searchResults").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/product/**").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/profile").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/profile/**").access("hasRole('ROLE_CUSTOMER')")
							.antMatchers("/robots.txt").permitAll()
							.antMatchers("/login").permitAll()
							.antMatchers("/register").permitAll()
					.and()
					.formLogin()
							.loginPage("/login")
							.defaultSuccessUrl("/home")
			  	.and()
			  	.exceptionHandling().accessDeniedPage("/")
			  	.and()
			  	.rememberMe()
			  					.tokenValiditySeconds(2419200)
			  		  			.key("SpringSecured")
			  		.and()
			  	  .requiresChannel()
			  					//.antMatchers("/").requiresInsecure()
			  					.antMatchers("/*").requiresSecure()
			  		.and()
			  		.csrf()
			  		  	       .ignoringAntMatchers("/merchantSms")
	  	       				   .ignoringAntMatchers("/userShowPhotoUpload");


			}	
			
		@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
		@Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
		      return super.authenticationManagerBean();
	    }
	
	}
	
}