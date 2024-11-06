package blog.proj.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board extends BaseTimeEntity{
    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long view;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    //업데이트 횟수(1이상이면 업데이트 되었다는 것)
    private Long boardUpdateCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();


    @Builder
    public Board(String name,String title,String content,Category category,User user,Folder folder){
        this.name=name;
        this.title=title;
        this.content=content;
        this.category=category;
        this.boardUpdateCount=0L;
        this.user = user;
        this.folder = folder;
    }
    public void update(String title, String content ,Long updateCount,Folder folder) {
        this.title = title;
        this.content=content;
        this.boardUpdateCount = updateCount+1;
        this.folder=folder;
    }
}
