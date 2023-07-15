package cn.idea360.rest.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cuishiying
 */
@Slf4j
public class SerializationUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
				ObjectMapper.DefaultTyping.NON_FINAL);
	}

	private SerializationUtils() {
	}

	public static String serialize(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			log.error("json serialize with class err", e);
			return null;
		}
	}

	public static <T> T deserialize(String json, Class<T> cls) {
		try {
			return OBJECT_MAPPER.readValue(json, cls);
		}
		catch (JsonProcessingException e) {
			log.error("json deserialize with class err", e);
			return null;
		}
	}

}
