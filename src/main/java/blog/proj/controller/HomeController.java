package blog.proj.controller;

import blog.proj.dto.UserDto;
import blog.proj.service.HomeService;
import blog.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//로그인과 홈
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class HomeController {
    private final HomeService homeService;
    //홈
    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("home");
    }
//    //로그인사용자 홈
//    @GetMapping("/home/{user}")
//    public ResponseEntity<> home() {
//        return ResponseEntity.ok("home");
//    }

    //로그인
    @PostMapping("/login")
    public  ResponseEntity<?> loginUser(@RequestBody UserDto userDto){
        try {
            // 로그인 처리
            UserDetails userDetails = homeService.loginUser(userDto.getEmail(), userDto.getPassWord());

            // 로그인 성공 시 사용자 정보 반환
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } catch (Exception e) {
            // 로그인 실패 시 에러 메시지 반환
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
