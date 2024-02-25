package com.springboot.aws.project.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//세션값을 가져오는 로직을 메소드 인자로 받아올 수 있도록 @LoginUser 어노테이션 생성
@Target(ElementType.PARAMETER)//이 어노테이션이 생성될 수 있는 위치를 지정
                              //파라미터로 지정했으므로 메소드의 파림터로 선언된 객체에서만 사용할 수 있다.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {   //@interface : 이 파일을 어노테이션 클래스로 지정한다는 뜻
}
