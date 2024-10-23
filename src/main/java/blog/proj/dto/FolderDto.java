package blog.proj.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderDto {
    private Long id;
    private String folderName;

//    @QueryProjection
//    public FolderDto(Long id, String folderName) {
//        this.folderName=folderName;
//        this.id=id;
//    }
@Override
public String toString() {
    return "FolderDto{id=" + id + ", folderName='" + folderName + "'}";
}
}
