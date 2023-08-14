package com.example.forest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.model.Event;


public interface EventRepository extends JpaRepository<Event, Long> {

    // id 내림차순 정렬:
    // select * from EVENT order by ID desc
    List<Event> findByOrderByIdDesc();
    
    // 제목으로 검색:
    // select * from event e
    // where lower(e.title) like lower('%' || ? || '%')
    // order by e.id desc
    Page<Event> findByTitleContainsIgnoreCaseOrderByIdDesc(String title, Pageable pageable);
    
    // 내용으로 검색:
    // select * from event e
    // where lower(e.content) like lower('%' || ? || '%')
    // order by e.id desc
    Page<Event> findByContentContainsIgnoreCaseOrderByIdDesc(String content, Pageable pageable);
    
    // 작성자로 검색:
    // select * from event e
    // where lower(e.author) like lower('%' || ? || '%')
    // order by e.id desc
    Page<Event> findByAuthorContainsIgnoreCaseOrderByIdDesc(String author, Pageable pageable);
    
    // 제목 또는 내용으로 검색:
    // select * from event e
    // where lower(e.title) like lower('%' || ? || '%')
    //    or lower(e.content) like lower('%' || ? || '%')
    // order by e.id desc
    Page<Event> findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByIdDesc(String title, String content, Pageable pageable);
    
    // JPQL(JPA Query Language) 문법으로 쿼리를 작성하고, 그 쿼리를 실행하는 메서드 이름을 설정:
    // JPQL은 Entity 클래스의 이름과 필드 이름들을 사용해서 작성.
    // (주의) DB 테이블 이름과 컬럼 이름을 사용하지 않음!
    @Query(
        "select e from Event e " +
        " where lower(e.title) like lower('%' || :keyword || '%') " + 
        " or lower(e.content) like lower('%' || :keyword || '%') " +
        " order by e.id desc"
    )
    Page<Event> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    // 페이징 메서드
    // 검색과 동시에 페이징 가능
    Page<Event> findAll(Pageable pageable);
    
}