package com.wang.givemeaname.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.wang.givemeaname.window.ConsoleToolRunner;
import com.wang.givemeaname.window.MyProjectUtil;
import org.apache.commons.lang3.time.StopWatch;

import static com.wang.givemeaname.action.SendP8z.getP8zList;

public class SelectText extends AnAction {

    /**
     * 测试右键方法
     *
     * @param e
     * @return
     * @author wangshuai
     * @date 2022/6/20 18:09
     */
    @Override
    public void actionPerformed(AnActionEvent e) {

        // TODO: insert action logic here
        //获取当前编辑器对象
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        //获取选择的数据模型
        SelectionModel selectionModel = editor.getSelectionModel();
        //获取当前选择的文本
        String selectedText = selectionModel.getSelectedText();
        ResultDto p8zList = getP8zList(selectedText);
        runExecutor(e, p8zList);
    }

    public void runExecutor(AnActionEvent e, ResultDto p8zList) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }
        // 设置restart (再次执行这个action)
        Runnable rerun = () -> runExecutor(e, p8zList);
        // 设置stop
        Runnable stop = () -> MyProjectUtil.setRunning(project, "running", false);
        MyProjectUtil.setRunning(project, "running", true);
        ConsoleToolRunner executor = new ConsoleToolRunner(project, rerun, stop, p8zList);
        executor.run();
    }
}
