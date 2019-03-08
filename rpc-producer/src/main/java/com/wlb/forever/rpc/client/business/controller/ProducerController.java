package com.wlb.forever.rpc.client.business.controller;

import com.wlb.forever.rpc.client.business.entity.User;
import com.wlb.forever.rpc.client.business.service.UserService;
import com.wlb.forever.rpc.common.entity.JsonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: william
 * @Date: 18/11/15 14:23
 * @Description:
 */
@RestController
@RequestMapping("/producer")
@Slf4j
public class ProducerController {
    @Autowired
    private UserService userService;

    @ApiOperation("获取用户")
    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<JsonResult> test() {
        JsonResult jr = new JsonResult();
        try {
            User user = userService.getUser();
            jr.setResult(user);
            return ResponseEntity.ok(jr);
        } catch (Exception e) {
            e.printStackTrace();
            jr.setStatus(-1);
            jr.setDesc("出现异常");
            return ResponseEntity.ok(jr);
        }
    }
}
