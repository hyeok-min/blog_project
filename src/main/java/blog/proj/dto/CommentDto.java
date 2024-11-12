package blog.proj.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentDto {
    private long id;
    private String name;

    @NotEmpty(message = "댓글은 필수 입력 값입니다.")
    @Length(max=20, message = "댓글은 20자 이하로 작성해주세요.")
    private String comment;

    private int comment_order;

    private Long commentParentId;
}
