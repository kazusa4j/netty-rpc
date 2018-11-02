package com.wlb.forever.rpc.client.business.controller;

import com.wlb.forever.rpc.client.business.entity.User;
import com.wlb.forever.rpc.client.business.service.RpcTest2Service;
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
 * @Date: 18/10/18 09:13
 * @Description:
 */
@RestController
@RequestMapping("/rpc")
@Slf4j
public class TestRpcController {
    @Autowired
    private UserService userService;

    @Autowired
    private RpcTest2Service rpcTest2Service;

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

    @RequestMapping(value = "/test2", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<JsonResult> test2() {
        JsonResult jr = new JsonResult();
        try {
            User user = userService.getUser("13331", "22222");
            jr.setResult(user);
            return ResponseEntity.ok(jr);
        } catch (Exception e) {
            e.printStackTrace();
            jr.setStatus(-1);
            jr.setDesc("出现异常");
            return ResponseEntity.ok(jr);
        }
    }

    @RequestMapping(value = "/getBeans", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<JsonResult> getBeans() {
        JsonResult jr = new JsonResult();
        try {
            rpcTest2Service.getUser();
            jr.setResult(null);
            return ResponseEntity.ok(jr);
        } catch (Exception e) {
            e.printStackTrace();
            jr.setStatus(-1);
            jr.setDesc("出现异常");
            return ResponseEntity.ok(jr);
        }
    }

    @ApiOperation("添加用户")
    @RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<JsonResult> save(User user) {
        JsonResult jr = new JsonResult();
        try {
            userService.save(user);
            jr.setResult(null);
            return ResponseEntity.ok(jr);
        } catch (Exception e) {
            e.printStackTrace();
            jr.setStatus(-1);
            jr.setDesc("出现异常");
            return ResponseEntity.ok(jr);
        }
    }
}
