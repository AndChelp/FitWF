package fitwf.service;

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
        boolean usernameExists = userRepository.isUsernameExist(username);
        boolean emailExists = userRepository.isEmailExist(email);
        if(usernameExists && emailExists) 
            throw new UsernameAndEmailAlreadyExists("Username "+username+" and email "+email+" already exist!");
        else if (usernameExists)
            throw new UsernameAlreadyExists();
        else if (emailExists)
            throw new EmailAlreadyExists();
        userRepository.save(new User(registerDto));
    }
}








