package cn.idea360.rest.web;

import cn.idea360.rest.proxy.ProxyData;
import cn.idea360.rest.proxy.ResultWrapper;
import cn.idea360.rest.proxy.SerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author cuishiying
 */
@Slf4j
@RestController
@RequestMapping
public class ProxyApiController {

	@Resource
	private ApplicationContext applicationContext;

	@PostMapping("/proxy/api")
	public Object proxy(@RequestBody ProxyData proxyData) throws Exception {
		log.info("receive request: {}", proxyData);
		Method method = ReflectionUtils.findMethod(proxyData.getCls(), proxyData.getMethodName(),
				proxyData.getParameterTypes());
		if (method == null) {
			return new ResponseEntity<>(String.format("method[%s] 404", proxyData.getMethodName()),
					HttpStatus.BAD_REQUEST);
		}
		Object bean = applicationContext.getBean(proxyData.getCls());
		if (proxyData.getParameterTypes().length != proxyData.getArguments().length) {
			throw new IllegalArgumentException("Wrong args length");
		}
		Object invoke = method.invoke(bean, proxyData.getArguments());
		ResultWrapper res = new ResultWrapper();
		res.setData(invoke);
		return new ResponseEntity<>(SerializationUtils.serialize(res), HttpStatus.OK);
	}

}
