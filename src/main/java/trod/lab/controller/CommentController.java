package trod.lab.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trod.lab.dto.CommentDTO;
import trod.lab.service.CommentService;
import trod.lab.util.CommentNotFoundException;
import trod.lab.util.RecordNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{recordId}")
    public List<CommentDTO> findCommentsByRecordId(@PathVariable Long recordId){
        return commentService.findCommentsByRecordId(recordId);
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO){
        try {
            commentService.createComment(commentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long commentId){
        try {
            commentService.updateComment(commentDTO, commentId);
            return ResponseEntity.ok().build();
        } catch (CommentNotFoundException | RecordNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok().build();
        } catch (CommentNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
