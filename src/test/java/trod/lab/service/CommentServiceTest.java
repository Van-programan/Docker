package trod.lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trod.lab.dto.CommentDTO;
import trod.lab.entity.Comment;
import trod.lab.entity.Record;
import trod.lab.entity.User;
import trod.lab.mapper.CommentMapper;
import trod.lab.repository.CommentRepository;
import trod.lab.repository.RecordRepository;
import trod.lab.util.CommentNotFoundException;
import trod.lab.util.RecordNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    private User user;
    private Record record;
    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRegisterAt(LocalDateTime.now());

        record = new Record();
        record.setId(10L);
        record.setTitle("Test Record");
        record.setDescription("Test Description");
        record.setUser(user);
        record.setCreatedAt(LocalDateTime.now());

        comment = new Comment();
        comment.setId(100L);
        comment.setContent("Test comment content");
        comment.setRecord(record);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        commentDTO = new CommentDTO();
        commentDTO.setRecordId(10L);
        commentDTO.setUserId(1L);
        commentDTO.setContent("Test comment content");
    }

    @Test
    void findCommentsByRecordId_ShouldReturnComments() {
        List<Comment> comments = List.of(comment);
        when(commentRepository.findByRecordId(10L)).thenReturn(comments);
        when(commentMapper.toDto(comment)).thenReturn(commentDTO);

        List<CommentDTO> result = commentService.findCommentsByRecordId(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContent()).isEqualTo("Test comment content");
        assertThat(result.get(0).getRecordId()).isEqualTo(10L);

        verify(commentRepository).findByRecordId(10L);
        verify(commentMapper).toDto(comment);
    }

