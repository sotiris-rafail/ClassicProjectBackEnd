package com.classic.project.security;

import com.classic.project.model.user.User;
import com.classic.project.model.user.UserNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthConfirm {

    public void isTheAuthUser(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UpdateUserDetails authUser = (UpdateUserDetails) auth.getPrincipal();
        if (!authUser.getModifiableEmail().equals(user.getEmail())) {
            throw new ForbiddenException();
        }
    }

    public void updateAuthUserEmail(String email) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UpdateUserDetails authUser = (UpdateUserDetails) auth.getPrincipal();

        if (authUser.getModifiableEmail().equalsIgnoreCase(email)) {
            return;
        }
        authUser.setModifiableEmail(email);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(email, authUser.getPassword(), authUser.getAuthorities()));
    }
}
