package blog.proj.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("custom success handler");
        // 사용자 계정 이름을 가져오는 부분
        String username = authentication.getName();

        // 로그인 성공 후 이동할 URL 설정
//        String redirectUrl = "http://localhost:3000/"+username;
        String redirectUrl = "http://localhost:3000";
        log.info("======redirecturl==: "+redirectUrl);

        // 해당 URL로 리다이렉트
        response.sendRedirect(redirectUrl);
    }
}
