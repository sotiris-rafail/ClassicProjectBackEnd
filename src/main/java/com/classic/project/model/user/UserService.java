package com.classic.project.model.user;

import com.classic.project.model.user.response.AddUserToCP;
import com.classic.project.model.user.response.ResponseUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<String> registerUser(User user);

    ResponseEntity<ResponseUser> getUserById(int userId);

    ResponseEntity<List<ResponseUser>> getCharsWithoutCp();

    void addUsersToCp(AddUserToCP userIds);

    ResponseEntity<TypeOfUser> getTypeOfUser(int userId);

    void updateUserRole(int characterId, String typeOfUser);

    void addUserToCp(int characterId, int cpId);

    ResponseEntity<List<ResponseUser>> getUsersForDashboard();
}
