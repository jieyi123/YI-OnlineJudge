package com.pjieyi.yioj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeRequest {

    /**
     * 输入信息
     */
    private List<String> inputList;

    /**
     * 代码信息
     */
    private String code;

    /**
     * 语言
     */
    private String language;
}
