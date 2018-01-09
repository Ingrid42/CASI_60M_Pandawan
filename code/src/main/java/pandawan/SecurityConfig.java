package pandawan;

import javax.sql.DataSource;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.CallbackFilter;
import org.pac4j.springframework.security.web.LogoutFilter;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig {
	
    @Configuration
    @Order(1)
    public static class LinkedInWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "LinkedIn2Client", "custom");

            http
	            .antMatcher("/home")
	            .addFilterBefore(filter, BasicAuthenticationFilter.class)
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }

	@Configuration
	@Order(2)
	public static class ConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		ApplicationContext context;

		@Autowired
		private Config config;

		@Autowired
		public void globalConfig(AuthenticationManagerBuilder auth, DataSource datasource ) throws Exception{
			auth
				.jdbcAuthentication()
				.passwordEncoder(new BCryptPasswordEncoder())
				.dataSource(datasource)
				.usersByUsernameQuery("select login as principal, password as credentials, 1 from utilisateur where login = ?")
				.authoritiesByUsernameQuery("select utilisateur_login as principal, role_nom as role from utilisateur_role where utilisateur_login = ?")
				.rolePrefix("ROLE_");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception{

			http
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/", "/index.html").permitAll()
					.antMatchers("/css/**","/js/**","/lib/**","/img/**").permitAll()
					.anyRequest()
					.authenticated()
					.and()
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/home.html")
					.failureUrl("/error.html")
					.permitAll()
					.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.permitAll();


			CallbackFilter callbackFilter = new CallbackFilter(config);
			callbackFilter.setDefaultUrl("/home");
			http
				.antMatcher("/**")
				.addFilterBefore(callbackFilter, BasicAuthenticationFilter.class);

		}

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
  }

	@Configuration
	@Order(3)
	public static class Pac4jLogoutWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
			private Config config;

	 		protected void configure(final HttpSecurity http) throws Exception {

 				final LogoutFilter filter = new LogoutFilter(config, "/");
 				filter.setDestroySession(true);

 				http
					.antMatcher("/logout")
					.addFilterBefore(filter, BasicAuthenticationFilter.class)
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
	 		}
	}
}
