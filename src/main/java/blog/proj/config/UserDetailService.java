package blog.proj.config;

import blog.proj.entity.User;
import blog.proj.repository.UserCustomRepository;
import blog.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
        private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //사용자 이름(email)로 사용자의 정보를 가져오는 메소드
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            log.info("loaduserby");
            log.info(email);
            UserDetails userDetails = null;
            try {
                userDetails = userRepository.findByEmail(email);
            } catch (Exception e) {
                log.error("Error retrieving user from database: {}", e.getMessage());
            }

            if (userDetails != null) {
                log.info("User found: {}", userDetails.getUsername());
                log.info("User found: {}", userDetails.getPassword());
            } else {
                log.info("User details are null.");
            }

            return userDetails;
    }
    // 비밀번호 검증 메서드
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
