package trod.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String username;
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime registerAt;
}
