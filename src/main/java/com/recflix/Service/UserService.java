package com.recflix.service;

import com.recflix.dto.*;
import com.recflix.Exception.*;
import com.recflix.model.*;
import com.recflix.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepository;
    private final ConvertDTOService converter;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Autowired
    public UserService(UserRepository userRepository, ConvertDTOService converter, PasswordEncoder passwordEncoder, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserDTO userDTO) {
        Optional<Users> createdUser = userRepository.findByUsername(userDTO.getUsername());
        Users newUser = new Users();
        if (createdUser.isEmpty()) { 
            newUser.setUsername(userDTO.getUsername());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRole(userDTO.getRole());
            userRepository.save(newUser);
        }
        return converter.convertToDTO(newUser);
    }

    public Users newPassword(Long id, String newPassword){
        Users users = userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("tài khoản không tồn tại"));
        return users;
    }

    @PostConstruct
    @Transactional
    public void init() {
        Optional<Users> admin = userRepository.findByUsername("admin123");
        if (admin.isEmpty()) {
            Users originalAdmin = new Users();
            originalAdmin.setUsername("admin123");
            originalAdmin.setPassword(passwordEncoder.encode("admin@123"));
            originalAdmin.setRole("ADMIN");
            userRepository.save(originalAdmin);
        }

        Optional<Users> user = userRepository.findByUsername("user123");
        if (user.isEmpty()) {
            Users originalUser = new Users();
            originalUser.setUsername("user123");
            originalUser.setPassword(passwordEncoder.encode("user@123"));
            originalUser.setRole("USER");
            userRepository.save(originalUser);
        }

        Optional<Users> user2 = userRepository.findByUsername("user");
        if (user2.isEmpty()) {
            Users originalUser2 = new Users();
            originalUser2.setUsername("user");
            originalUser2.setPassword(passwordEncoder.encode("!@#$%^&*"));
            originalUser2.setRole("USER");

            Account newAcc = new Account();
            newAcc.setUser(originalUser2);
            userRepository.save(originalUser2);
            accountService.createAccount(newAcc);
        }
    }
}
