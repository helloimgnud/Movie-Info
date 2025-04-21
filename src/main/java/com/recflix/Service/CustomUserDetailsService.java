package com.recflix.service;

import com.recflix.Exception.ResourceNotFoundException;
import com.recflix.model.Users;
import com.recflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Tên tài khoản không tồn tại"));

        return new org.springframework.security.core.userdetails.User(
                users.getUsername(),                     // Tên người dùng
                users.getPassword(),                     // Mật khẩu
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + users.getRole()))  // Quyền của người dùng
        );
    }
}
