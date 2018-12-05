package com.it18zhang.ssm.test;

import com.it18zhang.ssm.util.CharUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 */
public class TestZH {
    private DecimalFormat df = new DecimalFormat();
    private Table table = null;
    private int partitions ;
    private String flag;
    @Test
    public void test1(){
        int nums = 0 ;
        System.out.println(0x9fa5 - 0x4e00);
//        for(int i = 0x4e00 ; i <= 0x9fa5 ;  i ++){
//            char c = (char)i ;
//            System.out.print(c);
//            nums ++ ;
//            if(nums > 51){
//                System.out.println();
//                nums = 0 ;
//            }
//        }
    }

    @Test
    public void containZH(){
        String str = "abc!!!" ;
        int count = str.length();
        for(int i = 0 ; i< count ; i ++){
            char c = str.charAt(i);
            int code = (int)c;
            if(code >= 0x4e00 && code <= 0x9fa5){
                System.out.println("含");
                return ;
            }
        }
    }
    @Test
    public void test2(){
        for(;;){
            System.out.println(CharUtil.getName3zh());

        }
    }
    public void HbaseDao(){
        try {
            Configuration configuration = HBaseConfiguration.create();
            Connection connection = ConnectionFactory.createConnection(configuration);
            TableName name = TableName.valueOf("ns1:calllogs");
            table = connection.getTable(name);

            partitions = Integer.parseInt(PropertiesUtil.getProp("partition.number"));
            df.applyPattern(PropertiesUtil.getProp("hashcode.pattern"));
            flag = PropertiesUtil.getProp("caller.flag");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
