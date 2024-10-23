package blog.proj.repository;

import blog.proj.entity.Board;
import blog.proj.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository  extends JpaRepository<Folder,Long> {
    public Folder findByFolderName(String folderName);
}
