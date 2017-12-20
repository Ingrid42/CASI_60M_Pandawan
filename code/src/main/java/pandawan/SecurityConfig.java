package pandawan;

import javax.sql.DataSource;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import org.pac4j.core.client.Clients;
// import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
// import org.pac4j.springframework.security.web.ClientAuthenticationFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	 @Autowired
	 ApplicationContext context;

	 @Autowired
	 private Config config;

	//  @Autowired
	//  Clients clients;

	//  @Autowired
	//  ClientAuthenticationProvider clientProvider;

	@Autowired
	public void globalConfig(AuthenticationManagerBuilder auth,DataSource datasource ) throws Exception{

			auth.jdbcAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			.dataSource(datasource)
			.usersByUsernameQuery("select login as principal, password as credentials, 1 from utilisateur where login = ?")
			.authoritiesByUsernameQuery("select utilisateur_login as principal, role_nom as role from utilisateur_role where utilisateur_login = ?");
			// .rolePrefix("ROLE_");
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception{
	// http
	// 	.authorizeRequests()
	// 		.antMatchers("/", "/index.html").permitAll()
	// 		.antMatchers("/css/**","/js/**","/lib/**","/img/**").permitAll()
	// 		.anyRequest()
	// 		.authenticated()
	// 		.and()
	// 	.formLogin()
	// 		.loginPage("/login")
	// 		.defaultSuccessUrl("/home.html")
	// 		.failureUrl("/error.html")
	// 		.permitAll() ;
	//
	// }

	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
        .antMatchers(
            "/**/*.css",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.jpg",
            "/**/*.ico",
            "/**/*.js"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .csrf().disable()
        .authorizeRequests()
          .and()
        .formLogin()
          .loginPage("/login")
          .permitAll()
          .and()
        .logout()
          .logoutUrl("/logout")
          .logoutSuccessUrl("/")
          .permitAll();

			// final SecurityFilter filter = new SecurityFilter(config, "GithubClient", "");
			//
			// http
			// 	.antMatcher("/github/**")
			// 	.addFilterBefore(filter, BasicAuthenticationFilter.class); // TODO Filter role
    }

}
