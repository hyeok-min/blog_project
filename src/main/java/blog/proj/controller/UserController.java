package blog.proj.controller;

import blog.proj.config.UserDetailService;
import blog.proj.dto.UserDto;
import blog.proj.entity.User;
import blog.proj.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    //계정 생성
    @PostMapping("/createUser")
    public  ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        try {
            // 사용자 저장 로직
            userService.createUser(userDto); // 사용자 정보 저장

            // 회원가입 성공 시 메시지
            return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            // 회원가입 실패시 메시지
            return new ResponseEntity<>("create failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //로그인
    @PostMapping("/login")
    public  ResponseEntity<?> loginUser(@RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response){
        try {
            Authentication authenticationtest = SecurityContextHolder.getContext().getAuthentication();
            log.info("로그인 사용자 테스트1 : "+authenticationtest.getName());
            log.info("====로그인 처리");
            log.info(userDto.getEmail());
            log.info("{} == ",userDto.getPassWord());
            // 사용자 정보를 로드
            UserDetails userDetails = userDetailService.loadUserByUsername(userDto.getEmail());

            // 비밀번호 비교
            log.info("=====validatepass");

            if (!userDetailService.validatePassword(userDto.getPassWord(), userDetails.getPassword())) {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassWord());
            log.info("토큰 ==="+authToken);
            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("인증시도 ==="+authenticationtest);

            // 세션 생성 및 쿠키 설정
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // 쿠키 설정 (Optional)
            Cookie cookie = new Cookie("SESSION", session.getId());
            cookie.setHttpOnly(true); // 보안 설정: 클라이언트에서 접근 불가
            cookie.setPath("/"); // 쿠키의 유효 경로
            cookie.setMaxAge(-1); // 세션 쿠키로 설정 (브라우저 종료 시 삭제)
            response.addCookie(cookie);


            log.info("====로그인 성공처리");
            Map<String, Object> responses = new HashMap<>();
            responses.put("message", "success");
            responses.put("user", userDetails);
            log.info("로그인 사용자 테스트2 : "+authentication.getName());
            log.info("로그인 사용자 테스트3 : "+authentication.getPrincipal());



            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

/*
    //계정 정보
    @GetMapping("/userInfo")
    public ResponseEntity<UserDto> userInfo(){
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



 */


    //계정 수정
    @PutMapping("{name}/update")
    public ResponseEntity<UserDto> userUpdate(@RequestBody UserDto userDto,@PathVariable("name") String name){
        log.info(String.valueOf(userDto));
        log.info(userDto.getEmail());
        log.info(userDto.getNickName());
        log.info(userDto.getPassWord());
        userService.updateUser(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    //계정 삭제
    @DeleteMapping("/{name}/delete")
    public ResponseEntity<String> userDelete(@PathVariable("name") String name){
        log.info("delete user controller in");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.userinfo(authentication.getName());
        try {
            log.info("name=={}", name);
            log.info("auth=={}", authentication.getName());

            if (name.equals(user.getNickName())) {
                userService.deleteUser(authentication.getName());
                log.info("회원 삭제 성공: {}", name);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                log.warn("삭제 요청한 사용자와 인증된 사용자가 다릅니다: {} vs {}", name, authentication.getName());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 요청한 사용자와 인증된 사용자가 다릅니다.");
            }
        } catch (Exception e) {
            log.error("회원 삭제 중 오류 발생: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
