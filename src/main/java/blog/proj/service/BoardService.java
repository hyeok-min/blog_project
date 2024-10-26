package blog.proj.service;

import blog.proj.dto.BoardDto;
import blog.proj.dto.FolderDto;
import blog.proj.dto.UserDto;
import blog.proj.entity.*;
import blog.proj.repository.BoardRepository;
import blog.proj.repository.FolderRepository;
import blog.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;
    private final UserService userService;
    private final FolderRepository folderRepository;
    //게시글 조회
        //리스트 조회
    public List<BoardDto> getBoardList(@AuthenticationPrincipal Authentication authentication,String folder){
        User user = (User) authentication.getPrincipal();
        Folder folder1 = folderRepository.findByFolderName(folder);
        log.info("user nickname =={}",user.getNickName());
        log.info("user folder =={}",folder1);
        return boardRepository.findByBoardList(user.getNickName(),folder1.getId());
    }
        //단 건 조회
    public BoardDto getBoard(Long id){
        return boardRepository.findByBoard(id);
    }

    //게시글 작성
    @Transactional
    public Board createBoard(BoardDto boardDto,@AuthenticationPrincipal Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Folder folder = folderRepository.findByFolderName(boardDto.getFolderName());
        Board board= Board.builder()
                .title(boardDto.getTitle())
                .name(user.getNickName())
                .content(boardDto.getContent())
                .user(user)
                .category(Category.BOARD)
                .folder(folder)
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
        Folder folder = folderRepository.findByFolderName(boardDto.getFolderName());
        board.update(boardDto.getTitle(),boardDto.getContent(),boardDto.getBoardUpdateCount(),folder);
    }

    //폴더 생성
    @Transactional
    public void addFolder(User user,String folderName){
        log.info("addfolder in");
        Folder folder = Folder.builder()
                .user(user)
                .folderName(folderName)
                .category(Category.FOLDER)
                .build();
        log.info("addfolder build out");

        try {
            folderRepository.save(folder);
        } catch (Exception e) {
            log.error("Error saving folder: " + e.getMessage(), e);
            throw e; // 예외를 다시 던져서 상위 메서드에서 처리하게 함
        }
        log.info("addfolder repository save");

    }

    //폴더 리스트
    public List<FolderDto> folderList(String name, Long id){
        List<FolderDto> folders = boardRepository.findByFolderList(name,id);
        log.info("Folders: {}", folders);
       return folders;
    }

}
