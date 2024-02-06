package com.springboot.aws.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication  //스프링 부트의 자동설정, 스프링 bean읽기와 생성 자동
                        //이 위치부터 설정을 읽기 때문에 항상 프로젝트의 최상단에 위치 필요
public class Application {  //이 프로젝트의 메인 클래스
    public static void main(String[] args){

        //내장WAS 실행 : 스프링부트에서는 내장was사용권장 (언제 어디서나 같은 환경에서 스프링 부트를 배포)
        SpringApplication.run(Application.class, args);
    }
}
