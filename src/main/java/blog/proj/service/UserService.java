package blog.proj.service;

import blog.proj.dto.UserDto;
import blog.proj.entity.User;
import blog.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입
    @Transactional
    public User createUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .nickName(userDto.getNickName())
                .passWord(bCryptPasswordEncoder.encode(userDto.getPassWord()))
                .build();
        return userRepository.save(user);
    }

    //로그인시 계정확인 로직
    public void findUserEmail(UserDto userDto){
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("해당 이메일로 가입한 사용자가 없습니다"));

        if(!bCryptPasswordEncoder.matches(userDto.getPassWord(), user.getPassword())){
            throw new BadCredentialsException("비밀번호가 틀립니다.");
        }
    }

    //계정 삭제
    @Transactional
    public void deleteUser(String email){
        userRepository.deleteByEmail(email);
    }

    //계정 업데이트
    @Transactional
    public void updateUser(UserDto userDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();

        User userUpdate = userRepository.findByEmail(user.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("해당 유저가 없습니다."));

        userUpdate.update(bCryptPasswordEncoder.encode(userDto.getPassWord()),userDto.getEmail(),userDto.getNickName());

        userRepository.save(userUpdate);

    }



}
