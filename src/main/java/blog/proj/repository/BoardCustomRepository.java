package blog.proj.repository;

import blog.proj.dto.BoardDto;
import blog.proj.entity.Board;

import java.util.List;

public interface BoardCustomRepository {
    List<Board> findByBoardList(String name);
    Board findByBoard(Long id);
    List<BoardDto> findByFolderList(String name);
}
