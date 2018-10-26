package com.wlb.forever.rpc.client.business.controller;

import com.wlb.forever.rpc.client.business.entity.User;
import com.wlb.forever.rpc.client.business.service.RpcTestService;
import com.wlb.forever.rpc.common.entity.JsonResult;
import com.wlb.forever.rpc.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private RpcTestService rpcTestService;

    @RequestMapping("/test")
    public ResponseEntity<JsonResult> test() {
        JsonResult jr = new JsonResult();
        try {
            User user=rpcTestService.getUser();
            jr.setResult(user);
            return ResponseEntity.ok(jr);
        }catch (Exception e){
            //e.printStackTrace();
            jr.setStatus(-1);
            jr.setDesc("出现异常");
            return ResponseEntity.ok(jr);
        }
    }

    @RequestMapping("/test2")
    public ResponseEntity<JsonResult> test2() {
        JsonResult jr = new JsonResult();
        try {
            User user=rpcTestService.getUser("11","22");
            jr.setResult(user);
            return ResponseEntity.ok(jr);
        }catch (Exception e){
            e.printStackTrace();
            jr.setStatus(-1);
            jr.setDesc("出现异常");
            return ResponseEntity.ok(jr);
        }
    }
}
