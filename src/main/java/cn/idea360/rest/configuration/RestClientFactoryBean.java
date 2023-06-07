package cn.idea360.rest.configuration;

import cn.idea360.rest.proxy.RestInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

/**
 * @author cuishiying
 */
public class RestClientFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware, BeanFactoryAware {

    private Class<?> type;
    private String url;
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() {
        RestInvocationHandler<?> invocationHandler = new RestInvocationHandler<>(type, url);
        return Proxy.newProxyInstance(type.getClassLoader(),
                new Class[] {type}, invocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
        this.beanFactory = context;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
