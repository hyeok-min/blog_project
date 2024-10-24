package blog.proj.controller;

import blog.proj.dto.BoardDto;
import blog.proj.dto.FolderDto;
import blog.proj.dto.UserDto;
import blog.proj.entity.Board;
import blog.proj.entity.Folder;
import blog.proj.entity.User;
import blog.proj.service.BoardService;
import blog.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

//게시글, 댓글
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    //특정폴더의 게시글 리스트
    @GetMapping("/{user}/{folder}")
    public  ResponseEntity<List<BoardDto>> getBardList(@PathVariable("user") String user, @PathVariable("folder") String folder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("===boardlist in =={}",authentication.getName());
        log.info("===boardlist user in =={}",user);
        log.info("===boardlist folder in =={}",folder);

//        User loggedInUser = (User) authentication.getPrincipal();
       List<BoardDto> boardlist =  boardService.getBoardList(authentication,folder);
        log.info("boardlist tostring"+boardlist.size());
        for (BoardDto board : boardlist) {
            log.info("Board ID: {}, Title: {}", board.getId(), board.getTitle());
        }
        if (boardlist.isEmpty()) {
            log.info("boardlist empty");
            return ResponseEntity.ok(Collections.emptyList()); // 빈 리스트 반환
        }
        log.info("boardlist out");
        return ResponseEntity.ok(boardlist); // 200 OK와 함께 리스트 반환
    }

    //게시글
    @GetMapping("/{user}/{folder}/{id}")
    public  ResponseEntity<BoardDto> getBoard( @PathVariable("user") String user, @PathVariable("folder") String folder, @PathVariable("id") Long id) {
        BoardDto board = boardService.getBoard(id);
        log.info("board : "+board.getContent(),board.getTitle(),board.getName(),board.getId());
        if (board==null) {
            log.info("board empty");
            return ResponseEntity.notFound().build(); //404 코드 반환
        }
        log.info("board out");
        return ResponseEntity.ok(board); // 200 OK와 함께 리스트 반환
    }

//게시글 작성
@PostMapping("/{users}/{folder}")
    public  ResponseEntity<BoardDto> getBoard( @PathVariable("users") String users, @PathVariable("folder") String folder, @RequestBody BoardDto boardDto) {
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();

        // 게시물 저장 로직
        boardService.createBoard(boardDto,authentication); // 사용자 정보 저장

        // 게시물 생성 성공 시 메시지
        return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
    } catch (Exception e) {
        // 게시물 생성 실패시 메시지
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    //게시글 수정
    @PutMapping("/{user}/{folder}/{id}/update")
    public ResponseEntity<BoardDto> updateBoard(@RequestBody BoardDto boardDto,@PathVariable("user") String user, @PathVariable("folder") String folder, @PathVariable("id") Long id) {
        boardService.modifyBoard(id,boardDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{user}/{folder}/{id}/delete")
    public ResponseEntity<Void> deleteBoard( @PathVariable("user") String user, @PathVariable("folder") String folder, @PathVariable("id") Long id) {
       boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제 성공 시 204 상태 코드 반환
    }



    //폴더생성(추가)
    @PostMapping("/{user}/folderCreate")
    public ResponseEntity<?> createFolder(@PathVariable("user") String user,@RequestBody FolderDto folderDto) {
        log.info(folderDto.getFolderName());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedInUser = (User) authentication.getPrincipal(); // 인증된 사용자 정보
            log.info("====try in");
            userService.authTransaction(user);
            log.info("====try in1");
            boardService.addFolder(loggedInUser,folderDto.getFolderName());
            log.info("====try in2");

            return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>("create failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    //폴더 리스트
    @GetMapping("/{user}/folderList")
    public ResponseEntity<List<FolderDto>> getFolderList(@PathVariable("user") String user) {
        log.info("folderlist in");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = (User) authentication.getPrincipal();

        List<FolderDto> folderList = boardService.folderList(user,loggedInUser.getId());

        if (folderList.isEmpty()) {
            log.info("folderlist empty");
            return ResponseEntity.ok(Collections.emptyList()); // 빈 리스트 반환
        }
        log.info("folderlist out");
        return ResponseEntity.ok(folderList); // 200 OK와 함께 리스트 반환
    }


}
