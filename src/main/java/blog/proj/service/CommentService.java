package blog.proj.service;

import blog.proj.dto.BoardDto;
import blog.proj.dto.CommentDto;
import blog.proj.entity.*;
import blog.proj.repository.BoardRepository;
import blog.proj.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    //부모 댓글달기
    @Transactional
    public Comment createComment(Long id,CommentDto commentDto, @AuthenticationPrincipal Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Board board = boardRepository.findById(id).orElseThrow();
        Comment comment= Comment.builder()
                .comment_order(0)
                .name(user.getNickName())
                .comment(commentDto.getComment())
                .user(user)
                .board(board)
                .build();
        return commentRepository.save(comment); }

    //해당 게시물 댓글 리스트
    public List<CommentDto> getCommentList(Long id){
        List<CommentDto> getComment = commentRepository.findByCommentList(id);
        return getComment;
    }
}

