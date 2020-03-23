package fitwf.service;

import fitwf.dto.RegisterDTO;
import fitwf.entity.User;
import fitwf.exception.*;
import fitwf.repository.RoleRepository;
import fitwf.repository.UserRepository;
import fitwf.security.RoleName;
import fitwf.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void changePassword(String oldPassword, String newPassword) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentPassword = jwtUser.getPassword();
        if (!passwordEncoder.matches(oldPassword, currentPassword))
            throw new InvalidPasswordException("Old password is invalid");
        if (oldPassword.equals(newPassword))
            throw new OldAndNewPasswordEqualException("Old and new passwords cannot be the same");
        userRepository.updatePassword(jwtUser.getId(), passwordEncoder.encode(newPassword));
    }

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
        user.setRoles(Collections.singletonList(roleRepository.findByName(RoleName.ROLE_USER)));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return JwtUser.create(userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username=" + username + " not found")));
    }
}








