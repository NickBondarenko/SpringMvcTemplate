package com.alphatek.tylt.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * @author naquaduh
 *         Created on 2014-01-10:9:15 PM.
 */
public class DefaultApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationContextInitializer.class);

	@Override public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		try {
			MutablePropertySources propertySources = environment.getPropertySources();
			propertySources.addFirst(new ResourcePropertySource("classpath:properties/system.properties"));
			propertySources.addLast(new ResourcePropertySource("classpath:properties/database.properties"));
			LOGGER.info("Environment properties loaded");
		} catch (IOException e) {
			LOGGER.info("Properties not found in classpath");
		}
	}
}