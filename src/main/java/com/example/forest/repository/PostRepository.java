package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.dto.post.PostWithLikesCount;
import com.example.forest.dto.post.PostWithLikesCount2;
import com.example.forest.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    // id 내림차순 정렬:
    // select * from POSTS order by ID desc
    List<Post> findByOrderByIdDesc();
    
    // 제목으로 검색:
    // select * from posts p
    // where lower(p.title) like lower('%' || ? || '%')
    // order by p.id desc
    List<Post> findByPostTitleContainsIgnoreCaseOrderByIdDesc(String title);
    
    // 내용으로 검색:
    // select * from posts p
    // where lower(p.content) like lower('%' || ? || '%')
    // order by p.id desc
    List<Post> findByPostContentContainsIgnoreCaseOrderByIdDesc(String content);
    
    // 작성자로 검색:
    // select * from posts p
    // where lower(p.nickname) like lower('%' || ? || '%')
    // order by p.id desc
    List<Post> findByPostNicknameContainsIgnoreCaseOrderByIdDesc(String nickname);
    
    // 제목 또는 내용으로 검색:
    // select * from posts p
    // where lower(p.title) like lower('%' || ? || '%')
    //    or lower(p.content) like lower('%' || ? || '%')
    // order by p.id desc
    // List<Post> findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByIdDesc(String title, String content);
    
    // JPQL(JPA Query Language) 문법으로 쿼리를 작성하고, 그 쿼리를 실행하는 메서드 이름을 설정:
    // JPQL은 Entity 클래스의 이름과 필드 이름들을 사용해서 작성.
    // (주의) DB 테이블 이름과 컬럼 이름을 사용하지 않음!
    @Query(
        "select p from Post p " +
        " where lower(p.postTitle) like lower('%' || :keyword || '%') " + 
        " or lower(p.postContent) like lower('%' || :keyword || '%') " +
        " order by p.id desc"
    )
    List<Post> searchByKeyword(@Param("keyword") String keyword);
    
    
    // POST + 좋아요 수 조회
    @Query("SELECT new com.example.forest.dto.post.PostWithLikesCount(p.id, p.postType, p.postTitle, p.postNickname, p.createdTime, p.postViews, "
            + " (SELECT COUNT(l.id) FROM Likes l where l.post = p and l.likeDislike = 1) as likesCount) "
            + " FROM Post p LEFT JOIN Likes l "
            + " ON p = l.post "
            + " GROUP BY p.id, p.postType, p.postTitle, p.postNickname, p.createdTime, p.postViews "
            + " ORDER BY p.id desc")
    List<PostWithLikesCount> findAllPostsWithLikesCount();
    
    
    // 인기글 조회(POST + 좋아요 수)
    @Query("SELECT new com.example.forest.dto.post.PostWithLikesCount2(p.id as id, p.postType as postType, p.postTitle as postTitle, p.postNickname as postNickname, p.createdTime as createdTime, p.postViews as postViews, "
            + " (SELECT COUNT(l.id) FROM Likes l WHERE l.post = p AND l.likeDislike = 1) as likesCount, "
            + " (SELECT COUNT(l.id) FROM Likes l WHERE l.post = p AND l.likeDislike = 1) - "
            + " (SELECT COUNT(l.id) FROM Likes l WHERE l.post = p AND l.likeDislike = 0) as likesDifference) "
            + " FROM Post p "
            + " GROUP BY p.id, p.postType, p.postTitle, p.postNickname, p.createdTime, p.postViews "
            + " HAVING (SELECT COUNT(l.id) FROM Likes l WHERE l.post = p AND l.likeDislike = 1) - "
            + " (SELECT COUNT(l.id) FROM Likes l WHERE l.post = p AND l.likeDislike = 0) >= 5 "
            + " ORDER BY p.id DESC")
    List<PostWithLikesCount2> findAllPostsWithLikesDifference();


    
}