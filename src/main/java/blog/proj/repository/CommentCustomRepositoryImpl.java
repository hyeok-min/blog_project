package blog.proj.repository;

import blog.proj.dto.BoardDto;
import blog.proj.dto.CommentDto;
import blog.proj.dto.FolderDto;
import blog.proj.entity.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static blog.proj.entity.QBoard.board;
import static blog.proj.entity.QComment.comment1;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

//    public BoardDto findByBoard(Long id){
//        return jpaQueryFactory
//                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,folder.folderName,board.boardUpdateCount))
//                .from(board)
//                .join(board.folder,folder)
//                .where(board.id.eq(id))
//                .fetchOne();
//    }
//
    public List<CommentDto> findByCommentList( Long id){
        return jpaQueryFactory
                .select(Projections.fields(CommentDto.class,comment1.comment,comment1.comment_order,comment1.name,comment1.id,comment1.commentParentId))
                .from(comment1)
                .join(comment1.board,board)
                .where(board.id.eq(id))
                .fetch();
    }


}
