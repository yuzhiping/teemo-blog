/*
 *Copyright  (C) 2016-2018 The hexsmith Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.github.hexsmith.blog.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 极验SDK
 * @author hexsmith
 * @version v1.0
 * @since 2018/5/29 21:23
 */
public class GeetestLib {

    private static final Logger logger = LoggerFactory.getLogger(GeetestLib.class);

    private final String verName = "4.0";
    private final String sdkLang = "java";

    private final String apiUrl = "http://api.geetest.com";

    private final String registerUrl = "/register.php";
    private final String validateUrl = "/validate.php";

    private final String json_format = "1";

    /**
     * 极验验证二次验证表单数据 challenge
     */
    public static final String fn_geetest_challenge = "geetest_challenge";

    /**
     * 极验验证二次验证表单数据 validate
     */
    public static final String fn_geetest_validate = "geetest_validate";

    /**
     * 极验验证二次验证表单数据 seccode
     */
    public static final String fn_geetest_seccode = "geetest_seccode";

    /**
     * 公钥
     */
    private String captchaId = "";

    /**
     * 私钥
     */
    private String privateKey = "";

    /**
     * 是否开启新的failback
     */
    private boolean newFailback = false;

    /**
     * 返回字符串
     */
    private String responseStr = "";

    /**
     * 极验验证API服务状态Session Key
     */
    public String gtServerStatusSessionKey = "gt_server_status";

    /**
     * 带参数构造函数
     *
     * @param captchaId 验证码ID
     * @param privateKey 私钥
     * @param newFailback failBack
     */
    public GeetestLib(String captchaId, String privateKey, boolean newFailback) {

        this.captchaId = captchaId;
        this.privateKey = privateKey;
        this.newFailback = newFailback;
    }

    /**
     * 获取本次验证初始化返回字符串
     *
     * @return 初始化结果
     */
    public String getResponseStr() {

        return responseStr;

    }

    public String getVersionInfo() {

        return verName;

    }

