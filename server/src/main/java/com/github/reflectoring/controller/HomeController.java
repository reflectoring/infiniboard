package com.github.reflectoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/")
@Controller
public class HomeController {


    @RequestMapping(method = GET)
    @ResponseBody
    public String usage() {
        return "/widgets\n<br>\n    returns a list of widgets as json";
    }

}
