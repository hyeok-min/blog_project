package blog.proj.repository;

import blog.proj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository  extends JpaRepository<User,Long>,QuestionCustomRepository{
}
