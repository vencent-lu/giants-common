/**
 * 
 */
package com.giants.common.beanutils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;

import com.giants.common.regex.Matcher;
import com.giants.common.regex.Pattern;

/**
 * @author vencent.lu
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	static {
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new ShortConverter(null), Short.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new DateConverter(null), Date.class);
	}

	
	public final static Pattern mapPropertyPattern = Pattern.compile("([a-zA-Z]+)\\s*\\:\\s*\\{([a-zA-Z\\,\\s]+)+\\}");
	
	public static void copyCollectionProperties(Collection<Object> dest,
			Collection<Object> orig, Class<?> copyToClass)
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Iterator<Object> it = orig.iterator();
		while (it.hasNext()) {
			Object destObj = copyToClass.getDeclaredConstructor().newInstance();
			copyProperties(destObj, it.next());
			dest.add(destObj);
		}
	}
	
	public static void copyCollectionProperties(Collection<Object> dest,
			Collection<Object> orig, Class<?> copyToClass,
			String... propertyNames) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Iterator<Object> it = orig.iterator();
		while (it.hasNext()) {
			Object destObj = copyToClass.getDeclaredConstructor().newInstance();
			copyProperties(destObj, it.next(), propertyNames);
			dest.add(destObj);
		}
	}
	
	public static void copyCollectionPropertiesToMap(Collection dest,
			Collection<?> orig, String... propertyNames)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException,
			IllegalArgumentException, SecurityException {
		Iterator<?> it = orig.iterator();
		while (it.hasNext()) {
			Map<String, Object> destObj = new HashMap<String, Object>();
			copyBeanPropertiesToMap(destObj, it.next(), propertyNames);
			dest.add(destObj);
		}
	}
		
	public static void copyMapPropertiesToBean(Object dest, Map<String,Object> orig)
			throws IllegalAccessException, InvocationTargetException {
		Iterator<Entry<String,Object>> entries = orig.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String,Object> entry = entries.next();
			String name = (String) entry.getKey();
			BeanUtilsBean utilsBean = BeanUtilsBean.getInstance();
			if (utilsBean.getPropertyUtils().isWriteable(dest, name)) {
				Object value = entry.getValue();
				if (value instanceof Map) {
					try {
						Class<?> type = utilsBean.getPropertyUtils()
								.getPropertyDescriptor(dest, name)
								.getPropertyType();
						Object obj = type.newInstance();
						if (obj instanceof Map) {
							utilsBean.copyProperty(dest, name, value);
						} else {
							copyMapPropertiesToBean(obj, (Map<String,Object>) value);
							utilsBean.copyProperty(dest, name, obj);
						}
					} catch (Exception e) {

					}
				} else {
					utilsBean.copyProperty(dest, name, value);
				}
			}
		}
	}
	
	public static void copyBeanPropertiesToMap(Map<String, Object> dest,
			Object orig, String... propertyNames)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException,
			IllegalArgumentException, SecurityException {
		BeanUtilsBean utilsBean = BeanUtilsBean.getInstance();
		if (propertyNames == null || propertyNames.length == 0) {
			PropertyDescriptor[] origDescriptors = utilsBean.getPropertyUtils()
					.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (utilsBean.getPropertyUtils().isReadable(orig, name)) {
					Object value = utilsBean.getPropertyUtils()
							.getSimpleProperty(orig, name);
					dest.put(name, value);
				}
			}
		} else {
			for (int i = 0; i < propertyNames.length; i++) {
				Matcher matcher = mapPropertyPattern.matcher(propertyNames[i]);
				if (!matcher.find()) {
					dest.put(propertyNames[i], utilsBean.getPropertyUtils()
							.getSimpleProperty(orig, propertyNames[i].trim()));
				} else {
					String propertyName = matcher.group(1).trim();
					Object propertyValue = utilsBean.getPropertyUtils().getSimpleProperty(orig, propertyName);
					if (propertyValue != null) {
						String[] copyPropertyNames = matcher.group(2).replaceAll("[\\,\\s]+", ",")
								.replaceAll("\\,", " ").trim().split(" ");
						if (propertyValue instanceof Collection<?>) {
							List<Map<String, Object>> collection = new ArrayList<Map<String, Object>>();
							copyCollectionPropertiesToMap(collection,
									(Collection) propertyValue, copyPropertyNames);
							dest.put(propertyName, collection);
						} else {
							Map<String, Object> map = new HashMap<String, Object>();
							copyBeanPropertiesToMap(
									map,
									utilsBean.getPropertyUtils().getSimpleProperty(
											orig, propertyName), copyPropertyNames);
							dest.put(propertyName, map);
						}
					}					
				}
			}
		}
	}
	
	public static Class<?> getPropertyType(Object bean, String propertyName)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return BeanUtilsBean.getInstance().getPropertyUtils()
				.getPropertyType(bean, propertyName);
	}
	
	public static Object getPropertyValue(Object bean, String propertyName)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if (bean instanceof Map) {
			return ((Map) bean).get(propertyName);
		} else {
			return BeanUtilsBean.getInstance().getPropertyUtils()
					.getSimpleProperty(bean, propertyName);
		}
	}
	
	public static void copyProperties(Object dest, Object orig,
			String... propertyNames) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		BeanUtilsBean utilsBean = BeanUtilsBean.getInstance();
		for (String propertyName : propertyNames) {
			utilsBean.copyProperty(dest, propertyName, utilsBean
					.getPropertyUtils().getSimpleProperty(orig, propertyName));
		}
	}
	
	public static void copyProperties(Object dest, Object orig)
	        throws IllegalAccessException, InvocationTargetException {
		BeanUtilsBean.getInstance().copyProperties(dest, orig);
	}
	
	public static void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtilsBean.getInstance().copyProperty(bean, name, value);
	}
	
	public static String[] getNotNullPropertyNames(Object source)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance()
				.getPropertyUtils();
		java.beans.PropertyDescriptor[] pds = propertyUtilsBean
				.getPropertyDescriptors(source);

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			if ("class".equals(pd.getName())) {
				continue;
			}
			Object srcValue = propertyUtilsBean.getProperty(source,
					pd.getName());
			if (srcValue != null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
	
	public static void copyPropertiesIgnoreNull(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		copyProperties(dest, orig, getNotNullPropertyNames(orig));
	}
}
