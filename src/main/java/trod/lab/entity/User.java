package trod.lab.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "register_at", nullable = false)
    private LocalDateTime registerAt;

    @PrePersist
    protected void onCreate() {
        registerAt = LocalDateTime.now();
    }
}
