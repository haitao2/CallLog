package com.it18zhang.ssm.test;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/11.
 */
public class PropertiesUtil {
    public static Properties pros ;
    static{
        try {
            //加载外部属性文件
            InputStream in = ClassLoader.getSystemResourceAsStream("kafka.properties");
            pros = new Properties();
            pros.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProp(String key){
        return pros.getProperty(key) ;
    }
}
