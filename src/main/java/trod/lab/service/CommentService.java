package trod.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trod.lab.dto.CommentDTO;
import trod.lab.entity.Comment;
import trod.lab.entity.Record;
import trod.lab.mapper.CommentMapper;
import trod.lab.repository.CommentRepository;
import trod.lab.repository.RecordRepository;
import trod.lab.util.CommentNotFoundException;
import trod.lab.util.RecordNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RecordRepository recordRepository;
    private final CommentMapper commentMapper;

    public List<CommentDTO> findCommentsByRecordId(Long id){
        return commentRepository.findByRecordId(id).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Transactional
    public void createComment(CommentDTO commentDTO){
        Optional<Record> record = recordRepository.findById(commentDTO.getRecordId());
        if (record.isEmpty()) throw new RecordNotFoundException();

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setRecord(record.get());
        comment.setUser(record.get().getUser());

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(CommentDTO commentDTO, Long id){
        Optional<Comment> preComment = commentRepository.findById(id);
        if (preComment.isEmpty()) throw new CommentNotFoundException();

        Optional<Record> record = recordRepository.findById(commentDTO.getRecordId());
        if (record.isEmpty()) throw new RecordNotFoundException();

        Comment comment = preComment.get();
        comment.setContent(commentDTO.getContent());
        comment.setRecord(record.get());
        comment.setUser(record.get().getUser());

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id){
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) throw new CommentNotFoundException();

        commentRepository.deleteById(id);
    }
}
