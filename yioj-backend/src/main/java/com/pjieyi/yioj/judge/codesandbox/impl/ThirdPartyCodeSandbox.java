package com.pjieyi.yioj.judge.codesandbox.impl;

import com.pjieyi.yioj.judge.codesandbox.CodeSandbox;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
