package com.example.forest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.model.ReReply;
import com.example.forest.model.Reply;

public interface ReReplyRepository extends JpaRepository<ReReply, Long> {
	
	/**
	 * 댓글에 달린 모든 대댓글 삭제
	 * @param reply
	 */
	@Transactional
	@Modifying
	void deleteByReply(@Param("reply") Reply reply);

}
