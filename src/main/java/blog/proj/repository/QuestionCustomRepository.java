package blog.proj.repository;

import blog.proj.dto.QuestionDto;

import java.util.List;

public interface QuestionCustomRepository {
    List<QuestionDto> findByQuestions(Long id);
    QuestionDto findByQuestion(Long userId,Long questionId);

}
