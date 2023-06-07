package cn.idea360.rest.web;

import cn.idea360.rest.proxy.ProxyData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author cuishiying
 */
@Slf4j
@RestController
@RequestMapping
public class ProxyApiController {

    @Resource
    private ApplicationContext applicationContext;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/proxy/api")
    public Object proxy(@RequestBody ProxyData proxyData) throws Exception{
        log.info("receive request: {}", proxyData);
        Method method = ReflectionUtils.findMethod(proxyData.getCls(), proxyData.getMethodName(),
                proxyData.getParameterTypes());
        if (method == null) {
            return "404";
        }
        Object bean = applicationContext.getBean(proxyData.getCls());
        if (Objects.isNull(bean)) {
            log.error("bean[{}] not exist", proxyData.getCls().getName());
            return null;
        }
        if (proxyData.getParameterTypes().length != proxyData.getArguments().length) {
            throw new IllegalArgumentException("Wrong args length");
        }
        Class<?>[] parameterTypes = proxyData.getParameterTypes();
        Object[] arguments = proxyData.getArguments();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < arguments.length; i++) {
            String json = mapper.writeValueAsString(arguments[0]);
            args[i] = mapper.readValue(json, parameterTypes[i]);
        }
        return method.invoke(bean, args);
    }
}
