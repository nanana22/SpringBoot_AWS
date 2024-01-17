package com.springboot.aws.project.web.dto;
import org.junit.Test;
//Junit이 아닌 asserj의 assertThat 사용
//장점: 추가적인 라이브러리가 필요하지 않다 , 자동완성이 확실함
import static  org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        //assertThat : 테스트검증라이브러리의 검증 메소드
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);


        //실행성공으로 알 수 있는 점
        // 롬복의 @Getter 로 get메소드가, @RequiredArgsConstructor로 생성자가 자동 생성됨!
    }
}
