package com.classic.project.model.constantParty;

import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.constantParty.response.file.AddNewFile;
import com.classic.project.model.constantParty.response.file.RootFolderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface ConstantPartyService {
    ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId);

    ResponseEntity<ResponseConstantParty> getCp(int cpId, int userId);

    void deleteMember(int characterId);

    void addNewCP(ConstantParty newCp);

    ResponseEntity<List<ResponseConstantParty>> getCPIdName();

    ResponseEntity<List<ResponseConstantParty>> getCPNumbers();

    ResponseEntity<List<ResponseConstantParty>> getEpicPoints();

    boolean uploadEpicPhoto(MultipartFile photo, int cpId, String cpName);

    ResponseEntity<RootFolderResponse> getCpPhotos(int cpId, int userId);

    void addNewFolder(int cpId, AddNewFile cpFile) throws GeneralSecurityException, IOException;
}
