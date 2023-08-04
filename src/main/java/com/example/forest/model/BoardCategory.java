package com.example.forest.model;

/**
 * 게시판 카테고리
 * @author User
 *
 */
public enum BoardCategory {
	
	/**
	 * TODO: 필요한 카테고리 향후 추가
	 */
	SPORTS("스포츠"),
	IT("IT"),
    IDOL("아이돌"),
    ANIMATION("만화/애니"),
    GAME("게임"),
    UNIVERSITY("대학");
	
	BoardCategory(String value) {
		this.value = value;
	}
	
	private final String value;
	
	public String getValue() {
		return this.value;
	}

}
