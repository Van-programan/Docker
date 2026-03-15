package trod.lab.mapper;

import org.mapstruct.Mapper;
import trod.lab.dto.UserDTO;
import trod.lab.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
}
