package com.pjieyi.yioj.judge;

import com.pjieyi.yioj.judge.strategy.DefaultJudgeStrategy;
import com.pjieyi.yioj.judge.strategy.JavaLanguageJudgeStrategy;
import com.pjieyi.yioj.judge.strategy.JudgeContext;
import com.pjieyi.yioj.judge.strategy.JudgeStrategy;
import com.pjieyi.yioj.model.dto.questionsubmit.JudgeInfo;
import com.pjieyi.yioj.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 判题管理(简化调用)
 */
@Service
public class JudgeManager {

    JudgeInfo doJudge(JudgeContext judgeContext){
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)){
            judgeStrategy=new JavaLanguageJudgeStrategy();
        }
        return  judgeStrategy.doJudge(judgeContext);
    }
}
