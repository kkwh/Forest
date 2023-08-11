package com.example.forest.dto.board;

import lombok.Data;

@Data
public class BoardRevokeDto {
	
	private long boardId; // 게시판 아이디
	private long userId; // 현재 게시판 관리자 유저 아이디
	private long userToId; // 게시판 권한을 넘겨줄 유저 아이디

}
