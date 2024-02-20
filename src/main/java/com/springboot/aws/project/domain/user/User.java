package com.springboot.aws.project.domain.user;

import com.springboot.aws.project.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)    //jpa로 db저장할 때 Enum값을 어떤 형태로 저장할지 결정
                                    //기본적으로는 int로 된 숫자가 저장
                                    //숫자로 저장 시 그 값이 무슨 코드를 의미하는지 알 수 없으므로 문자열로 저장되도록 선언
    @Column(nullable = false)
    private com.springboot.aws.project.domain.user.Role role;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name= name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
