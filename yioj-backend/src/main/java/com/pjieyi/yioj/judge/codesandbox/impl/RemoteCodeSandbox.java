package com.pjieyi.yioj.judge.codesandbox.impl;

import com.pjieyi.yioj.judge.codesandbox.CodeSandbox;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.pjieyi.yioj.model.dto.questionsubmit.JudgeInfo;
import com.pjieyi.yioj.model.enums.QuestionSubmitStatusEnum;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
