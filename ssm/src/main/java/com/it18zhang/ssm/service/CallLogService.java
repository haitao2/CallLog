package com.it18zhang.ssm.service;

import com.it18zhang.ssm.domain.CallLog;
import com.it18zhang.ssm.domain.CallLogRange;

import java.util.List;

/**
 *
 */
public interface CallLogService {
    public List<CallLog> findAll();

    /**
     * 数据查询范围管理
     * @param list
     * @return
     */

    List<CallLog> findCallLogs(String caller, List<CallLogRange> list);
}
