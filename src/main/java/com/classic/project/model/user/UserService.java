package com.classic.project.model.user;

import com.classic.project.model.user.response.AddUserToCP;
import com.classic.project.model.user.response.ResponseUser;
import com.classic.project.model.user.verification.VerificationStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseEntity<String> registerUser(User user);

    ResponseEntity<ResponseUser> getUserById(int userId);

    ResponseEntity<List<ResponseUser>> getCharsWithoutCp();

    ResponseEntity<List<ResponseUser>> addUsersToCp(AddUserToCP userIds);

    ResponseEntity<TypeOfUser> getTypeOfUser(int userId);

    void updateUserRole(int characterId, String typeOfUser);

    void addUserToCp(int characterId, int cpId);

    ResponseEntity<List<ResponseUser>> getUsersForDashboard();

    void deleteUser(int userId);

    ResponseEntity<Boolean> verifyUser(String email, String mainChar);

    ResponseEntity<Boolean> updatePassword(String[] params);

    ResponseEntity<Boolean> isCpMember(int userId);

    ResponseEntity<VerificationStatus> sendVerificationEmailToUser(String email);

    ResponseEntity<VerificationStatus> acceptVerificationMailCode(String code);

    void updateUserOptions(int userId, String options, boolean optionValue);

    Optional<User> findById(int userId);

    void deleteMemberByCharacterIdId(int userId);

    Optional<User> isUserMemberOfCP(int cpId, int userId);
}
