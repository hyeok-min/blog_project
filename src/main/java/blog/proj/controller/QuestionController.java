package blog.proj.controller;

import blog.proj.dto.QuestionDto;
import blog.proj.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//문의사항 controller
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;
/*
    //문의사항 리스트
    @GetMapping("/{user}")
    public ResponseEntity<List<QuestionDto>> getQuestionList(@PathVariable String user) {
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    //문의사항 리스트(관리자용)
    @GetMapping("/{user}/admin")
    public ResponseEntity<List<QuestionDto>> getQuestionListofAdmin(@PathVariable String user) {
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    //문의사항 단건조회
    @GetMapping("/{user}/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable String user,@PathVariable Long id) {
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    //문의사항 작성
    @PostMapping("/{user}/insert")
    public ResponseEntity<QuestionDto> insertQuestion(@PathVariable String user) {
        return "index.html";
    }

    //문의사항 삭제
    @DeleteMapping("/{user}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String user) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //문의사항 수정
    @PutMapping("/{user}/update")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable String user) {
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    //문의사항 답변(관리자 용)
    @PostMapping("/{user}/{id}/answer")
    public ResponseEntity<QuestionDto> answerQuestion(@PathVariable String user,@PathVariable Long id) {
        return "index.html";
    }

 */

}
