package cn.idea360.rest.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author cuishiying
 */
@Slf4j
public class RestInvocationHandler<T> implements InvocationHandler {

	private final Class<T> interfaceType;

	private final RequestTemplate requestTemplate;

	public RestInvocationHandler(Class<T> interfaceType, String url) {
		this.interfaceType = interfaceType;
		this.requestTemplate = new RequestTemplate(url);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (Object.class.equals(method.getDeclaringClass())) {
			return method.invoke(this, args);
		}
		ProxyData proxyData = new ProxyData();
		proxyData.setCls(interfaceType);
		proxyData.setMethodName(method.getName());
		proxyData.setParameterTypes(method.getParameterTypes());
		proxyData.setArguments(args);
		log.info("proxy data: {}", proxyData);
		return requestTemplate.doRequest(proxyData, method.getGenericReturnType());
	}

}
