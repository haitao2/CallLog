package com.it18zhang.ssm.domain;

import com.it18zhang.ssm.util.CallLogUtil;

import java.text.ParseException;

/**
 * CallLog
 */
public class CallLog {
    private String caller ;
    private String callee ;
    private String callTime ;
    private String callDuration ;
    private Boolean flag;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getCallTime() {

        return CallLogUtil.formatDate(callTime);
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallDuration() {

        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }
}
