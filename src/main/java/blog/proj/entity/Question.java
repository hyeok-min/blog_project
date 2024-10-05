package blog.proj.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Question")
public class Question extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Question_id")
    Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    //답변 받을 경우 채워짐
    @Column(nullable = false)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //문의 상태 WAIT, ANSWER
    private QuestionStatus questionStatus;

    @Builder
    public Question(String writer,String title,String content,String answer,QuestionStatus questionStatus,User user){
        this.writer=writer;
        this.title=title;
        this.content=content;
        this.answer=answer;
        this.questionStatus=questionStatus;
        this.user =user;
    }

    //문의 답변
    public void insertAnswer(String answer){
        this.answer=answer;
        this.questionStatus=QuestionStatus.ANSWER;
    }
}
