package com.example.forest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.forest.interceptor.CustomAccessDeniedHandler;
import com.example.forest.interceptor.CustomLoginSuccessHandler;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//내가 할 떈 지우기
//	@Bean
//    public UserDetailsService inMemoryUserDetailsService() {
//        // 사용자 상세 정보
//        UserDetails user1 = User
//                .withUsername("user1") // 로그인할 때 사용할 사용자 이름(아이디)
//                .password(passwordEncoder().encode("1111")) // 로그인할 때 사용할 비밀번호
//                .roles("USER") // 사용자 권한(USER, ADMIN, ...)
//                .build(); // UserDetails 객체 생성.
//        
//        UserDetails user2 = User
//                .withUsername("user2")
//                .password(passwordEncoder().encode("2222"))
//                .roles("USER", "ADMIN")
//                .build();
//        
//        UserDetails user3 = User
//                .withUsername("user3")
//                .password(passwordEncoder().encode("3333"))
//                .roles("ADMIN")
//                .build();
//        
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }
	
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
	
}
