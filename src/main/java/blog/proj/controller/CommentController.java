package blog.proj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    //댓글 작성
    @GetMapping("/{boardId}")
    public String insertComment(@PathVariable Long boardId) {
        return "index.html";
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
