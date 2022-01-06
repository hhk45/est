package cn.est.controller;

import cn.est.dto.Result;

import cn.est.service.ClassifyService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HomeController {
    @Resource
   private ClassifyService classifyService;
    @GetMapping("/api/home/classifyTree")
    public Result getTree(){
        return classifyService.getTree();
    }
}
