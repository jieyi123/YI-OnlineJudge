package com.pjieyi.yioj.judge.codesandbox.impl;

import com.pjieyi.yioj.judge.codesandbox.CodeSandbox;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.pjieyi.yioj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ExampleCodeSandboxTest {

    @Test
    void execute() {
        CodeSandbox codeSandbox=new ExampleCodeSandbox();
        ExecuteCodeRequest request=ExecuteCodeRequest.builder()
                .code("public static void main")
                .language(QuestionSubmitLanguageEnum.JAVA.getValue())
                .inputList(Arrays.asList("12 21","34 43"))
                .build();
        ExecuteCodeResponse execute = codeSandbox.execute(request);
        System.out.println(execute);
    }
}