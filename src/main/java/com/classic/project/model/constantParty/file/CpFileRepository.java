package com.classic.project.model.constantParty.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CpFileRepository extends JpaRepository<CpFile, String> {

    @Query("select cpFile from CpFile cpFile where cpFile.cpImg.cpId = ?1")
    List<CpFile> findAllByCpImg(int cpId);

    @Query("select cpFile from CpFile cpFile where cpFile.parents like ?1")
    Optional<CpFile> findByParents(String parent);

    @Query("select cpFile from CpFile cpFile where cpFile.cpImg is null ")
    List<CpFile> getCpFilesWithoutCp();
}
