package trod.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trod.lab.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameLike(String username);
    Optional<User> findByEmail(String email);
}
