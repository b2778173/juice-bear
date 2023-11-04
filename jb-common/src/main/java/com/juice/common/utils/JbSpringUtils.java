package com.juice.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * @author cheng
 * @since 2023/11/4 3:44 PM
 **/
@Component
public class JbSpringUtils implements ApplicationContextAware, BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;
    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        beanFactory = configurableListableBeanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContextParam) throws BeansException {
        applicationContext = applicationContextParam;
    }


    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();
        return Arrays.stream(pds)
                .map(PropertyDescriptor::getName)
                .filter(property -> wrapper.getPropertyValue(property) == null).toArray(String[]::new);
    }
}

