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

    //검색

    public List<BoardDto> search(String query) {
        // 제목 또는 내용에서 검색어가 포함된 게시글을 찾기
        return boardRepository.findBySearch(query);
    }

        //리스트 조회
    public List<BoardDto> getBoardList(UserDto userDto,String folder){
        log.info("board getboardlist service in ");
        log.info("id={}  nickname={}  email={}  folder={}",userDto.getId(),userDto.getNickName(),userDto.getEmail(),folder);
        Folder folder1 = folderRepository.findbyFolder(folder,userDto.getId());
        log.info("folder folderid =={}  foldername =={}  ",folder1.getId(),folder1.getFolderName());

        return boardRepository.findByBoardList(userDto.getNickName(),folder1.getId());
    }
    //특정블로거 게시물 top9
    public List<BoardDto> getBoardListUser(UserDto userDto){
        return boardRepository.findByBoardListTop9(userDto.getNickName());
    }
    //전체블로거 게시물 top9
    public List<BoardDto> getBoardListAll(){
        return boardRepository.findByBoardListTop9All();
    }
        //단 건 조회
    @Transactional
    public BoardDto getBoard(Long id){
        return boardRepository.findByBoard(id);
    }

    //게시글 작성
    @Transactional
    public Board createBoard(BoardDto boardDto,@AuthenticationPrincipal Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Folder folder = folderRepository.findbyFolder(boardDto.getFolderName(),user.getId());
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
    public void modifyBoard(Long id,BoardDto boardDto,@AuthenticationPrincipal Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Board board = boardRepository.findById(id).orElseThrow();
        Folder folder = folderRepository.findbyFolder(boardDto.getFolderName(),user.getId());
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
    //폴더 삭제
    @Transactional
    public void deleteFolder(String folder){
        folderRepository.deleteByFolderName(folder);
    }
}
