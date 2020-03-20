package fitwf.service;

import fitwf.dto.RegisterDTO;
import fitwf.exception.EmailAlreadyExistsException;
import fitwf.exception.UsernameAlreadyExistsException;
import fitwf.exception.UsernameAndEmailAlreadyExistsException;
import fitwf.model.User;
import fitwf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void block(int id) {
    }

    public User getUserByID(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void registerNewUser(RegisterDTO registerDto) {
        String username = registerDto.getUsername();
        String email = registerDto.getEmail();
        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);
        if (usernameExists && emailExists)
            throw new UsernameAndEmailAlreadyExistsException("Username " + username + " and email " + email + " already exist!");
        else if (usernameExists)
            throw new UsernameAlreadyExistsException("Username " + username + " already exists!");
        else if (emailExists)
            throw new EmailAlreadyExistsException("Email " + email + " already exists!");
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        userRepository.save(user);
    }
}








