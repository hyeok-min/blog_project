package blog.proj.service;

import blog.proj.config.UserDetailService;
import blog.proj.dto.UserDto;
import blog.proj.entity.User;
import blog.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class HomeService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;
    //홈


    //로그인
    public UserDetails loginUser(String email,String password){
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        // 사용자 인증 처리
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());

        // 인증 관리자에게 토큰을 전달해 인증 처리
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 인증이 성공하면 SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userDetails;
    }



}
