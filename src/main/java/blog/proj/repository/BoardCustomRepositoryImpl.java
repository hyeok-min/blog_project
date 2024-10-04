package blog.proj.repository;

import static blog.proj.entity.QBoard.board;

import blog.proj.dto.BoardDto;
import blog.proj.entity.Board;
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


}
