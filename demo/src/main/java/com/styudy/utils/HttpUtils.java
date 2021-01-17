package com.styudy.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName HttpUtils
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/7 9:41
 **/
public class HttpUtils {
    private PoolingHttpClientConnectionManager cm;



    public HttpUtils(){
         cm = new PoolingHttpClientConnectionManager();
//         设置最大连接数
        cm.setMaxTotal(100);
//        设置每一个主机得最大连接数
        cm.setDefaultMaxPerRoute(10);
    }
    /**
     * @Author xu
     * @Description //TODO http请求封装
     * @Date 9:51 2021/1/7
     * @Param [url]
     * @return java.lang.String
     **/
    public String doGetHtml(String url){
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse execute=null;
        try {
            execute = httpclient.execute(httpGet);
            if(execute.getStatusLine().getStatusCode()==200){
                HttpEntity entity = execute.getEntity();
                if(entity !=null){
                    String s = EntityUtils.toString(entity);
                    return s;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(execute !=null){
                try {
                    execute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public String updateImage(String url){
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse execute=null;
        try {
            execute = httpclient.execute(httpGet);
            if(execute.getStatusLine().getStatusCode()==200){
                HttpEntity entity = execute.getEntity();
                if(entity !=null){
//                    图片后缀
                    String substring = url.substring(url.lastIndexOf("."));
//                    重命名
                    String filename = UUID.randomUUID().toString() + substring;
                    OutputStream outputStream = new FileOutputStream(new File("D:\\FTP\\image\\"+filename));
                    entity.writeTo(outputStream);
                    return filename;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(execute !=null){
                try {
                    execute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public RequestConfig getConfig(){
       return RequestConfig.custom()
               //创建连接得最长时间
                .setConnectTimeout(1000)
               // 获取连接得最长时间
                .setConnectionRequestTimeout(1000)
               // 数据传输得最长时间
                .setSocketTimeout(10000)
                .build();
    }


}
