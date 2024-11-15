package blog.proj.controller;

import blog.proj.dto.QuestionDto;
import blog.proj.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//문의사항 controller
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    //문의사항 리스트
    @GetMapping("/{user}")
    public ResponseEntity<List<QuestionDto>> getQuestionList(@PathVariable("user") String user) {
        log.info("question list in");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<QuestionDto> list = questionService.getQuestionListUser(authentication);
        return ResponseEntity.ok(list);
    }
    //문의사항 작성
    @PostMapping("/{user}/new")
    public ResponseEntity<QuestionDto> createQuestion(@PathVariable("user") String user,@RequestBody QuestionDto questionDto) {
        try{
            log.info("타이틀"+questionDto.getTitle());
            log.info("컨텐츠"+questionDto.getContent());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        questionService.createQuestion(questionDto,authentication);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
    } catch (Exception e) {
        // 게시물 생성 실패시 메시지
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //문의사항 단건조회
    @GetMapping("/{user}/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable("user") String user,@PathVariable("id") Long id) {
        log.info("getQuestion start");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("getQuestion in");
        QuestionDto questionDto = questionService.getQuestion(authentication,id);
        log.info("getQuestion : "+questionDto.getContent(),questionDto.getTitle(),questionDto.getAnswer(),questionDto.getId(),questionDto.getQuestionStatus());
        if (questionDto==null) {
            log.info("getQuestion empty");
            return ResponseEntity.notFound().build(); //404 코드 반환
        }
        log.info("getQuestion out");
        return ResponseEntity.ok(questionDto); // 200 OK와 함께 리스트 반환
    }
    //관리자용 문의사항 단건조회
    @GetMapping("/admin/{id}")
    public ResponseEntity<QuestionDto> getQuestionadmin(@PathVariable("id") Long id) {
        log.info("getQuestion start");

        log.info("getQuestion in");
        QuestionDto questionDto = questionService.getQuestionAdmin(id);
        log.info("getQuestion : "+questionDto.getContent(),questionDto.getTitle(),questionDto.getAnswer(),questionDto.getId(),questionDto.getQuestionStatus());
        if (questionDto==null) {
            log.info("getQuestion empty");
            return ResponseEntity.notFound().build(); //404 코드 반환
        }
        log.info("getQuestion out");
        return ResponseEntity.ok(questionDto); // 200 OK와 함께 리스트 반환
    }

    //문의사항 삭제
    @DeleteMapping("/{user}/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("user") String user,@PathVariable("id") Long id) {
        log.info("Question delete in");
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //문의사항 리스트(관리자용)
    @GetMapping("/admin")
    public ResponseEntity<List<QuestionDto>> getQuestionListofAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    List<QuestionDto> list = questionService.getQuestionListAdmin(authentication);
        return ResponseEntity.ok(list);
    }

//    //문의사항 답변(관리자 용)
    @PostMapping("/admin/answer/{id}")
    public ResponseEntity<QuestionDto> answerQuestion(@PathVariable("id") Long id,@RequestBody QuestionDto questionDto) {
        log.info("answer post in ");
        try{
            log.info("answer post in try1 ");
            questionService.answerQuestion(id,questionDto);
            log.info("answer post in try2");
            return new ResponseEntity<>(HttpStatus.OK); // 201 Created
        } catch (Exception e) {
            // 게시물 생성 실패시 메시지
            log.info("answer post in catch");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
