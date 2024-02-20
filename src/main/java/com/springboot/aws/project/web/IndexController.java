package com.springboot.aws.project.web;

import com.springboot.aws.project.config.auth.dto.SessionUser;
import com.springboot.aws.project.service.posts.PostsService;
import com.springboot.aws.project.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){   //서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
                                        //여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
        model.addAttribute("posts", postsService.findAllDesc());

        //[시작]인덱스에 userName을 사용할 수 있게 model에 저장
        //앞서 저장된 customOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성함
        //로그인 성공시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user!=null){
            model.addAttribute("userName", user.getName());
        }
        //[끝]

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
