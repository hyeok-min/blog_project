package blog.proj.controller;

import blog.proj.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//문의사항 controller
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    //문의사항 리스트
    @GetMapping("/{user}")
    public String getQuestionList(@PathVariable String user) {
        return "index.html";
    }

    //문의사항 조회
    @GetMapping("/{user}/{id}")
    public String getQuestion(@PathVariable String user,@PathVariable Long id) {
        return "index.html";
    }

    //문의사항 작성
    @GetMapping("/{user}/insert")
    public String insertQuestion(@PathVariable String user) {
        return "index.html";
    }

    //문의사항 삭제
    @GetMapping("/{user}/delete")
    public String deleteQuestion(@PathVariable String user) {
        return "index.html";
    }

    //문의사항 수정
    @GetMapping("/{user}/update")
    public String updateQuestion(@PathVariable String user) {
        return "index.html";
    }

    //문의사항 답변(관리자 용)
    @GetMapping("/{user}/{id}/answer")
    public String answerQuestion(@PathVariable String user,@PathVariable Long id) {
        return "index.html";
    }

}
