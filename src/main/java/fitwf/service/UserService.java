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
}
