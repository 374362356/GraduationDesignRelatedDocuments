package com.pzh.zp.security;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.security.Handler.FailureHandler;
import com.pzh.zp.security.Handler.LogoutHandlerImpl;
import com.pzh.zp.security.Handler.SuccessHandler;
import com.pzh.zp.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private CustomAuthorizeFilter customAuthorizeFilter;
    @Autowired
    private SuccessHandler successHandler;
    @Autowired
    private FailureHandler failureHandler;
    @Autowired
    private LogoutHandlerImpl logoutHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/conference/**","/static/**","/user/**","/person/**","/reports/**","/news/**"
                                    ,"/staff/**","/suggestion/**","/permission/**","/role/**","/upload/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().and()
            .addFilterBefore(customAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, e) -> ResponseUtil.out(response, ResultVo.fail("请登陆再访问")))
                .accessDeniedHandler(
                        (request, response, e) -> ResponseUtil.out(response,ResultVo.fail("无权限")))
                .and()
                .formLogin().usernameParameter("userName").loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login","/user/register","/logout").permitAll()
                .antMatchers("/").authenticated()
                //自定义权限控制器  其他所有请求 都要进行校验 返回True表示通过 返回false表示不能访问
                .anyRequest().access("@AccessService.hasPermission(request,authentication)")
                .and()
                .logout().logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .and()
                //修改session策略 无状态应用 STATELESS 因为没用到session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().and().csrf().disable();
        http.addFilterAt(UserAuthenticationFilterBean(),UsernamePasswordAuthenticationFilter.class);

    }

    private UserAuthenticationFilter UserAuthenticationFilterBean() throws Exception {
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter();
        userAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        userAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        return userAuthenticationFilter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); //允许所有的跨域访问 有需要自己重写
        configuration.setAllowedMethods(Arrays.asList("*")); //允许所有方法的请求 有需要自己重写
        configuration.setAllowedHeaders(Arrays.asList("*")); //请求头  有需要自己重写
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", configuration); //代表可以访问所有的资源
        return source;
    }
}
