package blog.proj.repository;

import blog.proj.dto.QuestionDto;

import java.util.List;

public interface QuestionCustomRepository {
    List<QuestionDto> findByQuestions(String name);
}
