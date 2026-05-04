package trod.lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trod.lab.dto.UserDTO;
import trod.lab.entity.User;
import trod.lab.mapper.UserMapper;
import trod.lab.repository.UserRepository;
import trod.lab.util.UserCreatedException;
import trod.lab.util.UserNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
    }

//    @Test
//    void findUserById_ShouldReturnUserDTO() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(userMapper.toDto(user)).thenReturn(userDTO);
//
//        Optional<UserDTO> result = userService.findUserById(1L);
//
//        assertThat(result).isPresent();
//        assertThat(result.get().getUsername()).isEqualTo("testuser");
//        verify(userRepository).findById(1L);
//    }
//
//    @Test
//    void findUserById_ShouldReturnEmpty() {
//        when(userRepository.findById(99L)).thenReturn(Optional.empty());
//
//        Optional<UserDTO> result = userService.findUserById(99L);
//
//        assertThat(result).isEmpty();
//        verify(userRepository).findById(99L);
//    }

    @Test
    void findUsersByUsername_ShouldReturnMatchingUsers() {
        when(userRepository.findByUsernameLike("%test%")).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.findUsersByUsername("test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
    }

    @Test
    void findAllUsers_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.findAllUsers();

        assertThat(result).hasSize(1);
    }
//
//    @Test
//    void createUser_ShouldSaveUser_WhenEmailNotExists() {
//        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
//
//        userService.createUser(userDTO);
//
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void createUser_ShouldThrowException_WhenEmailExists() {
//        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
//
//        assertThatThrownBy(() -> userService.createUser(userDTO))
//                .isInstanceOf(UserCreatedException.class);
//
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    void updateUser_ShouldUpdateUser_WhenUserExists() {
//        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
//
//        userService.updateUser(userDTO);
//
//        verify(userRepository).save(user);
//        assertThat(user.getUsername()).isEqualTo("testuser");
//    }
//
//    @Test
//    void updateUser_ShouldThrowException_WhenUserNotFound() {
//        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> userService.updateUser(userDTO))
//                .isInstanceOf(UserNotFoundException.class);
//
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    void deleteUser_ShouldDeleteUser_WhenUserExists() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        doNothing().when(userRepository).deleteById(1L);
//
//        userService.deleteUser(1L);
//
//        verify(userRepository).deleteById(1L);
//    }
//
//    @Test
//    void deleteUser_ShouldThrowException_WhenUserNotFound() {
//        when(userRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> userService.deleteUser(99L))
//                .isInstanceOf(UserNotFoundException.class);
//
//        verify(userRepository, never()).deleteById(any());
//    }
}