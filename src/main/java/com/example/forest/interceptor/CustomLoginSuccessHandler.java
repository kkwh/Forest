package com.example.forest.interceptor;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.forest.model.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO: 로그인 성공시 핸들러 구현
	    String loginId = request.getParameter("loginId");
	    String password = request.getParameter("password");
	    
	    log.info("id = {}, pw = {}", loginId, password);

	    HttpSession session = request.getSession();
	    session.setAttribute("loginId", loginId);
	    
	    // 사용자의 역할(Role) 확인
        String role = authentication.getAuthorities().stream()
                                     .map(GrantedAuthority::getAuthority)
                                     .findFirst()
                                     .orElse("");

        // 관리자인지 확인하여 페이지 리다이렉트
        if (role.equals("ROLE_USER")) {
            response.sendRedirect("/"); // 일반 사용자 페이지로 리다이렉트
        } else {
            response.sendRedirect("/admin/main"); // 관리자 페이지로 리다이렉트
        }
	    
	}

}
