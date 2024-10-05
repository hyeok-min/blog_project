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
    private Long id;

    @Column(nullable = false)
    private String folderName;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Folder(String folderName,Category category) {
        this.folderName = folderName;
        this.category = category;
    }


}
