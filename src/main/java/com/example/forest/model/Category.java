package com.example.forest.model;

/**
 * 게시판 카테고리
 * @author User
 *
 */
public enum Category {
	
	/**
	 * TODO: 필요한 카테고리 향후 추가
	 */
	SPORTS("SPORTS"),
	IT("IT");
	
	Category(String value) {
		this.value = value;
	}
	
	private final String value;
	
	public String getValue() {
		return this.value;
	}

}
