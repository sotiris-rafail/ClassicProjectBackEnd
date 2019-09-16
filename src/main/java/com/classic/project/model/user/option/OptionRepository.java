package com.classic.project.model.user.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface OptionRepository extends JpaRepository<Option, Integer> {

    @Modifying
    @Transactional
    @Query("delete from Option where userOption.userId = ?1")
    void deleteByUserId(int userId);
}
