package com.classic.project.model.constantParty.response.file.schedule;

import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FileProcessing {

    @Autowired
    private CpFileRepository cpFileRepository;

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    List<CpFile> cpFiles = new ArrayList<>();

    private void assignFileToCp() {
        cpFiles = cpFileRepository.getCpFilesWithoutCp();
        for (CpFile file : cpFiles) {
            if (!file.getFileType().getType().equals(FileType.ROOT.getType())) {
                file.setCpImg(findCpId(cpFiles, file).get().getCpImg());
            }
        }
    }

    private Optional<CpFile> findCpId(List<CpFile> files, CpFile file) {
        Optional<CpFile> parent;
        if (!cpFileRepository.existsById(file.getFileId())) {
            parent = cpFileRepository.findById(file.getParents());
            if (!parent.isPresent()) {
                parent = findParent(files, file.getParents());
            }
        } else {
            return cpFileRepository.findById(file.getFileId());
        }
        if (parent.isPresent()) {
            if (parent.get().getCpImg() != null || parent.get().getFileType().getType().equals(FileType.ROOT.getType())) {
                return parent;
            } else {
                return findCpId(files, parent.get());
            }
        } else {
            if (!findParent(cpFiles, file.getParents()).isPresent()) {
                CpFile cpFile = new CpFile();
                cpFile.setCpImg(constantPartyRepository.findById(99).get());
                return Optional.of(cpFile);
            } else {
                return findParent(cpFiles, file.getParents());
            }
        }

    }

    private Optional<CpFile> findParent(List<CpFile> cpFiles, String parents) {
        for (CpFile cpFile : cpFiles) {
            if (cpFile.getFileId().equals(parents)) {
                return Optional.of(cpFile);
            }
        }
        return Optional.empty();
    }
}
