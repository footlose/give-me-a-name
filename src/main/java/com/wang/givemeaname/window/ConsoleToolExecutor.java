package com.wang.givemeaname.window;

import com.intellij.execution.Executor;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 10:15
 */
public class ConsoleToolExecutor extends Executor {

    public static final String PLUGIN_ID = "Give me a name Plugin ID";

    public static final String TOOL_WINDOW_ID = "Give me a name Scanner";

    public static final String CONTEXT_ACTION_ID = "custom context action id";

    @NotNull
    @Override
    public String getId() {
        return PLUGIN_ID;
    }

    @Override
    public String getToolWindowId() {
        return TOOL_WINDOW_ID;
    }

    @Override
    public Icon getToolWindowIcon() {
        return AllIcons.Debugger.Console;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return AllIcons.Debugger.Console;
    }

    @Override
    public Icon getDisabledIcon() {
        return null;
    }

    @Override
    public String getDescription() {
        return TOOL_WINDOW_ID;
    }

    @NotNull
    @Override
    public String getActionName() {
        return TOOL_WINDOW_ID;
    }

    @NotNull
    @Override
    public String getStartActionText() {
        return TOOL_WINDOW_ID;
    }

    @Override
    public String getContextActionId() {
        return CONTEXT_ACTION_ID;
    }

    @Override
    public String getHelpId() {
        return TOOL_WINDOW_ID;
    }

}
