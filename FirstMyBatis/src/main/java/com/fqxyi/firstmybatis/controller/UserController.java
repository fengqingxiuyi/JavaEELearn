package com.fqxyi.firstmybatis.controller;

import com.fqxyi.firstmybatis.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/insertName")
    public String insertName(@RequestParam("id") int id, @RequestParam("name") String name) {
        JSONObject jsonObject = new JSONObject();
        try {
            int affectedRows = userService.insertName(id, name);
            if (affectedRows == 0) {
                jsonObject.put("resultCode", -2);
                jsonObject.put("resultMessage", "数据库插入失败");
            } else {
                jsonObject.put("resultCode", 200);
                jsonObject.put("resultMessage", "数据库插入成功");
            }
        } catch (Exception e) {
            jsonObject.put("resultCode", -1);
            jsonObject.put("resultMessage", "数据库插入异常：" + e.getMessage());
        }
        return jsonObject.toString();
    }

    @RequestMapping("/updateName")
    public String updateName(@RequestParam("id") int id, @RequestParam("name") String name) {
        JSONObject jsonObject = new JSONObject();
        try {
            int affectedRows = userService.updateName(id, name);
            if (affectedRows == 0) {
                jsonObject.put("resultCode", -2);
                jsonObject.put("resultMessage", "数据库更新失败");
            } else {
                jsonObject.put("resultCode", 200);
                jsonObject.put("resultMessage", "数据库更新成功");
            }
        } catch (Exception e) {
            jsonObject.put("resultCode", -1);
            jsonObject.put("resultMessage", "数据库更新异常：" + e.getMessage());
        }
        return jsonObject.toString();
    }

    @RequestMapping("/queryName")
    public String queryName(@RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            String name = userService.queryName(id);
            if (StringUtils.isEmpty(name)) {
                jsonObject.put("resultCode", -2);
                jsonObject.put("resultMessage", "数据库查询失败");
            } else {
                JSONObject dataObject = new JSONObject();
                dataObject.put("name", name);
                jsonObject.put("data", dataObject);
                jsonObject.put("resultCode", 200);
                jsonObject.put("resultMessage", "数据库查询成功");
            }
        } catch (Exception e) {
            jsonObject.put("resultCode", -1);
            jsonObject.put("resultMessage", "数据库查询异常：" + e.getMessage());
        }
        return jsonObject.toString();
    }

}
