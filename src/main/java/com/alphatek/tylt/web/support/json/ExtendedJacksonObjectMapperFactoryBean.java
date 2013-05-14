package com.alphatek.tylt.web.support.json;

import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import java.text.DateFormat;
import java.util.Map;

public class ExtendedJacksonObjectMapperFactoryBean implements FactoryBean<ObjectMapper>, InitializingBean {
	private final Jackson2ObjectMapperFactoryBean jacksonObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
	private CharacterEscapes[] characterEscapes;
	private Map<Class<?>, Class<?>> mixInAnnotations;

	/**
	 * Set the ObjectMapper instance to use. If not set, the ObjectMapper will
	 * be created using its default constructor.
	 */
	public void setObjectMapper(ObjectMapper objectMapper) {
		jacksonObjectMapperFactoryBean.setObjectMapper(objectMapper);
	}

	public void setCharacterEscapes(CharacterEscapes... characterEscapes) {
		this.characterEscapes = characterEscapes;
	}

	public void setMixInAnnotations(Map<Class<?>, Class<?>> mixInAnnotations) {
		this.mixInAnnotations = mixInAnnotations;
	}

	/**
	 * Define the format for date/time with the given {@link java.text.DateFormat}.
	 * @see #setSimpleDateFormat(String)
	 */
	public void setDateFormat(DateFormat dateFormat) {
		jacksonObjectMapperFactoryBean.setDateFormat(dateFormat);
	}

	/**
	 * Define the date/time format with a {@link java.text.SimpleDateFormat}.
	 * @see #setDateFormat(java.text.DateFormat)
	 */
	public void setSimpleDateFormat(String format) {
		jacksonObjectMapperFactoryBean.setSimpleDateFormat(format);
	}

	/**
	 * Set the {@link com.fasterxml.jackson.databind.AnnotationIntrospector} for both serialization and
	 * deserialization.
	 */
	public void setAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
		jacksonObjectMapperFactoryBean.setAnnotationIntrospector(annotationIntrospector);
	}

	/**
	 * Configure custom serializers. Each serializer is registered for the type
	 * returned by {@link com.fasterxml.jackson.databind.JsonSerializer#handledType()}, which must not be
	 * {@code null}.
	 * @see #setSerializersByType(java.util.Map)
	 */
	public void setSerializers(JsonSerializer<?>... serializers) {
		jacksonObjectMapperFactoryBean.setSerializers(serializers);
	}

	/**
	 * Configure custom serializers for the given types.
	 * @see #setSerializers(com.fasterxml.jackson.databind.JsonSerializer...)
	 */
	public void setSerializersByType(Map<Class<?>, JsonSerializer<?>> serializers) {
		jacksonObjectMapperFactoryBean.setSerializersByType(serializers);
	}

	/**
	 * Configure custom deserializers for the given types.
	 */
	public void setDeserializersByType(Map<Class<?>, JsonDeserializer<?>> deserializers) {
		jacksonObjectMapperFactoryBean.setDeserializersByType(deserializers);
	}

	/**
	 * Shortcut for {@link com.fasterxml.jackson.databind.MapperFeature#AUTO_DETECT_FIELDS} option.
	 */
	public void setAutoDetectFields(boolean autoDetectFields) {
		jacksonObjectMapperFactoryBean.setAutoDetectFields(autoDetectFields);
	}

	/**
	 * Shortcut for {@link com.fasterxml.jackson.databind.MapperFeature#AUTO_DETECT_SETTERS}/
	 * {@link com.fasterxml.jackson.databind.MapperFeature#AUTO_DETECT_GETTERS} option.
	 */
	public void setAutoDetectGettersSetters(boolean autoDetectGettersSetters) {
		jacksonObjectMapperFactoryBean.setAutoDetectGettersSetters(autoDetectGettersSetters);
	}

	/**
	 * Shortcut for {@link com.fasterxml.jackson.databind.SerializationFeature#FAIL_ON_EMPTY_BEANS} option.
	 */
	public void setFailOnEmptyBeans(boolean failOnEmptyBeans) {
		jacksonObjectMapperFactoryBean.setFailOnEmptyBeans(failOnEmptyBeans);
	}

	/**
	 * Shortcut for {@link com.fasterxml.jackson.databind.SerializationFeature#INDENT_OUTPUT} option.
	 */
	public void setIndentOutput(boolean indentOutput) {
		jacksonObjectMapperFactoryBean.setIndentOutput(indentOutput);
	}

	/**
	 * Specify features to enable.
	 *
	 * @see com.fasterxml.jackson.databind.MapperFeature
	 * @see com.fasterxml.jackson.databind.SerializationFeature
	 * @see com.fasterxml.jackson.databind.DeserializationFeature
	 * @see com.fasterxml.jackson.core.JsonParser.Feature
	 * @see com.fasterxml.jackson.core.JsonGenerator.Feature
	 */
	public void setFeaturesToEnable(Object... featuresToEnable) {
		jacksonObjectMapperFactoryBean.setFeaturesToEnable(featuresToEnable);
	}

	/**
	 * Specify features to disable.
	 *
	 * @see com.fasterxml.jackson.databind.MapperFeature
	 * @see com.fasterxml.jackson.databind.SerializationFeature
	 * @see com.fasterxml.jackson.databind.DeserializationFeature
	 * @see com.fasterxml.jackson.core.JsonParser.Feature
	 * @see com.fasterxml.jackson.core.JsonGenerator.Feature
	 */
	public void setFeaturesToDisable(Object... featuresToDisable) {
		jacksonObjectMapperFactoryBean.setFeaturesToDisable(featuresToDisable);
	}

	/**
	 * Method afterPropertiesSet. Actions to perform after all properties are set.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		jacksonObjectMapperFactoryBean.afterPropertiesSet();

		if (characterEscapes != null) {
			for (CharacterEscapes escapes : characterEscapes) {
				jacksonObjectMapperFactoryBean.getObject().getFactory().setCharacterEscapes(escapes);
			}
		}

		if (mixInAnnotations != null) {
			jacksonObjectMapperFactoryBean.getObject().setMixInAnnotations(mixInAnnotations);
		}
	}

	@Override
	public ObjectMapper getObject() throws Exception {
		return jacksonObjectMapperFactoryBean.getObject();
	}

	@Override
	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return jacksonObjectMapperFactoryBean.isSingleton();
	}
}