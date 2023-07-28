package com.example.forest.interceptor;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO: 로그인 성공시 핸들러 구현
	    String loginId = request.getParameter("loginId");
	    String password = request.getParameter("password");
	    
	    // 1. UserRepository에서 해당 정보를 가지고 있는 사용자가 있는지 확인
	    
	    // 2. null이 아니면 USER, ADMIN 중 하나
	    
	    // 3. USER일 경우
	    response.sendRedirect("/");
	    
	    // 4. ADMIN일 경우
	    response.sendRedirect("/admin/main");
	}

}
