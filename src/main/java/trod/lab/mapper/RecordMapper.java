package trod.lab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trod.lab.dto.RecordDTO;
import trod.lab.entity.Record;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(source = "user.id", target = "userId")
    RecordDTO toDto(Record record);
}
