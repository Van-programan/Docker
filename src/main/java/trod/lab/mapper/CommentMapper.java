package trod.lab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trod.lab.dto.CommentDTO;
import trod.lab.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "record.id", target = "recordId")
    CommentDTO toDto(Comment comment);
}
