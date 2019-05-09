package com.classic.project.model.constantParty.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CpFileRepository extends JpaRepository<CpFile, String> {

    List<CpFile> findAllByCpImg(String cpId);

    Optional<CpFile> findByParents(String parent);
}
