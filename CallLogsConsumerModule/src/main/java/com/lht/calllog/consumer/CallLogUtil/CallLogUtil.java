package com.lht.calllog.consumer.CallLogUtil;

import com.lht.domain.CallLogRange;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CallLogUtil {

    public String getHashcode(String caller,String callTime,int partitions){
        DecimalFormat decimalFormat = new DecimalFormat();
        int len = caller.length();
        // 取出最后4位
        String last4 = caller.substring(len - 4);
        // 取出年份和月份
        String yearMonth = callTime.substring(0, 6);
        int hascode = (Integer.parseInt(last4) ^ Integer.parseInt(yearMonth) % partitions);
        //将不满2位的数字进行格式化  1 -> 01  9->09
        return decimalFormat.format(hascode);
    }
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
}
