package com.tenco.blog.repository;

import com.tenco.blog.model.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

// @Import: 테스트에서 사용할 커스텀 클래스를 추가로 로드
// BoardNativeRepository는 우리가 만든 클래스이므로 명시적으로 임포트 필요
// Spring Boot는 기본적으로 @Repository가 붙은 클래스만 자동 스캔
@Import(BoardNativeRepository.class)

// @DataJpaTest: JPA 관련 테스트만 로드하는 슬라이스 테스트
// 장점: 전체 애플리케이션 컨텍스트를 로드하지 않아 테스트 속도가 빠름
// 자동 설정: H2 인메모리 데이터베이스, EntityManager, TestEntityManager 등
// 트랜잭션: 각 테스트 메서드가 끝나면 자동으로 롤백(데이터 초기화)
@DataJpaTest
public class BoardNativeRepositoryTest {

    // @Autowired: 테스트에서 의존성 주입
    // Spring 테스트 컨텍스트에서 BoardNativeRepository 인스턴스를 자동 주입
    // 필드 주입 방식 (테스트에서는 생성자 주입보다 필드 주입이 간편)
    @Autowired
    private BoardNativeRepository boardNativeRepository;

    @Test
    public void findAll_test(){
        // given: 테스트를 위한 준비 단계
        // data.sql 파일에 의해 4개의 더미 데이터가 이미 삽입됨
        // @DataJpaTest가 자동으로 data.sql을 실행하여 테스트 데이터 준비

        // when: 실제 테스트할 행동
        // findAll() 메서드를 실행하여 모든 게시글을 조회
        List<Board> boardList = boardNativeRepository.findAll();

        // then: 결과 검증
        // 디버깅을 위한 출력 (실제 운영에서는 로그 사용 권장)
        System.out.println("findAll_test/size : "+boardList.size());
        System.out.println("findAll_test/username : "+boardList.get(2).getUsername());

        // AssertJ 라이브러리를 사용한 검증
        // 장점: 더 직관적이고 읽기 쉬운 테스트 코드 작성 가능
        // JUnit 기본 assertEquals보다 에러 메시지가 명확함
        Assertions.assertThat(boardList.size()).isEqualTo(4);
        // 실제 값 - ssar , 예상 값 cos
        Assertions.assertThat(boardList.get(2).getUsername()).isEqualTo("cos");

        // 다양한 AssertJ 검증 메서드
        // .isNotNull() - null이 아닌지 확인
        // .isGreaterThan(3) - 3보다 큰지 확인
        // .contains("제목1") - 특정 요소를 포함하는지 확인
    }

    // 추가 테스트 예시: 예외 상황 테스트
    @Test
    public void findAll_empty_test(){
        // given: 데이터를 모두 삭제하여 빈 상태 만들기
        // (실제로는 @Sql 어노테이션을 사용하여 특정 SQL 실행 가능)

        // when
        List<Board> boardList = boardNativeRepository.findAll();

        // then: 빈 리스트인지 확인
        Assertions.assertThat(boardList).isEmpty();
        // 또는 Assertions.assertThat(boardList.size()).isEqualTo(0);
    }
}
