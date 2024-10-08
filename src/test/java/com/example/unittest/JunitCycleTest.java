package com.example.unittest;

import org.junit.jupiter.api.*;

public class JunitCycleTest {

    // 전체 테스트를 시작하기 전 1회 실행, 메서드는 static 으로 선언 해야함.
    @BeforeAll
    static void beforeAll(){
        System.out.println("@BeforeAll");
    }

    // 테스트 케이스를 시작하기 전 마다 실행.
    @BeforeEach
    public void beforeEach(){
        System.out.println("@beforeEach");
    }

    @Test
    public void test1(){
        System.out.println("test1 실행");
    }

    @Test
    public void test2(){
        System.out.println("test2 실행");
    }

    @Test
    public void test3(){
        System.out.println("test3 실행");
    }

    // 전체 테스트 종료 시 1회 실행, 메서드는 static 으로 선언 해야함.
    @AfterAll
    static void afterAll(){
        System.out.println("@AfterAll");
    }

    // 테스트 케이스를 종료 시 마다 실행.
    @AfterEach
    public void afterEach(){
        System.out.println("@AfterEach");
    }

}