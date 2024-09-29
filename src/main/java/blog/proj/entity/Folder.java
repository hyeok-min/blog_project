package blog.proj.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "folder")
public class Folder{

    @Id
    @Column(name="folder_id")
    private int id;

    @Column(nullable = false)
    private String folder_name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Folder(int id, String folder_name,Category category,User user) {
        this.id = id;
        this.folder_name = folder_name;
        this.category = category;
        this.user=user;
    }


}
