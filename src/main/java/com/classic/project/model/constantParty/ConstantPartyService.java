package com.classic.project.model.constantParty;

import com.classic.project.model.constantParty.response.ResponseConstantParty;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.List;

public interface ConstantPartyService {
    ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId);

    ResponseEntity<ResponseConstantParty> getCp(int cpId, int userId);

    void deleteMember(int characterId);

    void updateEpicPoints(String rbName, int pointsToAdd, int cpId);

    void addNewCP(ConstantParty newCp);

    ResponseEntity<List<ResponseConstantParty>> getCPIdName();

    ResponseEntity<List<ResponseConstantParty>> getCPNumbers();

    ResponseEntity<List<ResponseConstantParty>> getEpicPoints();

    boolean uploadEpicPhoto(File photo, int cpId, int cpName);
}
