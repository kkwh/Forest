package com.example.forest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.forest.handler.CustomAccessDeniedHandler;
import com.example.forest.handler.CustomLoginSuccessHandler;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**");
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        
        // 로그인 페이지 설정 //기본 로그인 페이지 사용
        //http.formLogin(Customizer.withDefaults());
        
        // 로그인 처리 (내가 만들 html, 커스텀)
        http.formLogin(formLogin -> //이걸 꺼놔서 username파라미터를 userId라고 못읽나?
        	formLogin
        		.loginPage("/user/login")
        		.loginProcessingUrl("/user/login/process")
	        	.usernameParameter("loginId")
	        	.passwordParameter("password")
	        	.successHandler(new CustomLoginSuccessHandler())
    	);
        
        // 로그아웃 처리
       http.logout((logout) -> 
        	logout
        		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        		.logoutSuccessUrl("/")
        );
        
        // 접근 제한 처리
        http.exceptionHandling(exceptionHandling -> 
        	exceptionHandling.accessDeniedHandler(new CustomAccessDeniedHandler())
        );
        
        // 로그아웃 이후 이동할 페이지 - 메인 페이지
        http.logout((logout) -> logout.logoutSuccessUrl("/"));
        
        return http.build();
    }
	
	@Bean
    public Storage storage() {
        return StorageOptions.getDefaultInstance().getService();
    }
	
}
