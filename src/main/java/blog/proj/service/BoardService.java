package blog.proj.service;

import blog.proj.dto.BoardDto;
import blog.proj.entity.*;
import blog.proj.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    //게시글 조회
        //리스트 조회
    public List<Board> getBoardList(@AuthenticationPrincipal Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return boardRepository.findByBoardList(user.getNickName());
    }
        //단 건 조회
    public Board getBoard(Long id){
        return boardRepository.findByBoard(id);
    }

    //게시글 작성
    @Transactional
    public Board createBoard(BoardDto boardDto,@AuthenticationPrincipal Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Board board= Board.builder()
                .title(boardDto.getTitle())
                .name(user.getNickName())
                .content(boardDto.getContent())
                .user(user)
                .build();
        return boardRepository.save(board); }

    //게시글 삭제
    @Transactional
    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }
    //게시글 수정
    @Transactional
    public void modifyBoard(Long id,BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow();
        board.updateTitle(boardDto.getTitle());
        board.updateContent(boardDto.getContent());
        board.updateCount();
    }

    //폴더 생성
    @Transactional
    public void addFolder(String folderName,@AuthenticationPrincipal Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Folder folder = Folder.builder()
                .user(user)
                .folderName(folderName)
                .category(Category.FOLDER)
                .build();
    }

}
