package com.wang.givemeaname.window;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 10:22
 */
public class ConsoleToolStopAction extends AnAction implements DumbAware {

    /**
     * 停止 ToolWindow
     */
    private Runnable stop;


    public ConsoleToolStopAction(Runnable stop) {
        super("Stop", "Stop", AllIcons.Actions.Suspend);
        this.stop = stop;
    }

    /**
     * 执行动作
     */
    @Override
    public void actionPerformed(AnActionEvent e) {
        stop.run();
    }

    /**
     * 监听该Action, 每次焦点更换、窗口切换都会触发 update
     */
    @Override
    public void update(AnActionEvent e) {
        if (null != e && null != e.getProject()) {
            e.getPresentation().setEnabled(MyProjectUtil.getRunning(e.getProject(), "running"));
        }
    }
}
