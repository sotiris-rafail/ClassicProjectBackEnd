package com.classic.project.model.user.option;

import com.classic.project.model.user.User;
import com.classic.project.model.user.option.response.ResponseOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Value("${send.email.new.items}")
    private boolean sendEmailNewItemsConf;

    @Value("${send.email.sold.items}")
    private boolean sendEmailSoldItemsConf;

    @Value("${send.email.raid.boss.epics.on.window}")
    private boolean sendEmailRaidBossItemsConf;


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
    public ResponseEntity<ResponseOption> getWhatOptionsToShow() {
        return new ResponseEntity<>(new ResponseOption(sendEmailRaidBossItemsConf, sendEmailNewItemsConf, sendEmailSoldItemsConf), HttpStatus.OK);
    }

    @Override
    public void saveOptionsOnRegister(User user) {
        Option option = new Option();
        option.setUserOption(user);
        optionRepository.save(option);
    }
}
