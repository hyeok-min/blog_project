package blog.proj.repository;

import blog.proj.entity.Comment;
import blog.proj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long>,UserCustomRepository {
    void deleteByEmail(String email);
    User findByEmail(String email);
}
