package blog.proj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//로그인과 홈
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping
    public String home() {
        return "index.html";
    }
}
