package pandawan;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public void globalConfig(AuthenticationManagerBuilder auth,DataSource datasource ) throws Exception{
			
			auth.jdbcAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			.dataSource(datasource)
			.usersByUsernameQuery("select login as principal, password as credentials, 1 from utilisateur where login = ?")
			.authoritiesByUsernameQuery("select utilisateur_login as principal, role_nom as role from utilisateur_role where utilisateur_login = ?")
			.rolePrefix("ROLE_");
			
	}
	
	protected void configure(HttpSecurity http) throws Exception{
	http
		.authorizeRequests()
			.antMatchers("/css/**","/js/**","/lib/**","/img/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/home")
			.failureUrl("/error.html")
			.permitAll() ;
	
	}
}