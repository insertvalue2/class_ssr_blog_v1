package com.tenco.blog.repository;

import com.tenco.blog.model.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @RequiredArgsConstructor: final 필드에 대한 생성자를 자동 생성
// 의존성 주입을 위한 Lombok 어노테이션
@RequiredArgsConstructor
// @Repository: 스프링이 데이터 접근 계층으로 인식
// 데이터베이스 예외를 스프링 예외로 변환해줌
@Repository
public class BoardNativeRepository {

    // EntityManager: JPA의 핵심 인터페이스
    // 데이터베이스와의 모든 작업을 담당
    private final EntityManager em;

    // @Transactional: 이 메서드를 트랜잭션으로 실행
    // 성공하면 커밋, 실패하면 롤백
    @Transactional
    public void save(String title, String content, String username){
        // createNativeQuery: 직접 SQL을 작성해서 실행
        // ?는 파라미터 위치를 나타냄 (1번부터 시작)
        Query query =
                em.createNativeQuery("insert into board_tb(title, content, username, created_at) values(?,?,?,now())");

        // setParameter: ?에 실제 값을 바인딩
        // SQL Injection 공격을 방지하는 안전한 방법
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, username);

        // executeUpdate: INSERT, UPDATE, DELETE 쿼리 실행
        // SELECT는 executeQuery 사용
        query.executeUpdate();
    }

    // 게시글 목록 조회 메서드
    public List<Board> findAll(){
        // createNativeQuery의 두 번째 매개변수: 결과를 매핑할 엔티티 클래스
        // Board.class를 지정하면 쿼리 결과를 Board 객체로 자동 변환
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);

        // getResultList(): 여러 행의 결과를 List로 반환
        // getSingleResult(): 단일 결과만 반환 (한 개의 데이터만 있을 때)
        // order by id desc: 최신글이 위로 오도록 내림차순 정렬
        return query.getResultList();
    }

    // 특정 ID로 게시글 단건 조회
    public Board findById(int id) {
        // WHERE 조건을 사용한 단건 조회 쿼리
        // 기본키(Primary Key)를 사용한 조회는 가장 빠른 검색 방법
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);

        // 파라미터 바인딩: SQL Injection 방지
        // 직접 문자열을 연결하지 않고 ?를 사용하여 안전하게 값 전달
        query.setParameter(1, id);

        // getSingleResult(): 단일 결과만 반환하는 메서드
        // 주의: 결과가 없으면 NoResultException, 결과가 2개 이상이면 NonUniqueResultException 발생
        // 실무에서는 try-catch 또는 Optional을 사용한 예외 처리 필요
        return (Board) query.getSingleResult();
    }
}