package com.example.config;


import com.alibaba.fastjson.JSON;
import com.example.enums.AppHttpCodeEnum;
import com.example.filter.JwtAuthenticationTokenFilter;
import com.example.utils.WebUtils;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> {
                    auth
                            .requestMatchers("/user/login").anonymous()
//                            .requestMatchers("/logout","/user/userInfo","/upload").authenticated()
                            .anyRequest()
                            .authenticated();
                })
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(item -> item.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(
                        exceptions -> exceptions
                                .authenticationEntryPoint((req, rsp, e) -> {
                                    ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
                                    WebUtils.renderString(rsp, JSON.toJSONString(result));
                                }).accessDeniedHandler((req, rsp, e) -> {
                                    ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                                    WebUtils.renderString(rsp, JSON.toJSONString(result));
                                }))

        ;
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.logout(logout->logout.disable());
        // 构建过滤链并返回
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
