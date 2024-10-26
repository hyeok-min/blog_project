package blog.proj.repository;

import blog.proj.dto.QuestionDto;
import blog.proj.dto.UserDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static blog.proj.entity.QQuestion.question;

@Repository
@RequiredArgsConstructor
public class QuestionCustomRepositoryImpl  implements QuestionCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public List<QuestionDto> findByQuestions(String name){
        return jpaQueryFactory
                .select(Projections.fields(QuestionDto.class,question.content,question.title,question.questionStatus))
                .from(question)
                .where(question.writer.eq(name))
                .fetch();
    }
}
