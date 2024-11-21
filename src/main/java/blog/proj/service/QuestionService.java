package blog.proj.service;

import blog.proj.dto.QuestionDto;
import blog.proj.entity.Question;
import blog.proj.entity.QuestionStatus;
import blog.proj.entity.User;
import blog.proj.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository questionRepository;

    //유저 본인 문의 리스트
    public List<QuestionDto> getQuestionListUser(@AuthenticationPrincipal Authentication authentication){
        log.info("question list service in");
        User user = (User) authentication.getPrincipal();
        log.info("user nickname =={}",user.getNickName());
        log.info("user id =={}",user.getId());
        return questionRepository.findByQuestionsUser(user.getId());
    }
    //관리자용 유저들 문의 리스트
    public List<QuestionDto> getQuestionListAdmin(@AuthenticationPrincipal Authentication authentication){
        log.info("question list service in");
        User user = (User) authentication.getPrincipal();
        log.info("user nickname =={}",user.getNickName());
        log.info("user id =={}",user.getId());
        log.info("user id =={}",user.getRole());

        return questionRepository.findByQuestionsAdmin();
    }

    //문의 작성
    @Transactional
    public Question createQuestion(QuestionDto questionDto, @AuthenticationPrincipal Authentication authentication) {
        log.info("createquestion service in");
        User user = (User) authentication.getPrincipal();
        Question question = Question.builder()
                .writer(user.getNickName())
                .user(user)
                .questionStatus(QuestionStatus.WAIT)
                .title(questionDto.getTitle())
                .answer("")
                .content(questionDto.getContent())
                .build();
        return questionRepository.save(question);
    }

    //답변 로직
    @Transactional
    public Question answerQuestion(Long id,QuestionDto questionDto) {
        log.info("answerquestion answer in");
        Question question =questionRepository.findById(id).orElseThrow();
        question.insertAnswer(questionDto.getAnswer());
        return questionRepository.save(question);
    }

    //문의 상세 보기
    public QuestionDto getQuestion(@AuthenticationPrincipal Authentication authentication,Long id){
        log.info("question list service in");
        User user = (User) authentication.getPrincipal();
        log.info("user nickname =={}",user.getNickName());
        log.info("user id =={}",user.getId());
        return questionRepository.findByQuestion(user.getId(),id);
    }

    //관리자용 문의 상세 보기
    public QuestionDto getQuestionAdmin(Long id){
        log.info("question ADMIN list service in");
        return questionRepository.findByQuestionAdmin(id);
    }

    //문의 삭제
    @Transactional
    public void deleteQuestion(Long id){
        questionRepository.deleteById(id);
    }
}
