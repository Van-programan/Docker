package trod.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trod.lab.entity.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByTitleLike(String title);
    List<Record> findByUserId(Long userId);
}
