package com.lch.ssm.mvc.controller;

import com.lch.ssm.service.api.AdminService;

import com.lch.ssm.util.CrowdUtil;
import com.lch.ssm.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lch
 * @create 2020-07-16 15:11
 */
@Controller
public class TestController {

    @Autowired
    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test")
    public String test(Model model) {

//        List<Admin> adminList = adminService.getAll();
//        for (Admin admin:adminList) {
//            System.out.println(admin);
//        }
        model.addAttribute("adminList","adminList");
        int a = 10/0;
//        String a = null;
//        System.out.println(a.length());
        return "welcome";
    }


    @RequestMapping("/send/array")
    @ResponseBody
    public String receiveArr(@RequestParam("array[]") List<Integer> array) {
        for (int i:array) {
            System.out.println(i);
        }
        return "success";
    }

    @RequestMapping("/send/two")
    @ResponseBody
    public String receiveArr2(@RequestBody List<Integer> array) {
        for (Integer integer : array) {
            logger.info("number"+integer);
        }
        return "success";
    }

    @RequestMapping("/send/three")
    @ResponseBody
    public ResultEntity<List<Integer>> receiveArr3(@RequestBody List<Integer> array , HttpServletRequest request) {
        boolean reqType = CrowdUtil.judgeReqType(request);
        logger.info("ReqTYPE=" + reqType);
        for (Integer integer : array) {
            logger.info("number=="+integer);
        }
        String a = null;
        System.out.println(a.length());
        return ResultEntity.successWithData(array);
    }
}
