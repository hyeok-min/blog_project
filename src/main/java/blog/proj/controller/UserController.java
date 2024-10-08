package blog.proj.controller;

import blog.proj.dto.UserDto;
import blog.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    /*
    //계정 생성
    @GetMapping("/createUser")
    public String createUser(){
    }

    //계정 정보
    @GetMapping("/userInfo")
    public ResponseEntity<UserDto> userInfo(){
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //계정 수정
    @PostMapping("/userUpdate")
    public ResponseEntity<UserDto> userUpdate(){
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //계정 삭제
    @DeleteMapping("/userDelete")
    public ResponseEntity<Void> userDelete(){
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

     */

}
