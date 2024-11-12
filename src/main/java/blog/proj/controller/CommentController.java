package blog.proj.controller;

import blog.proj.dto.BoardDto;
import blog.proj.dto.CommentDto;
import blog.proj.entity.Comment;
import blog.proj.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    // 댓글 리스트
    @GetMapping("/{id}")
    public ResponseEntity<List<CommentDto>> getCommentList(@PathVariable("id") Long id) {
        log.info("commentlist start");
        List<CommentDto> commentlist =  commentService.getCommentList(id);
        log.info("commentlist tostring"+commentlist.size());
        for (CommentDto commentDto : commentlist) {
            log.info("comment ID: {}, Comment: {}, name: {}", commentDto.getId(), commentDto.getComment(),commentDto.getName());
        }
        if (commentlist.isEmpty()) {
            log.info("commentlist empty");
            return ResponseEntity.ok(Collections.emptyList()); // 빈 리스트 반환
        }
        log.info("commentlist out");
        return ResponseEntity.ok(commentlist); // 200 OK와 함께 리스트 반환
    }

    //댓글 작성
    @PostMapping("/{user}/{folderName}/{id}")
    public ResponseEntity<CommentDto> insertComment(@PathVariable("user") String user,@PathVariable("folderName") String folderName,@PathVariable("id") Long id, @RequestBody CommentDto commentDto){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            commentService.createComment(id,commentDto,authentication);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
    } catch (Exception e) {
        // 게시물 생성 실패시 메시지
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    //대댓글 작성
    @PostMapping("/{user}/{folderName}/{id}/reply")
    public ResponseEntity<CommentDto> insertReplyComment(@PathVariable("user") String user,@PathVariable("folderName") String folderName,@PathVariable("id") Long id, @RequestBody CommentDto commentDto){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            commentService.createReplyComment(id,commentDto,authentication);
            return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            // 게시물 생성 실패시 메시지
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 삭제
    @GetMapping("/{boardId}/delete/{commentId}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        return "index.html";
    }

    //댓글 수정
    @GetMapping("/{boardId}/update/{commentId}")
    public String updateComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        return "index.html";
    }
}
