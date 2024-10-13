package blog.proj.dto;

import blog.proj.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class BoardDto {
    private Long id;
    private String name;

    @NotEmpty(message = "제목은 필수 입력 값입니다.")
    @Length(max=20, message = "제목은 20자 이하로 작성해주세요.")
    private String title;

    @NotEmpty(message = "내용은 필수 입력 값입니다.")
    private String content;

    private Category category;

    private String folderName;
}
