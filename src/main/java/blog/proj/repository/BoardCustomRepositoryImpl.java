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

    //게시물 조회
    public BoardDto findByBoard(Long id){
        //조회수 1 올리기
        long updateView = jpaQueryFactory
                .update(board)
                .set(board.view, board.view.add(1))  // 조회수 증가
                .where(board.id.eq(id))
                .execute();

        //게시물 반환
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,folder.folderName,board.boardUpdateCount,board.view))
                .from(board)
                .join(board.folder,folder)
                .where(board.id.eq(id))
                .fetchOne();
    }

    //특정 블로거의 메인 게시물 리스트(조회수 top 9)
    public List<BoardDto> findByBoardListTop9(String name){
        //게시물 반환
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,board.view))
                .from(board)
                .where(board.name.eq(name))
                .orderBy(board.view.desc()) // board.view를 내림차순으로 정렬
                .limit(9) // 상위 9개만 가져오기
                .fetch();
    }

    //블로거 전체의 메인 게시물 리스트(조회수 top 9)
    public List<BoardDto> findByBoardListTop9All(){
        //게시물 반환
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,board.view))
                .from(board)
                .orderBy(board.view.desc()) // board.view를 내림차순으로 정렬
                .limit(9) // 상위 9개만 가져오기
                .fetch();
    }

    //게시물 리스트
    public List<BoardDto> findByBoardList(String name,Long id){
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,folder.folderName,board.view))
                .from(board)
                .join(board.folder,folder)
                .where(board.folder.id.eq(id))
                .fetch();
    }

    //폴더 리스트
    public List<FolderDto> findByFolderList(String name, Long id){
        return jpaQueryFactory
                .select(Projections.fields(FolderDto.class,folder.id,folder.folderName))
                .from(folder)
                .join(folder.user,user)
                .where(folder.category.eq(Category.valueOf("FOLDER")).and(user.id.eq(id)))
                .fetch();
    }

    //검색
    public List<BoardDto> findBySearch(String query){
        return jpaQueryFactory
                .select(Projections.fields(BoardDto.class,board.id,board.name,board.title,board.content,folder.folderName))
                .from(board)
                .join(board.folder,folder)
                .where(board.title.containsIgnoreCase(query) // 대소문자 구분 없이 제목 검색
                        .or(board.content.containsIgnoreCase(query)))
                .fetch();
    }

}
