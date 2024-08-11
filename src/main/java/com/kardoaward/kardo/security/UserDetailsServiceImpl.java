package com.kardoaward.kardo.security;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(username);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Адрес " + username + " не найден"));
    }
}
