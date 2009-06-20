package com.amb.wikishare.app;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BeanFactory {

    /**
     * Get spring bean by name.
     * @require org.springframework.web.context.ContextLoaderListener
     * @param servletContext
     * @param beanName
     * @return
     */
    public static Object getBean(ServletContext servletContext, String beanName) {
        WebApplicationContext wac =
            WebApplicationContextUtils.getWebApplicationContext(servletContext);

        return wac.getBean(beanName);
    }
}
