package com.example.forest.model;

/**
 * 댓글 카테고리
 *
 */
public enum ReplyCategory {

  등록순("등록순"),
  최신순("최신순");
 
 ReplyCategory(String value) {
     this.value = value;
 }
    
 private final String value;
 
 public String getValue() {
     return this.value;
 }
 
}
