package fitwf.service;

import fitwf.dto.RegisterDTO;
import fitwf.exception.EmailAlreadyExistsException;
import fitwf.exception.UsernameAlreadyExistsException;
import fitwf.exception.UsernameAndEmailAlreadyExistsException;
import fitwf.model.User;
import fitwf.model.UserPrinciple;
import fitwf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
/*
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with username=" + username + " not found!"));
    }*/

    public void registerNewUser(RegisterDTO registerDto) {
        String username = registerDto.getUsername();
        String email = registerDto.getEmail();
        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);
        if (usernameExists && emailExists)
            throw new UsernameAndEmailAlreadyExistsException("Username=" + username + " and email=" + email + " already exist");
        else if (usernameExists)
            throw new UsernameAlreadyExistsException("Username=" + username + " already exists");
        else if (emailExists)
            throw new EmailAlreadyExistsException("Email=" + email + " already exists");
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrinciple.create(
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("Username=" + username + " not found"))
        );
    }
}








