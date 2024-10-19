package blog.proj.repository;

import static blog.proj.entity.QBoard.board;
import static blog.proj.entity.QUser.user;

import blog.proj.dto.BoardDto;
import blog.proj.entity.Board;
import blog.proj.entity.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public Board findByBoard(Long id){
        return jpaQueryFactory
                .selectFrom(board)
                .where(board.id.eq(id))
                .fetchOne();
    }

    public List<Board> findByBoardList(String name){
        return jpaQueryFactory
                .selectFrom(board)
                .where(board.name.eq(name))
                .fetch();
    }
    public List<BoardDto> findByFolderList(String name){
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.folder))
                .from(board)
                .join(board.user,user)
                .where(board.category.eq(Category.valueOf("Folder")).and(user.nickName.eq(name)))
                .fetch();
    }


}
