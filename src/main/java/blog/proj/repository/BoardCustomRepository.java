package blog.proj.repository;

import blog.proj.dto.BoardDto;
import blog.proj.dto.FolderDto;
import blog.proj.entity.Board;
import blog.proj.entity.Folder;

import java.util.List;

public interface BoardCustomRepository {
    List<BoardDto> findByBoardList(String name,Long id);
    Board findByBoard(Long id);
    List<FolderDto> findByFolderList(String name, Long id);
}
