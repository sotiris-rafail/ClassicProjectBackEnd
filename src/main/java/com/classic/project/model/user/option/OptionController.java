package com.classic.project.model.user.option;

import com.classic.project.model.user.option.response.ResponseOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/user/option")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @RequestMapping(value = "/getConfigOptions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseOption> getWhatOptionsToShow() {
        return optionService.getWhatOptionsToShow();
    }
}
