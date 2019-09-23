package com.classic.project.model.user.option;

import com.classic.project.model.user.User;

public interface OptionService {

    public void saveOptionsOnRegister(User user);

    void updateOptions(int userId, String options, boolean optionValue);

    void deleteByUserId(int userId);
}
