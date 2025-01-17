package com.pjieyi.yioj.judge.strategy;

import com.pjieyi.yioj.model.dto.question.JudgeCase;
import com.pjieyi.yioj.model.dto.questionsubmit.JudgeInfo;
import com.pjieyi.yioj.model.entity.Question;
import com.pjieyi.yioj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {


    /**
     * 题目输入信息
     */
    private List<String> inputList;

    /**
     * 判题后的输出信息
     */
    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private JudgeInfo judgeInfo;

    private Question question;

    private QuestionSubmit questionSubmit;
}
