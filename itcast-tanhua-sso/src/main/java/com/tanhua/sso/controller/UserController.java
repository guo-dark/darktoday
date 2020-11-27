package com.tanhua.sso.controller;
import com.tanhua.sso.service.SmsService;
import com.tanhua.sso.service.UserService;
import com.tanhua.sso.utils.ValidateCodeUtils;
import com.tanhua.sso.vo.ErrorResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @PostMapping("loginVerification")
    public ResponseEntity<Object> login(@RequestBody Map<String,String> map){
        String phone = map.get("phone");
        String verificationCode = map.get("verificationCode");
        String token= this.userService.login(phone,verificationCode);
        System.out.println(token);
        Map<String,Object> result = new HashMap<>();
        if(StringUtils.isNotEmpty(token)){
            String[] split = token.split("\\|");
            /*stringutils 与 split类的使用 */
            Boolean isNew = Boolean.valueOf(split[0]);
            String tokenStr = split[1];
            result.put("isNew", isNew);
            result.put("token", tokenStr);
            /*返回 */
            System.out.println(tokenStr);
            System.out.println(result
            );
            return ResponseEntity.ok(result);
        }
        // 登录失败，验证码错误
        /*返回一个带有信息的 类  */
        ErrorResult errorResult = ErrorResult.builder().errCode("000000").errMsg("验证码错误").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }




    @PostMapping("login")
    public ResponseEntity<Object> sendCode(@RequestBody Map<String,Object> params){
        ErrorResult.ErrorResultBuilder resultBuilder = ErrorResult.builder().errCode("000000").errMsg("发送短信验证码失败");
        try {
            String phone = String.valueOf(params.get("phone"));
            Map<String, Object> msg = smsService.sendCheckCode(phone);
            Integer code = ((Integer) msg.get("code")).intValue();
            if (code == 3) {
                return ResponseEntity.ok(null);
            } else if (code == 1){
                resultBuilder.errCode("000001").errMsg(msg.get("msg").toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        /*返回一个状态 包含  */
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBuilder.build());
    }
}
