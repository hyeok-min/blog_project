package blog.proj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//게시글, 댓글
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    //특정폴더의 게시글 리스트
    @GetMapping("/{user}/{folder}")
    public String getBardList( @PathVariable String user, @PathVariable String folder) {
        return "index.html";
    }

    //게시글
    @GetMapping("/{user}/{folder}/{id}")
    public String getBoard( @PathVariable String user, @PathVariable String folder, @PathVariable Long id) {
        return "index.html";
    }

    //게시글 수정
    @GetMapping("/{user}/{folder}/{id}/update")
    public String updateBoard( @PathVariable String user, @PathVariable String folder, @PathVariable Long id) {
        return "index.html";
    }

    //게시글 삭제
    @GetMapping("/{user}/{folder}/{id}/delete")
    public String deleteBoard( @PathVariable String user, @PathVariable String folder, @PathVariable Long id) {
        return "index.html";
    }

    //폴더생성(추가)
}
