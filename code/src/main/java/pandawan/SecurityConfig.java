package pandawan;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
public void globalConfig(AuthenticationManagerBuilder auth,DataSource datasource ) throws Exception{
		
		auth.jdbcAuthentication()
		.dataSource(datasource)
		.usersByUsernameQuery("select mail as principal, password as credentials from utilisateurs where mail = ?")
		.authoritiesByUsernameQuery("select utilisateurs_mail as principal, roles_nom as role from utilisateurs_role where utilisateurs_mail = ?")
		.rolePrefix("ROLE_");
		
}
	
	protected void configure(HttpSecurity http) throws Exception{
	http
		.authorizeRequests()
			.antMatchers("/css/**","/js/**","/images/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/index.html")
			.failureUrl("/error.html")
			.permitAll() ;
	
	}
}