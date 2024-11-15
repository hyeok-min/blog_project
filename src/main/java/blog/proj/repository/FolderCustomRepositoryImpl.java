package blog.proj.repository;

import blog.proj.dto.BoardDto;
import blog.proj.dto.FolderDto;
import blog.proj.entity.Folder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static blog.proj.entity.QBoard.board;
import static blog.proj.entity.QFolder.folder;
import static blog.proj.entity.QUser.user;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FolderCustomRepositoryImpl  implements FolderCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public Folder findbyFolder(String name,Long id){
        //폴더
        log.info("folder repository name = {}  id = {}",name,id);
        return jpaQueryFactory
                .select(Projections.fields(Folder.class,folder.id,folder.folderName,folder.category,folder.user))
                .from(folder)
                .join(folder.user,user)
                .where(folder.folderName.eq(name).and(user.id.eq(id)))
                .fetchOne();
    }
}
