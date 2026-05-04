package trod.lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecordMapper recordMapper;

    @InjectMocks
    private RecordService recordService;

    private User user;
    private Record record;
    private RecordDTO recordDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        record = new Record();
        record.setId(1L);
        record.setTitle("Test Title");
        record.setDescription("Test Description");
        record.setUser(user);

        recordDTO = new RecordDTO();
        recordDTO.setUserId(1L);
        recordDTO.setTitle("Test Title");
        recordDTO.setDescription("Test Description");
    }

    @Test
    void findRecordByTitle_ShouldReturnMatchingRecords() {
        when(recordRepository.findByTitleLike("%Test%")).thenReturn(List.of(record));
        when(recordMapper.toDto(record)).thenReturn(recordDTO);

        List<RecordDTO> result = recordService.findRecordByTitle("Test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Title");
    }

    @Test
    void findRecordByUserId_ShouldReturnUserRecords() {
        when(recordRepository.findByUserId(1L)).thenReturn(List.of(record));
        when(recordMapper.toDto(record)).thenReturn(recordDTO);

        List<RecordDTO> result = recordService.findRecordByUserId(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    void findAllRecords_ShouldReturnAllRecords() {
        when(recordRepository.findAll()).thenReturn(List.of(record));
        when(recordMapper.toDto(record)).thenReturn(recordDTO);

        List<RecordDTO> result = recordService.findAllRecords();

        assertThat(result).hasSize(1);
    }

    @Test
    void createRecord_ShouldSaveRecord_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        recordService.createRecord(recordDTO);

        verify(recordRepository).save(any(Record.class));
    }

    @Test
    void createRecord_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recordService.createRecord(recordDTO))
                .isInstanceOf(UserNotFoundException.class);

        verify(recordRepository, never()).save(any(Record.class));
    }

    @Test
    void updateRecord_ShouldUpdateRecord_WhenRecordAndUserExist() {
        when(recordRepository.findById(1L)).thenReturn(Optional.of(record));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        recordService.updateRecord(recordDTO, 1L);

        verify(recordRepository).save(record);
        assertThat(record.getTitle()).isEqualTo("Test Title");
    }

    @Test
    void updateRecord_ShouldThrowException_WhenRecordNotFound() {
        when(recordRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recordService.updateRecord(recordDTO, 99L))
                .isInstanceOf(RecordNotFoundException.class);

        verify(recordRepository, never()).save(any());
    }

    @Test
    void deleteRecord_ShouldDeleteRecord_WhenRecordExists() {
        when(recordRepository.findById(1L)).thenReturn(Optional.of(record));
        doNothing().when(recordRepository).deleteById(1L);

        recordService.deleteRecord(1L);

        verify(recordRepository).deleteById(1L);
    }

    @Test
    void deleteRecord_ShouldThrowException_WhenRecordNotFound() {
        when(recordRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recordService.deleteRecord(99L))
                .isInstanceOf(RecordNotFoundException.class);

        verify(recordRepository, never()).deleteById(any());
    }
}