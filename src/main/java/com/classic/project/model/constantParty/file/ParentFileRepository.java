package com.classic.project.model.constantParty.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParentFileRepository extends JpaRepository<ParentFile, Integer> {

    @Query("select parent.parentId from ParentFile parent where parent.folderId.fileId = ?1")
    List<String> getParetIdByFileId(String fileId);
}
