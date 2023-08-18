package com.wang.givemeaname.window;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;

/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 10:21
 */
public class ConsoleToolRerunAction extends AnAction implements DumbAware {


    /**
     * 重启 ToolWindow
     */
    private Runnable rerun;

    public ConsoleToolRerunAction(Runnable rerun) {
        super("Rerun", "Rerun", AllIcons.Actions.Restart);
        this.rerun = rerun;
    }

    /**
     * 执行动作
     */
    @Override
    public void actionPerformed(AnActionEvent e) {
        rerun.run();
    }

    /**
     * 监听该Action, 每次焦点更换、窗口切换都会触发 update
     */
    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setVisible(rerun != null);
    }
}
