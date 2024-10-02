package blog.proj.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 사용자 계정 이름을 가져오는 부분
        String username = authentication.getName();

        // 로그인 성공 후 이동할 URL 설정
        String redirectUrl = "/api/" + username + "/home";

        // 해당 URL로 리다이렉트
        response.sendRedirect(redirectUrl);
    }
}
