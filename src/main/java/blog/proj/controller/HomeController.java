package blog.proj.controller;

import blog.proj.dto.UserDto;
import blog.proj.repository.UserRepository;
import blog.proj.service.HomeService;
import blog.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//로그인과 홈
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {
    private final HomeService homeService;
    private final UserRepository userRepository; //테스트용
    private final UserService userService;
    //홈
    @GetMapping("/home")
    public ResponseEntity<String> home() {

        Authentication authenticationtest = SecurityContextHolder.getContext().getAuthentication();
        userRepository.findByLoginUserEmail(authenticationtest.getName());
        log.info("로그인 사용자 테스트 log : "+authenticationtest.getName());
        return ResponseEntity.ok("home");
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserDto> getTargetHome(@PathVariable("name") String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.userinfo(authentication.getName());
        log.info(String.valueOf(user));
        return ResponseEntity.ok(user);
    }


}
