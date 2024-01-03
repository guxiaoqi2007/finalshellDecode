package com.geek.script.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 *
 * @author eric.yu 2021/11/24
 * @since 4.3.x1500
 */
public class RequestUtil {
    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    public static Response doPost(String body) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String uri="http://172.18.1.42:8895";
            HttpPost httppost = new HttpPost(uri);

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000)
                    .setSocketTimeout(200000).build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(new StringEntity(body, ContentType.create("application/json", "utf-8")));

            httppost.setHeader("Content-Type", "application/json");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseEntityStr = EntityUtils.toString(response.getEntity());
                    JSONObject res = JSON.parseObject(responseEntityStr);
                    JSONObject ret = (JSONObject) res.get("response");
                    JSONObject header = (JSONObject) ret.get("header");
                    String msg= (String) header.get("msg");
                    Integer code= (Integer) header.get("code");
                    String reponseId= (String) header.get("responseId");
                    Response result=new Response();
                    result.setMsg(msg);
                    result.setCode(code);
                    result.setResponseId(reponseId);
                    if(code==0){
                        JSONObject b = (JSONObject) ret.get("body");
                        result.setBody(b);
                    }
                    return result;
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
