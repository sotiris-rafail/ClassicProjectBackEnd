package com.classic.project.security;

import com.classic.project.model.user.User;
import com.classic.project.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private int userId;

    public int getUserId() {
        return userId;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        final User user = userRepository.findUserByEmail(email);
        if(user == null) {
          throw new UsernameNotFoundException(email);
        }
        userId = user.getUserId();
        authorityList.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        return new UpdateUserDetails(user.getEmail(), user.getPassword(), authorityList);
    }
}
