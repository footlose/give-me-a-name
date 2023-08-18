package com.wang.givemeaname.window;

import com.intellij.execution.Executor;
import com.intellij.execution.ExecutorRegistry;

/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 10:26
 */
public class MyExecutorUtil {
    /**
     * 返回正在运行的 Executor
     *
     * @param id Executor id
     */
    public static Executor getRunExecutorInstance(String id) {
        return ExecutorRegistry.getInstance().getExecutorById(id);
    }
}
