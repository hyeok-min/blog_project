package blog.proj.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//파라미터가 없는 기본 생성자를 추가.
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseTimeEntity {
    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String comment;

    //부모댓글인지, 대댓글인지 확인하는 변수 0은 부모, 1은 자식
    private int comment_order;

    private Long commentParentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(Long id,String name,String comment,int comment_order,Board board,User user,Long commentParentId) {
        this.id = id;
        this.name = name;
        this.comment =comment;
        this.comment_order=comment_order;
        this.commentParentId=commentParentId;
        this.board=board;
        this.user=user;
    }

}
