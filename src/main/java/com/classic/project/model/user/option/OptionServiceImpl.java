package com.classic.project.model.user.option;

import com.classic.project.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public void updateOptions(int userId, String options, boolean optionValue) {
        if(options.equals(UserOption.ALL.name())) {
            optionRepository.updateAllOptions(userId, optionValue);
        } else if (options.equals(UserOption.BOSSES.name())) {
            optionRepository.updateBossOption(userId, optionValue);
        } else if (options.equals(UserOption.SOLDITEM.name())) {
            optionRepository.updateSoldItemOption(userId, optionValue);
        } else if (options.equals(UserOption.NEWITEM.name())) {
            optionRepository.updateNewItemsOption(userId, optionValue);
        }
    }

    @Override
    public void deleteByUserId(int userId) {
        optionRepository.deleteByUserId(userId);
    }

    @Override
    public void saveOptionsOnRegister(User user) {
        Option option = new Option();
        option.setUserOption(user);
        optionRepository.save(option);
    }
}
