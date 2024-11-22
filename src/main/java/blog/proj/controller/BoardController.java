package blog.proj.controller;

import blog.proj.dto.BoardDto;
import blog.proj.dto.FolderDto;
import blog.proj.dto.UserDto;
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

    //검색
    @GetMapping("/search")
    public List<BoardDto> getBoardSearch(@RequestParam("query") String query){
        log.info("===search in =={}",query);
        return boardService.search(query);
    }

    //특정폴더의 게시글 리스트
    @GetMapping("/{user}/{folder}")
    public  ResponseEntity<List<BoardDto>> getBoardList(@PathVariable("user") String user, @PathVariable("folder") String folder) {
        log.info("boardlist user = "+user+"   folder = "+folder);
        UserDto userDto = userService.userinfoNickName(user);
        log.info("boardlist dto user = "+userDto.getNickName()+"   email = "+userDto.getEmail()+"   id = "+userDto.getId());
        List<BoardDto> boardlist =  boardService.getBoardList(userDto,folder);
        log.info("boardlist tostring"+boardlist.size());
        for (BoardDto board : boardlist) {
            log.info("Board ID: {}, Title: {}", board.getId(), board.getTitle());
        }
        if (boardlist.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(boardlist);
    }
    //특정블로거 게시글 리스트 top9
    @GetMapping("/{user}")
    public  ResponseEntity<List<BoardDto>> getBoardListUser(@PathVariable("user") String user) {
        UserDto userDto = userService.userinfoNickName(user);
        List<BoardDto> boardlist =  boardService.getBoardListUser(userDto);
        log.info("boardlist tostring"+boardlist.size());
        for (BoardDto board : boardlist) {
            log.info("Board ID: {}, Title: {}", board.getId(), board.getTitle());
        }
        if (boardlist.isEmpty()) {
            log.info("boardlist empty");
            return ResponseEntity.ok(Collections.emptyList());
        }
        log.info("boardlist out");
        return ResponseEntity.ok(boardlist);
    }

    //모든 블로거 게시글 리스트 top9
    @GetMapping("/all")
    public  ResponseEntity<List<BoardDto>> getBardListAll() {
        List<BoardDto> boardlist =  boardService.getBoardListAll();
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

    //게시글 상세
    @GetMapping("/{user}/{folder}/{id}")
    public  ResponseEntity<BoardDto> getBoard( @PathVariable("user") String user, @PathVariable("folder") String folder, @PathVariable("id") Long id) {
        BoardDto board = boardService.getBoard(id);
        log.info("board : "+board.getContent(),board.getTitle(),board.getName(),board.getId());
        if (board==null) {
            log.info("board empty");
            return ResponseEntity.notFound().build();
        }
        log.info("board out");
        return ResponseEntity.ok(board);
    }

//게시글 작성
@PostMapping("/{users}/{folder}")
    public  ResponseEntity<BoardDto> createBoard( @PathVariable("users") String users, @PathVariable("folder") String folder, @RequestBody BoardDto boardDto) {
    try {
        log.info("board create in");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("board create service in");
        // 게시물 저장 로직
        boardService.createBoard(boardDto,authentication);

        return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
        // 게시물 생성 실패시 메시지
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    //게시글 수정
    @PutMapping("/{user}/{folder}/{id}/update")
    public ResponseEntity<BoardDto> updateBoard(@RequestBody BoardDto boardDto,@PathVariable("user") String user, @PathVariable("folder") String folder,
                                                @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("modify =={}",authentication.getPrincipal());
        boardService.modifyBoard(id,boardDto,authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{user}/{folder}/{id}/delete")
    public ResponseEntity<Void> deleteBoard( @PathVariable("user") String user, @PathVariable("folder") String folder, @PathVariable("id") Long id) {
       boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("create failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    //폴더 리스트
    @GetMapping("/{user}/folderList")
    public ResponseEntity<List<FolderDto>> getFolderList(@PathVariable("user") String user) {

        log.info("folderlist in");
        UserDto userDto = userService.userinfoNickName(user);
        List<FolderDto> folderList = boardService.folderList(user,userDto.getId());

        if (folderList.isEmpty()) {
            log.info("folderlist empty");
            return ResponseEntity.ok(Collections.emptyList());
        }
        log.info("folderlist out");
        return ResponseEntity.ok(folderList);
    }

    //폴더 삭제
    @DeleteMapping("/{user}/{folder}/delete")
    public ResponseEntity<Void> deleteFolder( @PathVariable("user") String user, @PathVariable("folder") String folder) {
        boardService.deleteFolder(folder);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
