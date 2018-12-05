package com.lht.calllog.consumer;


import com.lht.calllog.consumer.CallLogUtil.PropertiesUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Hbase数据库访问对象
 */
public class HbaseDao {
    // 格式化数据
    private DecimalFormat decimalFormat = new DecimalFormat();
    // 创建表
    private Table table = null;
    // 分区号
    private int partitions;
    private int flag;

    public HbaseDao() {
        try {
            // 创建数据库连接配置
            Configuration configuration = HBaseConfiguration.create();
            // 创建数据库连接对象
            Connection conn = ConnectionFactory.createConnection(configuration);
            // 创建tableNmae
            TableName tableName = TableName.valueOf(PropertiesUtil.getProp("table.name"));
            // 通过连接回去表描述对象
            table = conn.getTable(tableName);
            // 按照00进行格式化数据
            decimalFormat.applyPattern(PropertiesUtil.getProp("hashcode.pattern"));
            // 分区数
            partitions = Integer.parseInt(PropertiesUtil.getProp("partition.number"));
            // 标识主叫还是被叫
            flag = Integer.parseInt(PropertiesUtil.getProp("caller.flag"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将数据put到hbase中
     * 13754827364     余建堂  18733560618     陈鑫    2018-7-30  10:28:59     2187
     */

    public void put(String log) {
        if (log == null || log.length() == 0) {
            return;
        }

        //解析日志
        String[] arr = log.split("\t");
        System.out.println(arr.length);
        if (arr != null && arr.length == 6) {
            try {
                // 主叫
                String caller = arr[0];
                // 被叫
                String callee = arr[2];
                // 通话起始时间
                String time = arr[4]
                        .replace(" ", "")
                        .replace("-", "")
                        .replace(":", "");
                //通话总时长
                String duration = arr[5];
                // rowkey的设计
                String rowKey = getRowKey(getHashcode(caller, time), caller, time, flag, callee, duration);
                Put put = new Put(Bytes.toBytes(rowKey));
                //添加列
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("caller"), Bytes.toBytes(caller));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("callee"), Bytes.toBytes(callee));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("time"), Bytes.toBytes(time));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("duration"), Bytes.toBytes(duration));
                table.put(put);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHashcode(String caller, String time) {
        int len = caller.length();
        // 取出最后4位
        String last4 = caller.substring(len - 4);
        // 取出年份和月份
        String yearMonth = time.substring(0, 6);
        int hascode = (Integer.parseInt(last4) ^ Integer.parseInt(yearMonth) % partitions);
        //将不满2位的数字进行格式化  1 -> 01  9->09
        return decimalFormat.format(hascode);
    }

    public String getRowKey(String hash, String caller, String time, int flag, String callee, String duration) {
        return hash + "," + caller + ","  + time + "," + flag + "," + callee + "," + duration;
    }

}
