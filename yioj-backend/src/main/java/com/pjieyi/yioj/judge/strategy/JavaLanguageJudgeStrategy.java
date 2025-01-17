package com.pjieyi.yioj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.pjieyi.yioj.model.dto.question.JudgeCase;
import com.pjieyi.yioj.model.dto.question.JudgeConfig;
import com.pjieyi.yioj.model.dto.questionsubmit.JudgeInfo;
import com.pjieyi.yioj.model.entity.Question;
import com.pjieyi.yioj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * java程序的判题策略
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Question question = judgeContext.getQuestion();

        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        if (inputList.size() != outputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        //循环判断判题结果是否与之前设置的结果一致
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfo;
            }
        }
        String judgeConfig = question.getJudgeConfig();
        JudgeConfig judgeConfigBean = JSONUtil.toBean(judgeConfig, JudgeConfig.class);
        //题目时间限制
        Long timeLimit = judgeConfigBean.getTimeLimit();
        Long memoryLimit = judgeConfigBean.getMemoryLimit();
        // Java 程序本身需要额外执行 10 秒钟
        long JAVA_PROGRAM_TIME_COST = 10L;
        if ((judgeInfo.getTime()+JAVA_PROGRAM_TIME_COST) > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        if (judgeInfo.getMemory() > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfo;
    }
}
