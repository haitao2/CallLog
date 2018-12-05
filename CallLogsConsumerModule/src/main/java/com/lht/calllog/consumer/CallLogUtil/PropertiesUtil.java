package com.lht.calllog.consumer.CallLogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载外部属性文件
 */
public class PropertiesUtil {
    public static Properties props;

    static {
        try {
            //加载外部属性文件
            InputStream in = ClassLoader.getSystemResourceAsStream("kafka.properties");
            props = new Properties();
            //加载
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProp(String key){
        return props.getProperty(key);
    }



}
