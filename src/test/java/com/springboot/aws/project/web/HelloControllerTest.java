package com.springboot.aws.project.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)//스프링부트테스트와 JUnit 사이에 연결자역할
@WebMvcTest(controllers = HelloController.class)  //여러 스프링 테스트 어노테이션 중 Web에 집중할 수 있는 어노테이션

public class HelloControllerTest {

    @Autowired  //스프링이 관리하는 빈 주입받는다.
    private MockMvc mvc;    //웹API 테스트 시 사용 (테스트의 시작점)

    @Test
    public void hello가_리턴된다() throws  Exception{
        String hello = "hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                //결과 검증
                .andExpect(status().isOk()) //헤더의 상태 검증 (200, 404, ...)
                .andExpect(content().string(hello)); //응답 본문의 내용 검증 (hello인지 아닌지)
    }

    @Test
    public void helloDtd가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

        //param
        //1.api테스트할 때 사용될 요청파라미터 설정
        //2.string만 허용

        //jsonPath
        //1.JSON 응답값을 필드별로 검증할 수 있는 소드
        //2.$를 기준으로 필드명 명시
    }
}
