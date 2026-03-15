package trod.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trod.lab.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecordId(Long recordId);
}
