package trod.lab.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trod.lab.dto.RecordDTO;
import trod.lab.service.RecordService;
import trod.lab.util.RecordNotFoundException;
import trod.lab.util.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/record")
@AllArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/filter")
    public List<RecordDTO> findRecordByTitle(@RequestParam String title){
        return recordService.findRecordByTitle(title);
    }

    @GetMapping("/{userId}")
    public List<RecordDTO> findRecordByUserId(Long userId){
        return recordService.findRecordByUserId(userId);
    }

    @GetMapping("/all")
    public List<RecordDTO> findAllRecords(){
        return recordService.findAllRecords();
    }

    @PostMapping
    public ResponseEntity<?> createRecord(@RequestBody RecordDTO recordDTO){
        try {
            recordService.createRecord(recordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<?> updateRecord(@RequestBody RecordDTO recordDTO, @PathVariable Long recordId){
        try {
            recordService.updateRecord(recordDTO, recordId);
            return ResponseEntity.ok().build();
        } catch (RecordNotFoundException | UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long recordId){
        try {
            recordService.deleteRecord(recordId);
            return ResponseEntity.ok().build();
        } catch (RecordNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
