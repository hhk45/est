package cn.est.controller;

import cn.est.dto.Result;
import cn.est.utils.RedisUtils;
import cn.est.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Api("这是Redis测试接口")
@Controller
@RequestMapping("/test")
public class TsetUsercontroller {



        @Autowired
        private RedisUtils redisUtils;
    @ApiOperation(value = "测试Redis",produces = "application/json",notes = "测试Redis")
    @ApiImplicitParam
        @RequestMapping(value = "/Redis",method = RequestMethod.POST)
        @ResponseBody
        public Result testRedis(){
            redisUtils.set("name","123456");
            Object value=redisUtils.get("name");
            return ResultUtils.returnDataSuccess(value);
        }

}
