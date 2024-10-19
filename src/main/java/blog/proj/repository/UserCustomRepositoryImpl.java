package blog.proj.repository;

import blog.proj.dto.QUserDto;
import blog.proj.dto.UserDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static blog.proj.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public UserDto findByLoginUserEmail(String email){
        return jpaQueryFactory
                .select(Projections.fields(UserDto.class,user.email,user.nickName,user.passWord))
                .from(user)
                .where(user.email.eq(email))
                .fetchOne();
    }
}
