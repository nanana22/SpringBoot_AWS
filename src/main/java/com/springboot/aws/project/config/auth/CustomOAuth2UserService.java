package com.springboot.aws.project.config.auth;

import com.springboot.aws.project.config.auth.dto.OAuthAttributes;
import com.springboot.aws.project.config.auth.dto.SessionUser;
import com.springboot.aws.project.domain.user.User;
import com.springboot.aws.project.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

//구글 로그인 이후 가져온 사용자의 정보들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession   httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //registrationId : 현재 로그인 진행 중인 서비스를 구분하는 코드(네이버로그인인지, 구글로그인인지)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //userNameAttributeName : OAuth2로그인 진행 시 키가 되는 필드값을 말한다.(pk같은의미)
        //구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본지원하지 않음 (구글: "sub")
        //이후 네이버로그인과 구글 로그인을 동시 지원할 때 사용된다.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        //이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //sessionUser: 세션에 사용자 정보를 저장하기 위한 Dto클래스
        //왜 User 클래스를 사용하지않고 새로 만들어서 쓰느냐?
        //-> User클래스에 세션을 저장하면 '직렬화를 구현하지 않았다'라는 에러가 뜸
        //   User클래스가 엔티티이기 때문에 (언제 다른 엔티티와 관계가 형성될지 모름) 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되어
        //   성능이슈, 부수 효과가 발생할 확률이 엎음
        //   그러므로 직렬화 기능을 가진 세션 Dto를 하나 추가로 만드는 것이 나은 선택
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey() );
    }

    //구글 사용자 정보가 업데이트 되었을 때를 대비
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
