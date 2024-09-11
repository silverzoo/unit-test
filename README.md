# 단위 테스트와 Junit

## 1. 유닛 테스트란?
   ### 1.1 유닛 테스트의 정의
   소프트웨어 개발에서 유닛 테스트의 의미와 목적
   ### 1.2 유닛 테스트의 중요성
   코드 품질 향상
   버그 조기 발견
   리팩토링과 유지 보수의 용이성
   ### 1.3 유닛 테스트의 기본 원칙
   독립성
   반복 가능성
   자동화

## 2. JUnit 소개
   ### 2.1 JUnit의 정의
   JUnit의 역할과 목적
   ### 2.2 JUnit의 역사와 발전
   JUnit의 역사적 배경과 주요 버전
   ### 2.3 JUnit의 주요 기능
   어노테이션
   테스트 실행 및 결과 보고

## 3. JUnit 기본 구성
### 3.1 JUnit 환경 설정
   Maven/Gradle 의존성 추가
   IDE 설정

   ### 3.2 JUnit 어노테이션
   @Test: 테스트 메서드 정의
   @BeforeEach / @AfterEach: 테스트 전후 실행되는 메서드
   @BeforeAll / @AfterAll: 모든 테스트 전후 실행되는 메서드
   @Disabled: 테스트 비활성화
   @Tag: 테스트 그룹화

   ### 3.3 테스트 메서드 작성
   기본적인 테스트 메서드 작성법
   테스트 입력과 기대 결과 설정

## 4. JUnit 고급 기능
   ### 4.1 매개변수화된 테스트
   @ParameterizedTest 어노테이션을 사용한 테스트

   ### 4.2 예외 테스트
   assertThrows를 사용한 예외 검증

   ### 4.3 테스트 케이스의 정렬
   @TestMethodOrder를 사용한 테스트 실행 순서 지정

   ### 4.4 테스트 결과 검증
   다양한 Assertion 메서드 사용법 (assertEquals, assertTrue, assertFalse, 등)

## 5. 테스트와 Mocking
   ### 5.1 Mocking의 필요성
   Mock 객체의 개념과 용도

   ### 5.2 Mockito 프레임워크 소개
   Mockito의 기능 및 사용 예

   ### 5.3 Mockito를 이용한 Mock 객체 생성
   @Mock 어노테이션
   when과 thenReturn을 사용한 Mock 설정

   ### 5.4 Mock 객체와 상호작용 검증
   verify를 사용한 메서드 호출 검증