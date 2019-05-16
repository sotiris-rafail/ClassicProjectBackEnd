package com.classic.project.model.constantParty.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
//delete pf,cpFile from parent_file pf INNER JOIN cp_file cpFile where cpFile.file_id = pf.file_id and  cpFile.cp_id is null;
public interface CpFileRepository extends JpaRepository<CpFile, String> {

    @Query("select cpFile from CpFile cpFile where cpFile.cpImg.cpId = ?1")
    List<CpFile> findAllByCpImg(int cpId);

    @Query("select cpFile from CpFile cpFile where cpFile.parents like ?1")
    Optional<CpFile> findByParents(String parent);

    @Query("select cpFile from CpFile cpFile where cpFile.cpImg is null ")
    List<CpFile> getCpFilesWithoutCp();

    @Query("select cpFile from CpFile cpFile where cpFile.fileType = 2")
    List<CpFile> getRootFolders();


    @Modifying
    @Transactional
    @Query("delete from CpFile cpFile where cpFile.cpImg is null")
    void deleteCpFilesWithoutCp();
}
