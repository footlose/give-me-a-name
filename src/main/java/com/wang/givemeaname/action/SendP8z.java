package com.wang.givemeaname.action;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.hsf.HSFJSONUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.wang.givemeaname.action.OkHttpUtil.postJsonParams;


/**
 * @author wangshuai
 * @version 1.0
 * @description
 * @date 2022/6/21 08:58
 */
public class SendP8z {


    /**
     * 获取对象的属性和其对应的值
     */
    public static ResultDto getFiledAndValue(ResultDto object) {
        if ("请输入想要命名的中文".equals(object.getError())) {

        }
        // 对obj反射
        Class objClass = object.getClass();
        Method[] objmethods = objClass.getDeclaredMethods();
        Map objMeMap = new HashMap();
        for (Method method : objmethods) {
            objMeMap.put(method.getName(), method);
        }
        for (Method objmethod : objmethods) {
            {
                String methodName = objmethod.getName();
                if (methodName.startsWith("get")) {
                    try {
                        Object returnObj = objmethod.invoke(object);
                        Method setmethod = (Method) objMeMap.get("set"
                                + methodName.split("get")[1]);
                        if (returnObj != null) {
                            returnObj = stripHtml(String.valueOf(returnObj));
                            setmethod.invoke(object, returnObj);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return object;
    }


    public static ResultDto getP8zList(String name) {
        String result = postJsonParams("http://p8z.fun/c", "{\"name\":\"" + name + "\"}");
        ResultDto resultDto = JSON.parseObject(result, ResultDto.class);
        return getFiledAndValue(resultDto);
    }

    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", ",");
        content = content.replaceAll(" ,", ",");
        content = content.replaceAll(",*,", ",");
        if (content.startsWith(",")) {
            content = content.substring(1);
        }
        return content;
    }
}
