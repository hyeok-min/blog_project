package blog.proj.controller;

import blog.proj.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//로그인과 홈
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    //로그인
    @GetMapping("/home/login")
    public String login() {
        return "index.html";
    }

    //홈
    @GetMapping("/home")
    public String home() {
        return "index.html";
    }
    //로그인한 사용자 홈
//    @GetMapping("/home/{user}")
//    public ResponseEntity loginUser(@PathVariable String user){
//    }
}
