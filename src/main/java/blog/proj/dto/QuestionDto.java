package blog.proj.dto;

import blog.proj.entity.QuestionStatus;
import blog.proj.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class QuestionDto {
    private Long id;
    private String writer;

    @NotEmpty(message = "제목은 필수 입력 값입니다.")
    @Length(max=20, message = "제목은 20자 이하로 작성해주세요.")
    private String title;

    @NotEmpty(message = "내용은 필수 입력 값입니다.")
    private String content;

    private String answer;
    private QuestionStatus questionStatus;
}
