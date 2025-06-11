package com.tenco.blog.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}