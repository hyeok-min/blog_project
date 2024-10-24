package blog.proj.repository;

import static blog.proj.entity.QBoard.board;
import static blog.proj.entity.QUser.user;
import static blog.proj.entity.QFolder.folder;
import blog.proj.dto.BoardDto;
import blog.proj.dto.FolderDto;
import blog.proj.dto.UserDto;
import blog.proj.entity.Board;
import blog.proj.entity.Category;
import blog.proj.entity.Folder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public BoardDto findByBoard(Long id){
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,folder.folderName,board.boardUpdateCount))
                .from(board)
                .join(board.folder,folder)
                .where(board.id.eq(id))
                .fetchOne();
    }

    public List<BoardDto> findByBoardList(String name,Long id){
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,folder.folderName))
                .from(board)
                .join(board.folder,folder)
                .where(board.name.eq(name).and(board.folder.id.eq(id)))
                .fetch();
    }
    public List<FolderDto> findByFolderList(String name, Long id){
        return jpaQueryFactory
                .select(Projections.fields(FolderDto.class,folder.id,folder.folderName))
                .from(folder)
                .join(folder.user,user)
                .where(folder.category.eq(Category.valueOf("FOLDER")).and(user.id.eq(id)))
                .fetch();
    }


}
