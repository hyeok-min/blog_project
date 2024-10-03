package blog.proj.controller;

import blog.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    //계정 생성
    @GetMapping("/createUser")
    public String createUser(){

    }
    //계정 정보
    @GetMapping("/userInfo")
    public String userInfo(){
        return ;
    }

    //계정 수정
    @PostMapping("/userUpdate")
    public String userUpdate(){
        return ;
    }

    //계정 삭제
    @DeleteMapping("/userDelete")
    public String userDelete(){
        return ;
    }

}
