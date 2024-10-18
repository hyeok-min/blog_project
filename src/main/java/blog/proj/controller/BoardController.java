package blog.proj.controller;

import blog.proj.dto.BoardDto;
import blog.proj.dto.UserDto;
import blog.proj.entity.Folder;
import blog.proj.service.BoardService;
import blog.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//게시글, 댓글
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    /*
    //특정폴더의 게시글 리스트
    @GetMapping("/{user}/{folder}")
    public List<BoardDto> getBardList(@PathVariable String user, @PathVariable String folder) {
//        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    //게시글
    @GetMapping("/{user}/{folder}/{id}")
    public  ResponseEntity<BoardDto> getBoard( @PathVariable String user, @PathVariable String folder, @PathVariable Long id) {
//        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    //게시글 수정
    @PutMapping("/{user}/{folder}/{id}/update")
    public ResponseEntity<BoardDto> updateBoard( @PathVariable String user, @PathVariable String folder, @PathVariable Long id) {
//        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{user}/{folder}/{id}/delete")
    public ResponseEntity<Void> deleteBoard( @PathVariable String user, @PathVariable String folder, @PathVariable Long id) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제 성공 시 204 상태 코드 반환
    }

     */

    //폴더생성(추가)
    @PostMapping("/{user}/folderCreate")
    public ResponseEntity<?> createFolder(@PathVariable String user,@RequestBody BoardDto folderDto) {

        try {
            userService.authTransaction(user);
            boardService.addFolder(folderDto.getFolderName());

            return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>("create failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    //폴더 리스트
    @GetMapping("/{user}/folderList")
    public List<BoardDto> getFolderList(@PathVariable String user) {
        return boardService.folderList(user);
    }


}
