package blog.proj.service;

import blog.proj.dto.UserDto;
import blog.proj.entity.User;
import blog.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원정보를 가져오는 로직
    public UserDto userinfo(String email) {
        UserDto user = userRepository.findByLoginUserEmail(email);
        return user;
    }
    //닉네임으로 정보 가져오는 로직
    public UserDto userinfoNickName(String nick) {
        UserDto user = userRepository.findByLoginUserNickName(nick);
        return user;
    }
    //회원가입
    @Transactional
    public User createUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .nickName(userDto.getNickName())
                .passWord(bCryptPasswordEncoder.encode(userDto.getPassWord()))
                .role("user")
                .build();
        return userRepository.save(user);
    }

    //로그인한 사용자와 블로거 주인 비교(글작성, 글 삭제 등에 이용됨)
    public void authTransaction(String name) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }

        String currentUserName = authentication.getName();
        log.info("authTransaction = "+currentUserName);
        UserDto currentUser = userinfo(currentUserName);
        log.info("authTransaction2 = "+currentUser);
        if (!name.equals(currentUser.getNickName())) {
            throw new AccessDeniedException("You do not have permission to perform this action");
        }
    }

    //계정 삭제
    @Transactional
    public void deleteUser(String email){
        log.info(("delete service come"));
        userRepository.deleteByEmail(email);
    }

    //계정 업데이트
    @Transactional
    public void updateUser(UserDto userDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        User userUpdate = userRepository.findByEmail(user.getEmail());
        log.info(userDto.getNickName());
        log.info(userDto.getEmail());
        log.info(userDto.getPassWord());
        userUpdate.update(bCryptPasswordEncoder.encode(userDto.getPassWord()),userDto.getEmail(),userDto.getNickName());

        userRepository.save(userUpdate);

    }



}
