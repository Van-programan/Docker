package trod.lab.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trod.lab.dto.UserDTO;
import trod.lab.service.UserService;
import trod.lab.util.UserCreatedException;
import trod.lab.util.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable Long userId){
        var user = userService.findUserById(userId);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/filter")
    public List<UserDTO> findUsersByUsername(@RequestParam String username){
        return userService.findUsersByUsername(username);
    }

    @GetMapping("/all")
    public List<UserDTO> findAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        try {
            userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserCreatedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        try {
            userService.updateUser(userDTO);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
