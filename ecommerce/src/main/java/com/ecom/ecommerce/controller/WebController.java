package com.ecom.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping({"/", "/create", "/home"})
    public String index() {
        return "forward:/index.html";
    }
}
