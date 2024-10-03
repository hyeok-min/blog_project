package blog.proj.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailService userService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    //Spring Security 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth //인증, 인가 설정
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/home"),
                                new AntPathRequestMatcher("/api/board/**"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll() //누구나 접근 가능
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin //폼 기반 로그인 설정
                        .usernameParameter("email") // id값 email 사용
                        .passwordParameter("pwd")   // pwd값 pwd 사용
//                        .loginPage("/api/login") //로그인 페이지 경로
                        .successHandler(customLoginSuccessHandler) //로그인 완료 후 이동할 페이지
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") //로그아웃이 완료되었을 때 이동할 경로
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }



    //인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception{
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); //사용자 정보 서비스 저장
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
