package com.pjieyi.yioj.judge.codesandbox;

import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest 代码执行请求
     * @return 代码执行响应
     */
    ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest);
}
