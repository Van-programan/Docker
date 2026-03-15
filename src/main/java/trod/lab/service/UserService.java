package trod.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trod.lab.dto.UserDTO;
import trod.lab.entity.User;
import trod.lab.mapper.UserMapper;
import trod.lab.repository.UserRepository;
import trod.lab.util.UserCreatedException;
import trod.lab.util.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<UserDTO> findUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public List<UserDTO> findUsersByUsername(String username){
        return userRepository.findByUsernameLike("%" + username + "%").stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<UserDTO> findAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    public void createUser(UserDTO userDTO){
        Optional<User> preUser = userRepository.findByEmail(userDTO.getEmail());
        if (preUser.isPresent()) throw new UserCreatedException();

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(UserDTO userDTO){
        Optional<User> preUser = userRepository.findByEmail(userDTO.getEmail());
        if (preUser.isEmpty()) throw new UserNotFoundException("Пользователь с таким email не существует");

        User user = preUser.get();
        user.setUsername(userDTO.getUsername());

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("Пользователь с таким email не существует");

        userRepository.deleteById(id);
    }

}
