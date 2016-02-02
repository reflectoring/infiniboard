package com.github.reflectoring.controller;

import com.github.reflectoring.model.Widget;
import com.github.reflectoring.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/widgets")
@RestController
public class WidgetController {

    private WidgetService widgetService;


    @Autowired
    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Widget> widgets() {
        return widgetService.loadWidgets();
    }

}
