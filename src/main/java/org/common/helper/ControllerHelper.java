package org.common.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.common.annotation.Action;
import org.common.domain.Handler;
import org.common.domain.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**控制器助手类
 *  获取Action注解中表达式，封装request与handler，建立映射关系，放入map，提供根据request获取handler方法
 * Created by paul on 2017/8/22.
 */
public class ControllerHelper {
    /**
     * 存放request与handler映射关系map
     */
    private static Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        //所有controller类
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerSet)) {
            for (Class<?> controllerClass : controllerSet) {
                //获取所有方法
                Method[] methods = controllerClass.getMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        //是否有Action注解
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            //Action注解的value
                            String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    //将action 的value 放入request对象中
                                    Request request = new Request(requestMethod, requestPath);
                                    //对应的handler对象
                                    Handler handler = new Handler(controllerClass, method);
                                    //映射关系保存到map中
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
