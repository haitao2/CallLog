package com.it18zhang.ssm.web.controller;

import com.it18zhang.ssm.domain.CallLog;
import com.it18zhang.ssm.domain.CallLogRange;
import com.it18zhang.ssm.service.CallLogService;
import com.it18zhang.ssm.util.CallLogUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */
@Controller
public class CallLogController {

    @Resource(name="callLogService")
    private CallLogService cs ;

    @RequestMapping("/callLog/findAll")
    public String findAll(Model m){
        List<CallLog> list = cs.findAll();
        m.addAttribute("callLogs",list);
            return "callLog/callLogList" ;
    }

    // 进入查询通话记录的页面
    @RequestMapping("/callLog/toFindCallLogPage")
    public String toFindCallLogPage(){
        return "callLog/findCallLog";
    }

    @RequestMapping(value = "/callLog/findCallLog",method = RequestMethod.POST)
    public String findCallLog(Model m,@RequestParam("caller") String caller,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) throws ParseException {
        List<CallLogRange> list = CallLogUtil.getCallLogRanges(startTime, endTime);
        List<CallLog> callLogList  = cs.findCallLogs(caller,list);
        m.addAttribute("callLogs",callLogList);
        return "callLog/callLogList";
    }

}
