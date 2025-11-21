package com.dincher.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;

import java.nio.charset.Charset;
import java.util.HashMap;

public class FileWatchUtils {

    public static void main(String[] args) {
        String url = "http://192.168.1.241:15672/api/trace-files/node/rabbit%40localhost/outQueueConsumer.log";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("authorization", generateCode());
        String body = HttpUtil.createGet(url).addHeaders(paramMap).execute().body();
        // 当无法识别页面编码的时候，可以自定义请求页面的编码
        System.out.println(body);
/*        //这里只监听文件或目录的修改事件
        WatchMonitor.createAll(file, new SimpleWatcher() {
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Console.log("{}->{}->{}->{}->{}", currentPath, event.context(),event.count(),event.kind());
            }
        }).start();*/
    }

    /**
     * 生成base64 用于登录认证
     *
     * @return
     */
    public static String generateCode() {
        String auth = "test" + ":" + "123456";
        String encode = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encode);
        System.out.println("rabbitmq登录认证成功返回：" + authHeader);
        return authHeader;
    }

}
