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
		/**
		 * TODO: 로그인 성공시 핸들러
		 * 권한에 따라 redirect URL을 다르게 설정
		 * ADMIN -> admin 페이지로 이동
		 * USER, SOCIAL -> 메인 페이지로 이동
		 */
	}

}
