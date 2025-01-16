package com.pjieyi.yioj.judge;

import com.pjieyi.yioj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 执行判题
     * @param questionSubmitId 题目提交 id
     * @return 题目提交信息
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
