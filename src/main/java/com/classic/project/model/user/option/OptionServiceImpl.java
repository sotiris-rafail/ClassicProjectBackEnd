package com.classic.project.model.user.option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;
}
