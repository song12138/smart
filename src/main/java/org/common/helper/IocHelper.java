package org.common.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.common.annotation.Inject;
import org.common.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**依赖注入助手类
 * Created by paul on 2017/8/22.
 */
public class IocHelper {
    static {
        //获取beanap
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //todo
        if (null != beanMap && beanMap.size()>0) {
            //Bean map 遍历
            for (Map.Entry<Class<?>, Object> beanEntity : beanMap.entrySet()) {
                Class<?> beanClass = beanEntity.getKey();
                Object beanInstance = beanEntity.getValue();
                //成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanfield:beanFields) {
                        //判断是否有Inject注解
                        if (beanfield.isAnnotationPresent(Inject.class)) {
                            //获得class，在从map中取出实例
                            Class<?> beanFieldClass = beanfield.getType();
                            Object beanFieldInstance=beanMap.get(beanFieldClass);
                            if (null != beanFieldInstance) {
                                //反射赋值
                                ReflectionUtil.setField(beanInstance,beanfield,beanFieldInstance);
                            }
                        }
                    }
                }
            }

        }
    }
}
