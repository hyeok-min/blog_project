package blog.proj.repository;

import blog.proj.dto.QuestionDto;

import java.util.List;

public interface QuestionCustomRepository {
    List<QuestionDto> findByQuestionsUser(Long id);
    QuestionDto findByQuestion(Long userId,Long questionId);
    List<QuestionDto> findByQuestionsAdmin();
    QuestionDto findByQuestionAdmin(Long questionId);

}
