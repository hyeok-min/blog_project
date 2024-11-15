package blog.proj.repository;

import blog.proj.entity.Folder;

public interface FolderCustomRepository {
    Folder findbyFolder(String name,Long id);
}
