package blog.proj.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class UserDto {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotEmpty(message = "닉네임은 필수 입력 값입니다.")
    @Length(max=10, message = "닉네임은 10자 이하로 작성해주세요.")
    private String nickName;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String passWord;

    private Long id;

    private String role;
    @QueryProjection
    public UserDto(Long id, String email, String nickName, String passWord) {
        this.email = email;
        this.nickName = nickName;
        this.passWord = passWord;
    }
}
