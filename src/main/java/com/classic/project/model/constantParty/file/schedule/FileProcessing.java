package com.classic.project.model.constantParty.file.schedule;

import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.file.ParentFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    private ParentFileRepository parentFileRepository;

    List<CpFile> cpFiles = new ArrayList<>();

    @Scheduled(cron = "0 * * * * *")
    private void assignFileToCp() {
        cpFiles = cpFileRepository.getCpFilesWithoutCp();
        for (CpFile file : cpFiles) {
            if (!file.getFileType().getType().equals(FileType.ROOT.getType())) {
            }
        }
    }
}
