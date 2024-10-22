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
@Table(name = "folder")
public class Folder{

    @Id
    @Column(name="folder_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String folderName;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "folder",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board> board = new ArrayList<>();

    @Builder
    public Folder(User user,String folderName,Category category) {
        this.user=user;
        this.folderName = folderName;
        this.category = category;
    }


}
