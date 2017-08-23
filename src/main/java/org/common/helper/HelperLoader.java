package org.common.helper;

import org.common.util.ClassUtil;

/**加载相应的helper类
 * 直接调用init方法来加载这些helper类，实际上static块会加载，只是让加载集中
 * Created by paul on 2017/8/22.
 */
public class HelperLoader {

    public static void init(){
        Class<?>[] classList={
                ClassHelper.class,
                BeanHelper.class,
                ControllerHelper.class,
                IocHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
