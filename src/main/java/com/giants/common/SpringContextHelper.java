/**
 * 
 */
package com.giants.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author vencent.lu
 *
 */
public class SpringContextHelper implements ApplicationContextAware {
	
	private static ApplicationContext appContext;
	private static String contextRealPath;
	
	private static ResourceBundleMessageSource resourceBundleMessageSource;

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
		resourceBundleMessageSource = appContext.getBean(ResourceBundleMessageSource.class);
	}

	/**
	 * @return the appContext
	 */
	public static ApplicationContext getAppContext() {
		return appContext;
	}

	/**
	 * @return the contextRealPath
	 */
	public static String getContextRealPath() {
		return contextRealPath;
	}

	public static final Object getSpringBean(String beanName) {
		return appContext.getBean(beanName);
	}
	
	public static final <T> T getSpringBean(Class<T> beanType) {
		return appContext.getBean(beanType);
	}
	
	public static final String getMessage(String resourceKey, Object... args) {
		if (resourceBundleMessageSource == null) {
			return null;
		}
		return resourceBundleMessageSource.getMessage(resourceKey, args, null);
	}
}
