package com.pjieyi.yioj.judge.codesandbox.impl;

import com.pjieyi.yioj.judge.codesandbox.CodeSandbox;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 示例代码沙箱 (跑通业务流程)
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        return null;
    }
}
