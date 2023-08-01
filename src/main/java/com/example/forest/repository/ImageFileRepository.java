package com.example.forest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.model.ImageFile;


public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
	
	@Query("select f from ImageFile f "
			+ " where f.boardId = :boardId")
	ImageFile findByBoardId(@Param("boardId") long boardId);

}
