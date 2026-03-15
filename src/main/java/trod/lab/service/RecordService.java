package trod.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trod.lab.dto.RecordDTO;
import trod.lab.entity.Record;
import trod.lab.entity.User;
import trod.lab.mapper.RecordMapper;
import trod.lab.repository.RecordRepository;
import trod.lab.repository.UserRepository;
import trod.lab.util.RecordNotFoundException;
import trod.lab.util.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final RecordMapper recordMapper;

    public List<RecordDTO> findRecordByTitle(String title){
        return recordRepository.findByTitleLike("%" + title + "%").stream()
                .map(recordMapper::toDto)
                .toList();
    }

    public List<RecordDTO> findRecordByUserId(Long id){
        return recordRepository.findByUserId(id).stream()
                .map(recordMapper::toDto)
                .toList();
    }

    public List<RecordDTO> findAllRecords(){
        return recordRepository.findAll().stream()
                .map(recordMapper::toDto)
                .toList();
    }

    @Transactional
    public void createRecord(RecordDTO recordDTO){
        Optional<User> user = userRepository.findById(recordDTO.getUserId());
        if (user.isEmpty()) throw new UserNotFoundException("Такого пользователя не существует");

        Record record = new Record();
        record.setUser(user.get());
        record.setTitle(recordDTO.getTitle());
        record.setDescription(recordDTO.getDescription());

        recordRepository.save(record);
    }

    @Transactional
    public void updateRecord(RecordDTO recordDTO, Long id){
        Optional<Record> preRecord = recordRepository.findById(id);
        if (preRecord.isEmpty()) throw new RecordNotFoundException();

        Optional<User> user = userRepository.findById(recordDTO.getUserId());
        if (user.isEmpty()) throw new UserNotFoundException("Такого пользователя не существует");

        Record record = preRecord.get();
        record.setUser(user.get());
        record.setTitle(recordDTO.getTitle());
        record.setDescription(recordDTO.getDescription());

        recordRepository.save(record);
    }

    @Transactional
    public void deleteRecord(Long id){
        Optional<Record> record = recordRepository.findById(id);
        if (record.isEmpty()) throw new RecordNotFoundException();

        recordRepository.deleteById(id);
    }
}
