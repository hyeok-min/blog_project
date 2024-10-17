package blog.proj.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig  implements WebMvcConfigurer {
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailureHandler customLoginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests( auth -> auth
                                .anyRequest().permitAll() // 모든 요청에 대해 접근 허용
                )
//                .formLogin(formLogin -> formLogin //폼 기반 로그인 설정
//                                .usernameParameter("email") // id값 email 사용
//                                .passwordParameter("passWord")   // pwd값 pwd 사용
//                                .loginProcessingUrl("/api/login")
//                                .defaultSuccessUrl("http://localhost:3000/", true)
//                                .permitAll()
//                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // 로그아웃 요청 URL
                        .logoutSuccessUrl("http://localhost:3000/") // 로그아웃 성공 후 리디렉션할 URL
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
    //인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailService userService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}