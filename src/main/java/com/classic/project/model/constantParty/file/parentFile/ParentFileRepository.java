package com.classic.project.model.constantParty.file.parentFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParentFileRepository extends JpaRepository<ParentFile, String> {

    @Query("select parent.parentId from ParentFile parent where parent.folderId.fileId = ?1")
    List<String> getParentIdByFileId(String fileId);

    @Query("select parent from ParentFile parent where parent.folderId.fileId = ?1")
    List<ParentFile> getParentsByFileId(String fileId);
}
