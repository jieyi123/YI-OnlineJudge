package com.pjieyi.yioj.judge;

import cn.hutool.json.JSONUtil;
import com.pjieyi.yioj.common.ErrorCode;
import com.pjieyi.yioj.exception.BusinessException;
import com.pjieyi.yioj.judge.codesandbox.CodeSandbox;
import com.pjieyi.yioj.judge.codesandbox.CodeSandboxFactory;
import com.pjieyi.yioj.judge.codesandbox.CodeSandboxProxy;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.pjieyi.yioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.pjieyi.yioj.model.dto.question.JudgeCase;
import com.pjieyi.yioj.model.dto.question.JudgeConfig;
import com.pjieyi.yioj.model.dto.questionsubmit.JudgeInfo;
import com.pjieyi.yioj.model.entity.Question;
import com.pjieyi.yioj.model.entity.QuestionSubmit;
import com.pjieyi.yioj.model.enums.JudgeInfoMessageEnum;
import com.pjieyi.yioj.model.enums.QuestionSubmitStatusEnum;
import com.pjieyi.yioj.service.QuestionService;
import com.pjieyi.yioj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Value("${codesandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        Integer status = questionSubmit.getStatus();
        Long questionId = questionSubmit.getQuestionId();
        //2.如果题目提交状态不为等待中，就不用重复执行了
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        //3.更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        boolean update = questionSubmitService.updateById(updateQuestionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目信息不存在");
        }
        //获取输入以及输出用例
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        //4.调用沙箱，获取到执行结果
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy proxy = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeResponse execute = proxy.execute(executeCodeRequest);
        List<String> outputResponseList = execute.getOutPutList();
        String message = execute.getMessage();
        Integer judgeStatus = execute.getStatus();
        JudgeInfo judgeInfo = execute.getJudgeInfo();
        //5.根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeInfoMessageEnum judgeInfoMessageEnum=JudgeInfoMessageEnum.WAITING;
        if (inputList.size() != outputResponseList.size()) {
            judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
        }
        //循环判断判题结果是否与之前设置的结果一致
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputResponseList.get(i))) {
                judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
            }
        }
        String judgeConfig = question.getJudgeConfig();
        JudgeConfig judgeConfigBean = JSONUtil.toBean(judgeConfig, JudgeConfig.class);

        Long timeLimit = judgeConfigBean.getTimeLimit();
        Long memoryLimit = judgeConfigBean.getMemoryLimit();
        Long stackLimit = judgeConfigBean.getStackLimit();
        if (judgeInfo.getTime()>timeLimit){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
        }
        if (judgeInfo.getMemory()>memoryLimit){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
        }
        judgeInfoMessageEnum=JudgeInfoMessageEnum.ACCEPTED;
        QuestionSubmit finalQuestionSubmit = new QuestionSubmit();
        finalQuestionSubmit.setId(questionSubmitId);
        finalQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        finalQuestionSubmit.setStatus(judgeStatus);
        boolean questionSubmitInfo = questionSubmitService.updateById(finalQuestionSubmit);
        if (!questionSubmitInfo) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题信息更新错误");
        }
        return finalQuestionSubmit;
    }
}
