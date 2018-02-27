package uk.co.nstech.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user").password("password").roles("USER").build());
    manager.createUser(User.withUsername("admin").password("password").roles("USER", "ADMIN").build());
    return manager;
  }

  @Configuration
  @Order(2)
  public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          // Only user this config if this ant matcher is met
          .antMatcher("/admin/**")
          // Authorize all paths under this matcher if they have the role of admin
          .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
          // THIS commented out block will work but antMatchers will overwrite any previous values set.
          // This means that you can't use 'http.authorizeRequests().antMatchers' in multiple config classes
          //      http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
          .and()
          .formLogin().loginPage("/admin/loginAdmin").loginProcessingUrl("/admin/login").permitAll().failureUrl("/admin/loginAdmin?error=loginError")
          //          .formLogin().loginPage("/loginAdmin").permitAll().failureUrl("/loginAdmin?error=loginError")
          .and()
          .httpBasic().and()
          .logout().logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout")).logoutSuccessUrl("/")
          .deleteCookies("JSESSIONID")
          .invalidateHttpSession(true)
          .permitAll();
    }
  }

  @Configuration
  @Order(1)
  public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //      http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("USER")
      //      http.requestMatcher(new AntPathRequestMatcher("/user/**")).authorizeRequests().anyRequest().hasRole("USER")
      //      http.authorizeRequests().antMatchers("/user/**").hasRole("USER")
      http.antMatcher("/user/**").authorizeRequests().antMatchers("/user/**").hasRole("USER")
          .and()
          // Using this form the login processing input and login page all have to be under /user
          .formLogin().loginPage("/user/loginUser").loginProcessingUrl("/user/login").permitAll().failureUrl("/user/loginUser?error=loginError")
          .and()
          .httpBasic().and()
          .logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/")
          .deleteCookies("JSESSIONID")
          .invalidateHttpSession(true)
          .permitAll();
    }
  }

  @Configuration
  @Order(3)
  public static class App3ConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().anyRequest().permitAll();
    }
  }
}
