package conocimiento.cultural;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import conocimiento.cultural.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	String[] resources = new String[] {
			"/include/**","/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
	};
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .authorizeRequests()
        .antMatchers(resources).permitAll()  
        .antMatchers("/","/index","/destinos","/destinos/ayabaca","/destinos/huancabamba","/destinos/morropon","/destinos/paita","/destinos/piura","/destinos/sechura","/destinos/sullana","/destinos/talara").permitAll()//todos tienen acceso
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")//logueo
            .permitAll()
            .defaultSuccessUrl("/userForm")//logueo exitoso
            .failureUrl("/login?error=true")//si falla el logueo
            .usernameParameter("username")//pertenecen a los name de los input del formulario
            .passwordParameter("password")//  ""    ""
            .and()
            .csrf().disable()
        .logout()
            .permitAll()
            .logoutSuccessUrl("/login?logout");//cierre de sesion
    }
    
    BCryptPasswordEncoder bCryptPasswordEncoder;//encripta la contraseña

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);//nivel de la encriptacion hay hasta el 31
        return bCryptPasswordEncoder;
    }
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
    	//Especificar el encargado del login y encriptacion del password
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}