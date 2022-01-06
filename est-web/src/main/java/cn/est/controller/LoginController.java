package cn.est.controller;

import cn.est.constants.Constants;
import cn.est.dto.Result;
import cn.est.po.Users;
import cn.est.service.UsersService;
import cn.est.utils.RedisUtils;
import cn.est.utils.ResultUtils;
import cn.est.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
public class LoginController {

   @Autowired
   private RedisUtils redisUtils;

    @Autowired
    private UsersService usersService;


    @RequestMapping("/login/sms")
    public Result LoginSms(String phone,String sms){
       //1.校验phone， sms
        if(StringUtil.isBlank(phone)){
            return ResultUtils.returnFail("手机号不符合要求");
        }
        if(StringUtil.isBlank(sms)){
            return ResultUtils.returnFail("验证码不能为空");
        }

       //2.验证验证码是否正确
       //2.1从redis获取数据
        String codeKey=StringUtil.formatKeyWithPrefix(Constants.RedisKey.PREFIX_SMS, phone, Constants.Sms.SmsType.REGIST_OR_LOGIN + "");
        String code=redisUtils.getValue(codeKey);
        if(StringUtil.isBlank(code)){
            return ResultUtils.returnFail("验证码不存在或者已过期，请重新发送");
        }
        //2.2判断是否正确
        if(!code.equals(sms)){
            return ResultUtils.returnFail("验证码不正确");
        }

       //3.登录注册
        //3.1查询是否存在
        Map param=new HashMap();
        param.put("phone",phone);
        List<Users> userList= usersService.getUsersListByMap(param);
        Users user=new Users();
        if (userList!=null&&userList.size()>0){
            user=userList.get(0);
        }else {//3.3不存在，提醒注册，注册
            user=new Users();
            user.setAccount(phone);

            user.setCreatdTime(new Date());
            Integer integer= usersService.qdtxAddUsers(user);

        }
        String token=StringUtil.createToken();
        redisUtils.set(token, JSONObject.toJSONString(user));
       return ResultUtils.returnDataSuccess(StringUtil.createSimpleMap("token",token));
   }
   @RequestMapping("/info")
   public Result info(HttpServletRequest request){
        String token=request.getHeader("token");
        Users users=redisUtils.getJson(token,Users.class);
        return ResultUtils.returnDataSuccess(users);
   }

}
