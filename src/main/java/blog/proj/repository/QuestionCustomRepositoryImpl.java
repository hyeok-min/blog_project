package blog.proj.repository;

import blog.proj.dto.QuestionDto;
import blog.proj.dto.UserDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static blog.proj.entity.QQuestion.question;
import static blog.proj.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QuestionCustomRepositoryImpl  implements QuestionCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public List<QuestionDto> findByQuestionsUser(Long id){
        return jpaQueryFactory
                .select(Projections.fields(QuestionDto.class,question.id,question.content,question.title,question.questionStatus))
                .from(question)
                .join(question.user,user)
                .where(question.user.id.eq(id))
                .orderBy(question.id.desc())
                .fetch();
    }

    public List<QuestionDto> findByQuestionsAdmin(){
        return jpaQueryFactory
                .select(Projections.fields(QuestionDto.class,question.id,question.content,question.title,question.questionStatus,question.writer))
                .from(question)
                .orderBy(question.id.desc())
                .fetch();
    }

    public QuestionDto findByQuestion(Long userId,Long questionId){
        return jpaQueryFactory
                .select(Projections.fields(QuestionDto.class,question.id,question.content,question.title,question.questionStatus,question.answer))
                .from(question)
                .join(question.user,user)
                .where(question.user.id.eq(userId).and(question.id.eq(questionId)) )
                .fetchOne();
    }

    public QuestionDto findByQuestionAdmin(Long questionId){
        return jpaQueryFactory
                .select(Projections.fields(QuestionDto.class,question.id,question.content,question.title,question.questionStatus,question.writer,question.answer))
                .from(question)
                .join(question.user,user)
                .where(question.id.eq(questionId))
                .fetchOne();
    }
}
