package blog.proj.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {//Spring Security에서 사용자 정보를 가져오는 Interface

        private final UserRepository userRepository;

        //사용자 이름(email)로 사용자의 정보를 가져오는 메소드
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException((email)));

    }
}
