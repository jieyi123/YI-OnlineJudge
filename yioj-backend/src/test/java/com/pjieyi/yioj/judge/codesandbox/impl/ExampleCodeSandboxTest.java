package com.pjieyi.yioj.judge.codesandbox.impl;

import com.pjieyi.yioj.judge.codesandbox.CodeSandbox;
import com.pjieyi.yioj.judge.codesandbox.CodeSandboxFactory;
import com.pjieyi.yioj.judge.codesandbox.CodeSandboxProxy;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.pjieyi.yioj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Scanner;
@SpringBootTest
class ExampleCodeSandboxTest {


    @Value("${codesandbox.type:example}")  //codesandbox.type不存在就使用example默认值
    private String type;

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

    //通过配置文件执行代码沙箱
    @Test
    void executeCodeByValue(){
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        ExecuteCodeResponse execute = codeSandbox.execute(null);
        System.out.println(type);
    }

    //根据type执行不同的代码环境
    public static void main(String[] args) {
        Scanner ac=new Scanner(System.in);
        while (ac.hasNext()){
            String type=ac.next();
            CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
            ExecuteCodeResponse execute = codeSandbox.execute(null);
        }
    }

    @Test
    void testCodeSandboxProxy(){
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy proxy=new CodeSandboxProxy(codeSandbox);
        ExecuteCodeRequest request=ExecuteCodeRequest.builder()
                .code("public static void main")
                .language(QuestionSubmitLanguageEnum.JAVA.getValue())
                .inputList(Arrays.asList("12 21","34 43"))
                .build();
        ExecuteCodeResponse execute = proxy.execute(request);
    }


}