package com.classic.project.model.constantParty.file.schedule;

import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.CpFileRepository;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.file.parentFile.ParentFile;
import com.classic.project.model.constantParty.file.parentFile.ParentFileRepository;
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
    private ParentFileRepository parentFileRepository;

    List<CpFile> cpFiles = new ArrayList<>();

    //@Scheduled(cron = "0 0 */3 * * *")//runs every 3 hours
    private void assignFileToCp() {
        List<CpFile> toBeSaved = new ArrayList<>();
        cpFiles = cpFileRepository.getCpFilesWithoutCp();
        for (CpFile file : cpFiles) {
            if (!file.getFileType().getType().equals(FileType.ROOT.getType())) {
                List<ParentFile> parents = parentFileRepository.getParentsByFileId(file.getFileId());
                for (ParentFile parentFile : parents) {
                    Optional<CpFile> cpFile = cpFileRepository.findById(parentFile.getParentId());
                    if (parentFile.getFolderId().getFileId().equals(file.getFileId()) && cpFile.isPresent()) {
                        if (cpFile.get().getCpImg() != null) {
                            file.setCpImg(cpFile.get().getCpImg());
                            toBeSaved.add(file);
                        }
                    }
                }
            }
        }
        cpFileRepository.saveAll(toBeSaved);
    }

    //@Scheduled(cron = "0 0 23 * * *")//runs every night at 23
    private void deleteFilesWithoutCP(){
        cpFileRepository.deleteCpFilesWithoutCp();
    }
}
