package com.classic.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.regex.Pattern;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    private static final Pattern UNAUTHENTICATED_ROUTES = Pattern.compile("/user/verification\\?email=\\S+@\\S+\\.\\S+&mainChar=.*");//reset password end-point

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore).resourceId("oauth2-server");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/register").permitAll()
	    	    .regexMatchers(UNAUTHENTICATED_ROUTES.pattern()).permitAll()
                .antMatchers("/user/verify/{**}").permitAll()
                .antMatchers("/user/update/password").permitAll()
                .anyRequest().access("#oauth2.hasScope('read')");
    }

}
