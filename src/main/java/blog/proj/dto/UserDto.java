package blog.proj.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserDto {

    private long id;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotEmpty(message = "닉네임은 필수 입력 값입니다.")
    @Length(max=10, message = "닉네임은 10자 이하로 작성해주세요.")
    private String nickName;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String passWord;

}
