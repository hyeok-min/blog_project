package blog.proj.repository;

import blog.proj.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<Comment,Long> {
}
