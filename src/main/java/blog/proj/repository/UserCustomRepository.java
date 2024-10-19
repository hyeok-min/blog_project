package blog.proj.repository;

import blog.proj.dto.UserDto;
import blog.proj.entity.User;

public interface UserCustomRepository {
    UserDto findByLoginUserEmail(String email);
}
