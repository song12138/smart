package org.common.servlet;

import org.apache.commons.lang3.ArrayUtils;
import org.common.domain.Data;
import org.common.domain.Handler;
import org.common.domain.Param;
import org.common.domain.View;
import org.common.helper.BeanHelper;
import org.common.helper.ConfigHelper;
import org.common.helper.ControllerHelper;
import org.common.helper.HelperLoader;
import org.common.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**请求转发器
 * Created by paul on 2017/8/22.
 */
@WebServlet(value = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{


    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化Helper类
        HelperLoader.init();
        //获取servletContext
        ServletContext servletContext = config.getServletContext();
        //处理jsp的servelt
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJspPath()+"*");
        //处理静态资源默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getStaticPath() + "*");
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径与方法
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //获取Handler
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (null != handler) {
            //获取controller类与bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            //从req中获取请求参数
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String  paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = body.split("&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = param.split("=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            //根据获取的参数，new 请求参数对象
            Param param = new Param(paramMap);
            //调用Action对象
            Method method = handler.getActionMethod();
            //通过反射调用某类的某方法
            Object result = ReflectionUtil.invokeMethod(controllerBean, method, param);

            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getJspPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();
                if (null != model) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("utf-8");
                    PrintWriter write = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    write.write(json);
                    write.flush();
                    write.close();
                }
            }





        }


    }
}
