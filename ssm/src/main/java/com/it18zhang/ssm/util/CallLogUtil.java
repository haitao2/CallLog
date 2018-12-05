package com.it18zhang.ssm.util;

import com.it18zhang.ssm.domain.CallLogRange;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CallLogUtil {

    private static DecimalFormat decimalFormat = new DecimalFormat("00");

    /**
     * 获取hasb值，也就是rowkey的第一个字段
     */
    public static String getHashcode(String caller, String time,int partitions) {
        int len = caller.length();
        // 取出最后4位
        String last4 = caller.substring(len - 4);
        // 取出年份和月份
        String yearMonth = time.substring(0, 6);
        int hascode = (Integer.parseInt(last4) ^ Integer.parseInt(yearMonth) % partitions);
        //将不满2位的数字进行格式化  1 -> 01  9->09
        return decimalFormat.format(hascode);
    }

    /**
     * 根据起始时间获取rowkey的值
     */
    public static String getStartRowKey(String caller,String startTime,int partitions){
        String hash = getHashcode(caller, startTime, partitions);
        return hash+","+caller+","+startTime;
    }

    /**
     * 根据结束时间获取rowkey的值
     */
    public static String getStopRowKey(String caller,String startTime,String endTime,int partitions){
        String hash = getHashcode(caller, startTime, partitions);
        return hash+","+caller+","+endTime;
    }

    /**
     * 根据所给的起始时间和结束时间来获取一个list变化范围。
     */
    public static List<CallLogRange> getCallLogRanges(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfYM = new SimpleDateFormat("yyyyMM");
        DecimalFormat df00 = new DecimalFormat("00");

        //
        List<CallLogRange> list = new ArrayList<CallLogRange>();
        //字符串时间
        String startPrefix = startTime.substring(0, 6);


        String endPrefix = endTime.substring(0, 6);
        int endDay = Integer.parseInt(endTime.substring(6, 8));
        //结束点
        String endPoint = endPrefix + df00.format(endDay + 1);

        //日历对象
        Calendar c = Calendar.getInstance();

        //同年月
        if (startPrefix.equals(endPrefix)) {
            CallLogRange range = new CallLogRange();
            range.setStartPoint(startTime);          //设置起始点

            range.setEndPoint(endPoint);            //设置结束点
            list.add(range);
        } else {
            //1.起始月
            CallLogRange range = new CallLogRange();
            range.setStartPoint(startTime);

            //设置日历的时间对象
            c.setTime(sdfYMD.parse(startTime));
            c.add(Calendar.MONTH, 1);
            range.setEndPoint(sdfYM.format(c.getTime()));
            list.add(range);

            //是否是最后一月
            while (true) {
                //到了结束月份
                if (endTime.startsWith(sdfYM.format(c.getTime()))) {
                    range = new CallLogRange();
                    range.setStartPoint(sdfYM.format(c.getTime()));
                    range.setEndPoint(endPoint);
                    list.add(range);
                    break;
                } else {
                    range = new CallLogRange();
                    //起始时间
                    range.setStartPoint(sdfYM.format(c.getTime()));

                    //增加月份
                    c.add(Calendar.MONTH, 1);
                    range.setEndPoint(sdfYM.format(c.getTime()));
                    list.add(range);
                }
            }
        }
        return list;
    }
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat sdfFriend = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * 格式化时间
     */
    public static String formatDate(String date) {
        try {
            return sdfFriend.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
