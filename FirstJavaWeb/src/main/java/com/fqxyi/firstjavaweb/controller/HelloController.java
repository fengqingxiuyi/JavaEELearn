package com.fqxyi.firstjavaweb.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @CrossOrigin("http://vue.fqxyi.com")
    @RequestMapping("/hello")
    public String index() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", 200);
        jsonObject.put("resultMessage", "success");
        jsonObject.put("key", "Hello World");
        return jsonObject.toString();
    }

}
