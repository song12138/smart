package org.smart.controller;

import org.common.annotation.Aspect;
import org.common.annotation.Controller;
import org.common.proxy.AspectProxy;

/**
 * Created by paul on 2017/11/1.
 */
@Aspect(Controller.class)
public class controllerAspect extends AspectProxy {
    @Override
    public void begin() {
        System.out.print("************************************************************************");
    }
}
