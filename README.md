```markdown
# 익명 블로그 V1 📝

## 프로젝트 소개

Spring Boot를 학습하며 단계별로 구현하는 익명 블로그 프로젝트입니다. 사용자 인증 없이 누구나 게시글을 작성, 조회, 수정, 삭제할 수 있는 기본적인 CRUD 기능을 제공합니다.

## 개발 환경

- **Java**: 21
- **Spring Boot**: 3.3.12
- **Build Tool**: Gradle
- **Database**: H2 (인메모리 DB)
- **Template Engine**: Mustache
- **IDE**: IntelliJ IDEA 권장

## 사용 기술 스택

### Backend
- **Spring Boot Starter Web**: RESTful API 및 웹 애플리케이션 개발
- **Spring Boot Starter Data JPA**: ORM을 통한 데이터베이스 접근
- **Spring Boot DevTools**: 개발 시 자동 재시작 및 LiveReload
- **Lombok**: 보일러플레이트 코드 자동 생성

### Frontend
- **Mustache**: 로직이 없는 템플릿 엔진
- **Bootstrap**: 반응형 웹 UI 프레임워크

### Database
- **H2 Database**: 개발/테스트용 인메모리 데이터베이스
- **MySQL Connector**: 운영 환경 데이터베이스 연결 지원

### Test
- **JUnit 5**: Java 단위 테스트 프레임워크
- **AssertJ**: 직관적인 테스트 검증 라이브러리
- **Spring Boot Test**: 스프링 통합 테스트 지원

### Utility
- **Apache Commons Lang3**: 날짜 포맷팅 등 유틸리티 기능

## 주요 기능

### 1. 게시글 관리 📋
- **게시글 목록 조회**: 최신순으로 정렬된 게시글 목록
- **게시글 상세 보기**: 특정 게시글의 상세 내용 조회
- **게시글 작성**: 제목, 내용, 작성자명으로 새 게시글 생성
- **게시글 수정**: 기존 게시글의 내용 수정
- **게시글 삭제**: 불필요한 게시글 제거

### 2. 개발자 도구 🛠️
- **H2 콘솔**: 데이터베이스 상태 실시간 확인 (`/h2-console`)
- **SQL 로깅**: 실행되는 SQL 쿼리 콘솔 출력
- **Hot Reload**: 코드 변경 시 자동 재시작

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/tenco/blog/
│   │   ├── controller/          # 웹 요청 처리
│   │   │   ├── BoardController.java
│   │   │   └── UserController.java
│   │   ├── model/               # 엔티티 클래스
│   │   │   └── Board.java
│   │   ├── repository/          # 데이터 접근 계층
│   │   │   └── BoardNativeRepository.java
│   │   └── util/                # 유틸리티 클래스
│   │       └── MyDateUtil.java
│   └── resources/
│       ├── templates/           # Mustache 템플릿
│       │   ├── layout/
│       │   ├── board/
│       │   └── user/
│       ├── static/              # 정적 리소스
│       ├── db/
│       │   └── data.sql        # 초기 데이터
│       └── application-*.yml    # 설정 파일
└── test/                        # 테스트 코드
```

## 데이터베이스 스키마

### board_tb
| 컬럼명 | 타입 | 제약조건 | 설명 |
|--------|------|----------|------|
| id | INTEGER | PK, AUTO_INCREMENT | 게시글 고유 ID |
| title | VARCHAR | NOT NULL | 게시글 제목 |
| content | TEXT | NOT NULL | 게시글 내용 |
| username | VARCHAR | NOT NULL | 작성자명 |
| created_at | TIMESTAMP | NOT NULL | 작성 시간 |

## API 엔드포인트

### 게시글 관리
| HTTP Method | URL | 설명 |
|-------------|-----|------|
| GET | `/` | 게시글 목록 페이지 |
| GET | `/board/save-form` | 게시글 작성 폼 |
| POST | `/board/save` | 게시글 저장 |
| GET | `/board/{id}` | 게시글 상세보기 |
| GET | `/board/{id}/update-form` | 게시글 수정 폼 |
| POST | `/board/{id}/update` | 게시글 수정 |
| POST | `/board/{id}/delete` | 게시글 삭제 |

### 사용자 페이지 (준비 단계)
| HTTP Method | URL | 설명 |
|-------------|-----|------|
| GET | `/join-form` | 회원가입 폼 |
| GET | `/login-form` | 로그인 폼 |
| GET | `/user/update-form` | 회원정보 수정 폼 |
| GET | `/logout` | 로그아웃 |

## 설치 및 실행

### 1. 프로젝트 클론
```bash
git clone [repository-url]
cd anonymous-blog-v1
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. 접속 확인
- **메인 페이지**: http://localhost:8080
- **H2 콘솔**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:test`
  - Username: `sa`
  - Password: (공백)

## 학습 포인트

### 1. Spring Boot 핵심 개념
- **의존성 주입**: `@RequiredArgsConstructor`를 통한 생성자 주입
- **어노테이션 기반 설정**: `@Controller`, `@Repository`, `@Entity` 등
- **자동 설정**: Spring Boot의 Convention over Configuration

### 2. MVC 패턴
- **Controller**: HTTP 요청 처리 및 비즈니스 로직 호출
- **Repository**: 데이터 접근 계층 분리
- **View**: Mustache 템플릿을 통한 동적 HTML 생성

### 3. JPA와 네이티브 쿼리
- **엔티티 매핑**: `@Entity`, `@Id`, `@GeneratedValue`
- **네이티브 쿼리**: 직접 SQL 작성을 통한 데이터베이스 조작
- **트랜잭션**: `@Transactional`을 통한 데이터 일관성 보장

### 4. 테스트 주도 개발
- **단위 테스트**: Repository 계층 테스트
- **Given-When-Then**: 테스트 코드 작성 패턴
- **@DataJpaTest**: JPA 관련 슬라이스 테스트

### 5. RESTful 설계
- **자원 중심 URL**: `/board/{id}` 형태의 URL 설계
- **HTTP 메서드**: GET(조회), POST(생성/수정/삭제) 적절한 사용
- **상태 코드**: 리다이렉트를 통한 PRG 패턴 구현

## 다음 단계 (V2 예정)

- **JPA Persistence Context** 도입
- **Spring Data JPA Repository** 사용
- **연관관계 매핑** 학습
- **쿼리 메서드** 활용
- **페이징 처리** 구현

## 기여하기

이 프로젝트는 학습 목적으로 제작되었습니다. 개선사항이나 버그 발견 시 이슈를 등록해 주세요.

## 라이선스

이 프로젝트는 교육 목적으로 자유롭게 사용할 수 있습니다.

---

**개발자**: 취업 준비생을 위한 Spring Boot 실무 학습 프로젝트  
**버전**: V1 (익명 블로그 기본 CRUD)
```
