package org.common.annotation;

import java.lang.annotation.*;

/** 切面注解
 * Created by paul on 2017/11/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
