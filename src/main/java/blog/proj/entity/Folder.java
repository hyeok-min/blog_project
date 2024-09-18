package blog.proj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "folder")
public class Folder {

    @Id
    private int id;

    @Column(nullable = false)
    private String folder_owner;

    @Column(nullable = false)
    private String folder_name;

    @Enumerated(EnumType.STRING)
    private Category category;

}
