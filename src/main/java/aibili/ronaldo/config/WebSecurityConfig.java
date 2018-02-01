package aibili.ronaldo.config;

import aibili.ronaldo.security.UrlUserService;
import aibili.ronaldo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Created by rtf on  2018/1/27.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UrlUserService urlUserService;
    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/ronaldo/login").permitAll()
                .antMatchers("/api/ronaldo/signup").permitAll()
                .antMatchers("/api/ronaldo/logout").permitAll()
                .antMatchers("/api/ronaldo/register").permitAll()
                .antMatchers("/api/ronaldo/profile").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
                .and()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .httpBasic()
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(csrfRequestMatcher);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(urlUserService).passwordEncoder(new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            }
        });
    }

    RequestMatcher csrfRequestMatcher = new RequestMatcher() {
        private Pattern allowedMethods = Pattern.compile("^(GET|POST|PUT|DELETE)$");
        // Disable CSFR protection on the following urls:
        private AntPathRequestMatcher[] requestMatchers = {
                new AntPathRequestMatcher("/respond"),
        };
        @Override
        public boolean matches(HttpServletRequest request) {
            // If the request match one url the CSFR protection will be disabled
            for (AntPathRequestMatcher rm : requestMatchers) {
                if (rm.matches(request)) { return false; }
            }
            return !allowedMethods.matcher(request.getMethod()).matches();
        }
    };

    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }
}
