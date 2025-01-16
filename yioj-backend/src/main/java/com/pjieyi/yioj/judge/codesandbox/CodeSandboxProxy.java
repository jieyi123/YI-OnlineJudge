package com.pjieyi.yioj.judge.codesandbox;

import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理模式：沙箱增强(调用前输出日志，调用后输出响应结果，便于分析)
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox{

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox=codeSandbox;
    }
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息:{}",executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.execute(executeCodeRequest);
        log.info("代码沙箱响应结果:{}",executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
