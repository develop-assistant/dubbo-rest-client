package cn.idea360.rest.proxy;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cuishiying
 */
@Data
public class ProxyData implements Serializable {

	/**
	 * 代理类
	 */
	private Class<?> cls;

	/**
	 * 代理方法名
	 */
	private String methodName;

	/**
	 * 代理方法参数
	 */
	private Class<?>[] parameterTypes;

	/**
	 * 请求参数
	 */
	private Object[] arguments;

	/**
	 * 序列化载体
	 */
	private String args;

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
		this.setArgs(SerializationUtils.serialize(arguments));
	}

	public Object[] getArguments() {
		return SerializationUtils.deserialize(this.getArgs(), Object[].class);
	}

}
