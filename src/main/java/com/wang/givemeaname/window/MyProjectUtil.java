package com.wang.givemeaname.window;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 10:26
 */
public class MyProjectUtil {

    public static void setRunning(Project project, String key, boolean value) {
        PropertiesComponent.getInstance(project).setValue(key, value);
    }

    public static boolean getRunning(Project project, String key) {
        return PropertiesComponent.getInstance(project).getBoolean(key);
    }
}
