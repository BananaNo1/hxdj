package com.leis.hxds.common.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.leis.hxds.common.exception.HxdsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("MicroAppUtil")
public class MicroAppUtil {

    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    public String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject jsonObject = JSONUtil.parseObj(response);
        String openId = jsonObject.getStr("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("临时登录凭证错误");
        }
        return openId;
    }

    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String, Object> map = new HashMap<>();
        map.put("grant_type", "client_credential");
        map.put("appid", appId);
        map.put("secret", appSecret);
        String response = HttpUtil.get(url, map);
        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (jsonObject.containsKey("access_token")) {
            String access_token = jsonObject.getStr("access_token");
            return access_token;
        } else {
            throw new HxdsException(jsonObject.getStr("errmsg"));
        }
    }
}
