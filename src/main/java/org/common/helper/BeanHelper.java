package org.common.helper;

import com.sun.tools.jdi.LinkedHashMap;
import org.common.util.ClassUtil;
import org.common.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**bean助手类，相当于bean容器
 *载入bean类，通过反射实例bean，存放在map中
 * Created by paul on 2017/8/22.
 */
public class BeanHelper {
    /**
     * 存放bean类与bean类实例
     */
    public static final Map<Class<?>, Object> BEAN_MAP ;

    static {
        BEAN_MAP = new HashMap<>();
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : classSet) {
            Object instance = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls, instance);
        }
    }

    /**
     * 获取映射map
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}
