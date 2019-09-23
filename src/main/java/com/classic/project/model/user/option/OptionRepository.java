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

    @Modifying
    @Transactional
    @Query("update Option set soldItemOption = ?2, newItemOption = ?2, bossesOption =?2 where userOption.userId = ?1")
    void updateAllOptions(int userId, boolean optionValue);

    @Modifying
    @Transactional
    @Query("update Option set newItemOption = ?2 where userOption.userId = ?1")
    void updateNewItemsOption(int userId, boolean optionValue);

    @Modifying
    @Transactional
    @Query("update Option set soldItemOption = ?2 where userOption.userId = ?1")
    void updateSoldItemOption(int userId, boolean optionValue);


    @Modifying
    @Transactional
    @Query("update Option set bossesOption =?2 where userOption.userId = ?1")
    void updateBossOption(int userId, boolean optionValue);
}
