package com.example.forest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
@Entity
@Table(name = "BOOKMARKS")
@SequenceGenerator(name = "BOOKMARKS_SEQ_GEN", sequenceName = "BOOKMARKS_SEQ", allocationSize = 1)
public class Bookmark {
	
	@Id
	@GeneratedValue(generator = "BOOKMARKS_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	private long boardId;
	
	private long userId;

}