    /**
     * 预处理失败后的返回格式串
     *
     * @return
     */
    private String getFailPreProcessRes() {

        Long rnd1 = Math.round(Math.random() * 100);
        Long rnd2 = Math.round(Math.random() * 100);
        String md5Str1 = md5Encode(rnd1 + "");
        String md5Str2 = md5Encode(rnd2 + "");
        String challenge = md5Str1 + md5Str2.substring(0, 2);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", 0);
            jsonObject.put("gt", this.captchaId);
            jsonObject.put("challenge", challenge);
            jsonObject.put("new_captcha", this.newFailback);
        } catch (JSONException e) {
            logger.error("json dumps error.");
        }
        return jsonObject.toString();
    }

    /**
     * 预处理成功后的标准串
     *
     */
    private String getSuccessPreProcessRes(String challenge) {
        logger.info("challenge:{}",challenge);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", 1);
            jsonObject.put("gt", this.captchaId);
            jsonObject.put("challenge", challenge);
        } catch (JSONException e) {
            logger.error("json dumps error.");
        }
        return jsonObject.toString();
    }

    /**
     * 验证初始化预处理
     *
     * @return 1表示初始化成功，0表示初始化失败
     */
    public int preProcess(Map<String, String> data) {
        if (registerChallenge(data) != 1) {
            this.responseStr = this.getFailPreProcessRes();
            return 0;
        }
        return 1;
    }

    /**
     * 用captchaID进行注册，更新challenge
     *
     * @return 1表示注册成功，0表示注册失败
     */
    private int registerChallenge(Map<String, String>data) {
        try {
            String userId = data.get("user_id");
            String clientType = data.get("client_type");
            String ipAddress = data.get("ip_address");
            String getUrl = apiUrl + registerUrl + "?";
            String param = "gt=" + this.captchaId + "&json_format=" + this.json_format;
            if (userId != null){
                param = param + "&user_id=" + userId;
            }
            if (clientType != null){
                param = param + "&client_type=" + clientType;
            }
            if (ipAddress != null){
                param = param + "&ip_address=" + ipAddress;
            }
            logger.debug("GET_URL:{}{}", getUrl, param);
            String result_str = readContentFromGet(getUrl + param);
            if (result_str == "fail"){
                logger.error("gtServer register challenge failed.");
                return 0;
            }
            logger.info("result:{}", result_str);
            JSONObject jsonObject = new JSONObject(result_str);
            String return_challenge = jsonObject.getString("challenge");
            logger.info("return_challenge:{}", return_challenge);
            if (return_challenge.length() == 32) {
                this.responseStr = this.getSuccessPreProcessRes(this.md5Encode(return_challenge + this.privateKey));
                return 1;
            }
            else {
                logger.error("gtServer register challenge error");
                return 0;
            }
        } catch (Exception e) {
            logger.error("exception:register api, exception:{}", e.getMessage());
        }
        return 0;
    }

    /**
     * 判断一个表单对象值是否为空
     *
     * @param gtObj
     * @return
     */
    private boolean objIsEmpty(Object gtObj) {
        if (gtObj == null) {
            return true;
        }
        if (gtObj.toString().trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 检查客户端的请求是否合法,三个只要有一个为空，则判断不合法
     * @param challenge
     * @param validate
     * @param seccode
     * @return
     */
    private boolean resquestIsLegal(String challenge, String validate, String seccode) {
        if (objIsEmpty(challenge)) {
            return false;
        }
        if (objIsEmpty(validate)) {
            return false;
        }
        if (objIsEmpty(seccode)) {
            return false;
        }
        return true;
    }


    /**
     * 服务正常的情况下使用的验证方式,向gt-server进行二次验证,获取验证结果
     *
     * @param challenge
     * @param validate
     * @param seccode
     * @return 验证结果,1表示验证成功0表示验证失败
     */
    public int enhencedValidateRequest(String challenge, String validate, String seccode, HashMap<String, String> data) {
        if (!resquestIsLegal(challenge, validate, seccode)) {
            return 0;
        }
        logger.info("request legitimate");
        String userId = data.get("user_id");
        String clientType = data.get("client_type");
        String ipAddress = data.get("ip_address");
        String postUrl = this.apiUrl + this.validateUrl;
        String param = String.format("challenge=%s&validate=%s&seccode=%s&json_format=%s",
                challenge, validate, seccode, this.json_format);
        if (userId != null){
            param = param + "&user_id=" + userId;
        }
        if (clientType != null){
            param = param + "&client_type=" + clientType;
        }
        if (ipAddress != null){
            param = param + "&ip_address=" + ipAddress;
        }
        logger.info("param:{}",param);
        String response = "";
        try {
            if (validate.length() <= 0) {
                return 0;
            }
            if (!checkResultByPrivate(challenge, validate)) {
                return 0;
            }
            logger.info("checkResultByPrivate.");
            response = readContentFromPost(postUrl, param);
            logger.info("response:{}",response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_seccode = "";
        try {
            JSONObject return_map = new JSONObject(response);
            return_seccode = return_map.getString("seccode");
            logger.info("md5:{}",md5Encode(return_seccode));
            if (return_seccode.equals(md5Encode(seccode))) {
                return 1;
            } else {
                return 0;
            }
        } catch (JSONException e) {
            logger.error("json load error.");
            return 0;
        }

    }

    /**
     * failback使用的验证方式
     *
     * @param challenge
     * @param validate
     * @param seccode
     * @return 验证结果,1表示验证成功0表示验证失败
     */
    public int failbackValidateRequest(String challenge, String validate, String seccode) {
        logger.info("in failback validate.");
        if (!resquestIsLegal(challenge, validate, seccode)) {
            return 0;
        }
        logger.info("request legitimate.");
        return 1;
    }

    protected boolean checkResultByPrivate(String challenge, String validate) {
        String encodeStr = md5Encode(privateKey + "geetest" + challenge);
        return validate.equals(encodeStr);
    }

    /**
     * 发送GET请求，获取服务器返回结果
     * @param url
     * @return
     * @throws IOException
     */
    private String readContentFromGet(String url) throws IOException {
        URL getUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        // 设置连接主机超时（单位：毫秒）
        connection.setConnectTimeout(2000);
        // 设置从主机读取数据超时（单位：毫秒）
        connection.setReadTimeout(2000);
        // 建立与服务器的连接，并未发送数据
        connection.connect();
        if (connection.getResponseCode() == 200) {
            // 发送数据到服务器并使用Reader读取返回的数据
            StringBuffer sBuffer = new StringBuffer();
            InputStream inStream = null;
            byte[] buf = new byte[1024];
            inStream = connection.getInputStream();
            for (int n; (n = inStream.read(buf)) != -1;) {
                sBuffer.append(new String(buf, 0, n, "UTF-8"));
            }
            inStream.close();
            // 断开连接
            connection.disconnect();
            return sBuffer.toString();
        }
        else {
            return "fail";
        }
    }

    /**
     * 发送POST请求，获取服务器返回结果
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    private String readContentFromPost(String url, String data) throws IOException {
        logger.info(data);
        URL postUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();
        // 设置连接主机超时（单位：毫秒）
        connection.setConnectTimeout(2000);
        // 设置从主机读取数据超时（单位：毫秒）
        connection.setReadTimeout(2000);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 建立与服务器的连接，并未发送数据
        connection.connect();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
        outputStreamWriter.write(data);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        if (connection.getResponseCode() == 200) {
            // 发送数据到服务器并使用Reader读取返回的数据
            StringBuffer sBuffer = new StringBuffer();
            InputStream inStream = null;
            byte[] buf = new byte[1024];
            inStream = connection.getInputStream();
            for (int n; (n = inStream.read(buf)) != -1;) {
                sBuffer.append(new String(buf, 0, n, "UTF-8"));
            }
            inStream.close();
            // 断开连接
            connection.disconnect();
            return sBuffer.toString();
        }
        else {
            return "fail";
        }
    }

    /**
     * md5 加密
     *
     * @param plainText 加密字符串
     * @return 加密后的字符串
     */
    private String md5Encode(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }

                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

}
