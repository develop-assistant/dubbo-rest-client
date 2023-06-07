package cn.idea360.rest.annotation;

import cn.idea360.rest.proxy.ProxyType;
import cn.idea360.rest.configuration.RestClientRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({RestClientRegistrar.class})
public @interface EnableRestProxy {

    String[] value() default {};

    String[] basePackages() default {};

    ProxyType proxyType() default ProxyType.PROVIDER;

}
