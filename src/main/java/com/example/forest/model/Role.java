package com.example.forest.model;

/**
 * 사용자 권한
 * @author User
 *
 */
public enum Role {
	
	USER("ROLE_USER", "USER"), // 일반 회원
	ADMIN("ROLE_ADMIN", "ADMIN"), // 관리자
	SOCIAL("ROLE_SOCIAL", "SOCIAL"); // 소셜 로그인 회원
	
	private final String key;
	private final String value;
	
	Role(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}

}
