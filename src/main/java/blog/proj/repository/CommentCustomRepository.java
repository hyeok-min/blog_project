package blog.proj.repository;

import blog.proj.dto.CommentDto;

import java.util.List;

public interface CommentCustomRepository {
    List<CommentDto> findByCommentList(Long id);
}