//    @Test
//    void findCommentsByRecordId_ShouldReturnEmptyList() {
//        when(commentRepository.findByRecordId(99L)).thenReturn(List.of());
//
//        List<CommentDTO> result = commentService.findCommentsByRecordId(99L);
//
//        assertThat(result).isEmpty();
//        verify(commentRepository).findByRecordId(99L);
//        verify(commentMapper, never()).toDto(any());
//    }
//
//    @Test
//    void findCommentsByRecordId_ShouldReturnMultipleComments() {
//        Comment comment2 = new Comment();
//        comment2.setId(101L);
//        comment2.setContent("Second comment");
//        comment2.setRecord(record);
//        comment2.setUser(user);
//
//        CommentDTO commentDTO2 = new CommentDTO();
//        commentDTO2.setRecordId(10L);
//        commentDTO2.setContent("Second comment");
//
//        List<Comment> comments = List.of(comment, comment2);
//        when(commentRepository.findByRecordId(10L)).thenReturn(comments);
//        when(commentMapper.toDto(comment)).thenReturn(commentDTO);
//        when(commentMapper.toDto(comment2)).thenReturn(commentDTO2);
//
//        List<CommentDTO> result = commentService.findCommentsByRecordId(10L);
//
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getContent()).isEqualTo("Test comment content");
//        assertThat(result.get(1).getContent()).isEqualTo("Second comment");
//
//        verify(commentRepository).findByRecordId(10L);
//        verify(commentMapper, times(2)).toDto(any(Comment.class));
//    }
//
//    @Test
//    void createComment_ShouldSaveComment_WhenRecordExists() {
//        when(recordRepository.findById(10L)).thenReturn(Optional.of(record));
//        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
//
//        commentService.createComment(commentDTO);
//
//        verify(recordRepository).findById(10L);
//        verify(commentRepository).save(any(Comment.class));
//    }
//
//    @Test
//    void createComment_ShouldSetCorrectFields_WhenCreatingComment() {
//        when(recordRepository.findById(10L)).thenReturn(Optional.of(record));
//
//        commentService.createComment(commentDTO);
//
//        verify(commentRepository).save(argThat(savedComment -> {
//            assertThat(savedComment.getContent()).isEqualTo("Test comment content");
//            assertThat(savedComment.getRecord()).isEqualTo(record);
//            assertThat(savedComment.getUser()).isEqualTo(user);
//            return true;
//        }));
//    }
//
//    @Test
//    void createComment_ShouldThrowRecordNotFoundException_WhenRecordDoesNotExist() {
//        when(recordRepository.findById(999L)).thenReturn(Optional.empty());
//        commentDTO.setRecordId(999L);
//
//        assertThatThrownBy(() -> commentService.createComment(commentDTO))
//                .isInstanceOf(RecordNotFoundException.class);
//
//        verify(recordRepository).findById(999L);
//        verify(commentRepository, never()).save(any(Comment.class));
//    }
//
//    @Test
//    void updateComment_ShouldUpdateComment() {
//        CommentDTO updatedDTO = new CommentDTO();
//        updatedDTO.setRecordId(10L);
//        updatedDTO.setContent("Updated comment content");
//
//        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));
//        when(recordRepository.findById(10L)).thenReturn(Optional.of(record));
//        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
//
//        commentService.updateComment(updatedDTO, 100L);
//
//        verify(commentRepository).findById(100L);
//        verify(recordRepository).findById(10L);
//        verify(commentRepository).save(comment);
//
//        assertThat(comment.getContent()).isEqualTo("Updated comment content");
//        assertThat(comment.getRecord()).isEqualTo(record);
//    }
//
//    @Test
//    void updateComment_ShouldThrowCommentNotFoundException_WhenCommentDoesNotExist() {
//        when(commentRepository.findById(999L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> commentService.updateComment(commentDTO, 999L))
//                .isInstanceOf(CommentNotFoundException.class);
//
//        verify(commentRepository).findById(999L);
//        verify(recordRepository, never()).findById(any());
//        verify(commentRepository, never()).save(any(Comment.class));
//    }
//
//    @Test
//    void updateComment_ShouldThrowRecordNotFoundException_WhenRecordDoesNotExist() {
//        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));
//        when(recordRepository.findById(10L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> commentService.updateComment(commentDTO, 100L))
//                .isInstanceOf(RecordNotFoundException.class);
//
//        verify(commentRepository).findById(100L);
//        verify(recordRepository).findById(10L);
//        verify(commentRepository, never()).save(any(Comment.class));
//    }
//
//    @Test
//    void updateComment_ShouldChangeRecord_WhenRecordIdChanges() {
//        Record newRecord = new Record();
//        newRecord.setId(20L);
//        newRecord.setTitle("New Record");
//        newRecord.setUser(user);
//
//        CommentDTO updatedDTO = new CommentDTO();
//        updatedDTO.setRecordId(20L);
//        updatedDTO.setContent("Comment for new record");
//
//        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));
//        when(recordRepository.findById(20L)).thenReturn(Optional.of(newRecord));
//
//        commentService.updateComment(updatedDTO, 100L);
//
//        assertThat(comment.getRecord()).isEqualTo(newRecord);
//        assertThat(comment.getRecord().getId()).isEqualTo(20L);
//    }
//
//    @Test
//    void deleteComment_ShouldDeleteComment_WhenCommentExists() {
//        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));
//        doNothing().when(commentRepository).deleteById(100L);
//
//        commentService.deleteComment(100L);
//
//        verify(commentRepository).findById(100L);
//        verify(commentRepository).deleteById(100L);
//    }
//
//    @Test
//    void deleteComment_ShouldThrowCommentNotFoundException_WhenCommentDoesNotExist() {
//        when(commentRepository.findById(999L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> commentService.deleteComment(999L))
//                .isInstanceOf(CommentNotFoundException.class);
//
//        verify(commentRepository).findById(999L);
//        verify(commentRepository, never()).deleteById(any());
//    }
//
//    @Test
//    void updateComment_ShouldHandleEmptyContent() {
//        CommentDTO emptyContentDTO = new CommentDTO();
//        emptyContentDTO.setRecordId(10L);
//        emptyContentDTO.setContent("");
//
//        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));
//        when(recordRepository.findById(10L)).thenReturn(Optional.of(record));
//
//        commentService.updateComment(emptyContentDTO, 100L);
//
//        assertThat(comment.getContent()).isEmpty();
//    }
//
//    @Test
//    void updateComment_ShouldHandleNullContent() {
//        // given
//        CommentDTO nullContentDTO = new CommentDTO();
//        nullContentDTO.setRecordId(10L);
//        nullContentDTO.setContent(null);
//
//        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));
//        when(recordRepository.findById(10L)).thenReturn(Optional.of(record));
//
//        // when
//        commentService.updateComment(nullContentDTO, 100L);
//
//        // then
//        assertThat(comment.getContent()).isNull();
//    }
}