package com.example.unittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JunitTest {
    @DisplayName("1+2는 3이다.")
    @Test
    public void JunitTest() {

        //given 주어진 값
        int a = 1, b = 2;
        int expected = 4;

        // when 연산
        int result = a + b; //3

        // then 검증
        // 왼쪽에는 기대하는 결과값, 오른쪽에는 연산반환값
        Assertions.assertEquals(expected, result);
    }
}
