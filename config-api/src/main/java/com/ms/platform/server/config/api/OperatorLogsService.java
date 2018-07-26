package com.ms.platform.server.config.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.model.OperatorLogs;
import com.ms.platform.server.config.request.OperatorLogsPageRequest;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public interface OperatorLogsService {

    OperatorLogs getDetail(Long id);

    PageCustom<OperatorLogs> pages(OperatorLogsPageRequest pageRequest) throws BusinessException;

    void addLogs(OperatorLogs operatorLogs);

}
