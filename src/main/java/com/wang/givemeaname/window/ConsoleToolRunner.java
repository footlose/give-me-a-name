package com.wang.givemeaname.window;

import com.alibaba.fastjson.JSON;
import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunnerLayoutUi;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.ui.content.Content;
import com.sun.istack.NotNull;
import com.wang.givemeaname.action.ResultDto;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 10:24
 */
public class ConsoleToolRunner {
    /**
     * Project
     */
    private Project project = null;

    /**
     * 视图面板
     */
    private ConsoleView consoleView = null;
    private ConsoleView consoleViewZy = null;
    private ConsoleView consoleViewMj = null;

    private ConsoleView consoleViewClass = null;

    private ConsoleView consoleViewBl = null;

    private ConsoleView consoleViewVo = null;

    private ConsoleView consoleViewDTO = null;


    /**
     * 重启/运行 ToolWindow
     */
    private Runnable rerun;

    private ResultDto p8zList;

    /**
     * 停止 ToolWindow
     */
    private final Runnable stop;


    public ConsoleToolRunner(@NotNull Project project, Runnable rerun, Runnable stop, ResultDto p8zList) {
        this.p8zList = p8zList;
        this.project = project;
        this.consoleView = createConsoleView(project, p8zList, "");
        this.consoleViewZy = createConsoleView(project, p8zList, "zy");
        this.consoleViewMj = createConsoleView(project, p8zList, "mj");

        this.consoleViewClass = createConsoleView(project, p8zList, "class");

        this.consoleViewBl = createConsoleView(project, p8zList, "bl");

        this.consoleViewVo = createConsoleView(project, p8zList, "vo");

        this.consoleViewDTO = createConsoleView(project, p8zList, "dto");
        this.rerun = rerun;
        this.stop = stop;
    }

    private ConsoleView createConsoleView(Project project, ResultDto p8zList, String type) {
        TextConsoleBuilder consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        ConsoleView console = consoleBuilder.getConsole();
        if (StringUtils.isBlank(type)) {
            console.print(JSON.toJSONString(p8zList), ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
        if ("zy".equals(type)) {
            console.print(JSON.toJSONString(p8zList.getColBasic()), ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
        if ("mj".equals(type)) {
            console.print(JSON.toJSONString(p8zList.getEnumBasic()), ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
        if ("class".equals(type)) {
            console.print(JSON.toJSONString(p8zList.getColBasic()), ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
        if ("bl".equals(type)) {
            console.print(JSON.toJSONString(p8zList.getVarBasic()), ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
        if ("vo".equals(type)) {
            console.print(JSON.toJSONString(p8zList.getVarBasic()), ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
        if ("dto".equals(type)) {
            console.print(JSON.toJSONString(p8zList.getColBasic()), ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
        return console;
    }

    public void run() {
        // 返回定义的 Executor
        Executor executor = MyExecutorUtil.getRunExecutorInstance(ConsoleToolExecutor.PLUGIN_ID);
        if (executor == null) {
            return;
        }

        // 创建 RunnerLayoutUi
        final RunnerLayoutUi.Factory factory = RunnerLayoutUi.Factory.getInstance(project);
        RunnerLayoutUi layoutUi = factory.create("id", "title", "session name", project);

        // 创建 panel
        final JPanel panel = new JPanel();
        panel.add(consoleView.getComponent(), BorderLayout.CENTER);

        // 创建描述信息
        RunContentDescriptor descriptor = new RunContentDescriptor(new RunProfile() {
            @Nullable
            @Override
            public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) {
                return null;
            }

            @NotNull
            @Override
            public String getName() {
                return "Give me a name";
            }

            @Nullable
            @Override
            public Icon getIcon() {
                return AllIcons.Debugger.Console;
            }
        }, new DefaultExecutionResult(), layoutUi);
        descriptor.setExecutionId(System.nanoTime());

        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//获得系统剪贴板
        // 创建 panel
        final JPanel panelZy = new JPanel();

        String colBasic = p8zList.getColBasic();
        String enumBasic = p8zList.getEnumBasic();
        String colWebWord = p8zList.getColWebWord();
        String varWebWord = p8zList.getVarWebWord();
        String varBasic = p8zList.getVarBasic();
        List<String> words = new ArrayList<>();
        words.add(colBasic);
        words.add(enumBasic);
        words.add(colWebWord);
        words.add(varWebWord);
        words.add(varBasic);
        boolean have = false;
        for (String s : words) {
            if (StringUtils.isNotBlank(s)) {
                List<String> collect = Arrays.stream(s.split(",")).collect(Collectors.toList());
                if (!collect.isEmpty()) {
                    for (String k : collect) {
                        if (StringUtils.isNotBlank(k)) {
                            have = true;
                            JButton jButtons = new JButton(k);
                            jButtons.addActionListener(e -> {
                                //设置点击事件 点击时复制到剪切板
                                String text = jButtons.getText();
                                Transferable canonicalNameSelection = new StringSelection(text);
                                systemClipboard.setContents(canonicalNameSelection, null);
                            });
                            panelZy.add(jButtons, BorderLayout.NORTH);
                        }
                    }
                }
            }
        }
        if (!have) {
            TextConsoleBuilder consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
            ConsoleView console = consoleBuilder.getConsole();
            console.print("未查询到可用的名称", ConsoleViewContentType.LOG_INFO_OUTPUT);
            panelZy.add(console.getComponent(), BorderLayout.NORTH);
        }

        final Content contentZy = layoutUi.createContent("zy", panelZy, "翻译", AllIcons.Debugger.Console, panelZy);
        contentZy.setCloseable(true);
        layoutUi.addContent(contentZy);
        // 新增左边工具条
        layoutUi.getOptions().setLeftToolbar(createActionToolbar(consoleView), "RunnerToolbar");
        ExecutionManager.getInstance(project).getContentManager().showRunContent(executor, descriptor);
    }

    private ActionGroup createActionToolbar(ConsoleView consoleView) {
        final DefaultActionGroup actionGroup = new DefaultActionGroup();
        // ConsoleView 按钮: warp
        actionGroup.add(consoleView.createConsoleActions()[2]);
        // ConsoleView 按钮: scroll to end
        actionGroup.add(consoleView.createConsoleActions()[3]);
        // ConsoleView 按钮: clear
        actionGroup.add(consoleView.createConsoleActions()[5]);
        // 自定义按钮
        actionGroup.add(new ScannerAction(consoleView));
        actionGroup.add(new ConsoleToolRerunAction(rerun));
        actionGroup.add(new ConsoleToolStopAction(stop));
        return actionGroup;
    }
}
