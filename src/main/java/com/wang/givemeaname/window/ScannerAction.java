package com.wang.givemeaname.window;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sun.istack.NotNull;

/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 10:23
 */
public class ScannerAction extends AnAction {
    private ConsoleView consoleView;

    public ScannerAction(ConsoleView consoleView) {
        super("Give me a name", "Give me a name", AllIcons.Actions.Upload);
        this.consoleView = consoleView;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (null != consoleView) {
            consoleView.print("新输出一条日志.", ConsoleViewContentType.NORMAL_OUTPUT);
        }
    }
}
