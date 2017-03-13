/**
 * 
 */
package com.giants.common.lang.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具类.增加反射缓存，提高反射性能
 * @author vencent.lu
 *
 */
public class ReflectUtils {
	
	/**
	 * 缓存 Class
	 */
	private static final Map<String, Class<?>> classMap = new ConcurrentHashMap<String, Class<?>>();
	
	/**
	 * 缓存 Class 反射信息
	 */
	private static final Map<Class<?>,ReflectBean> reflectMap = new ConcurrentHashMap<Class<?>,ReflectBean>();
	
	public static Class<?> classForName(String className)
			throws ClassNotFoundException {
		Class<?> cls = classMap.get(className);
		if (cls == null) {
			try{
				cls = Class.forName(className);
			} catch(ClassNotFoundException e) {
				switch (className) {
				case "int":
					cls = int.class;
					break;
				case "boolean":
					cls = boolean.class;
				case "byte":
					cls = byte.class;
					break;
				case "short":
					cls = short.class;
					break;
				case "long":
					cls = long.class;
					break;
				case "char":
					cls = char.class;
					break;
				case "float":
					cls = float.class;
					break;
				case "double":
					cls = double.class;
					break;
				default:
					throw e;
				}
			}			
			classMap.put(className, cls);
		}
		return cls;
	}
	
	public static Class<?>[] getInterfaces(Class<?> cls) {
		return getReflectBean(cls).getInterfaces();
	}
	
	public static Class<?> getInterface(Class<?> cls, String interfaceName) {
		return getReflectBean(cls).getInterface(interfaceName);
	}
	
	public static Class<?> getSuperclass(Class<?> cls) {
		return getReflectBean(cls).getSuperclass();
	}
	
	public static Method[] getMethods(Class<?> cls) {
		return getReflectBean(cls).getMethods();
	}
	
	public static Method[] getDeclaredMethods(Class<?> cls) {
		return getReflectBean(cls).getDeclaredMethods();
	}
	
	public static Method getMethod(Class<?> cls, String methodName, Class<?>... parameterTypes)
			throws NoSuchMethodException {
		return getReflectBean(cls).getMethod(methodName, parameterTypes);
	}
	
	public static Class<?> findMethodInterface(Class<?> cls, String methodName,
			Class<?>... parameterTypes) {
		return getReflectBean(cls).findMethodInterface(methodName,
				parameterTypes);
	}
	
	public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
		if (cls == null) {
            return null;
        }
		Class<?> superclass = getReflectBean(cls).getSuperclass();
		if (superclass == null) {
			return null;
		}
		List<Class<?>> classes = new ArrayList<Class<?>>();  
        while (superclass != null) {
            classes.add(superclass);
            superclass = getReflectBean(superclass).getSuperclass();
        }
        return classes;
	}
	
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> cls) throws IntrospectionException {
        if (cls == null) {
            return null;
        }
        return getReflectBean(cls).getPropertyDescriptors();
    }
    
    public static PropertyDescriptor getPropertyDescriptor(Class<?> cls, String propertyName) throws IntrospectionException {
        if (cls == null) {
            return null;
        }
        return getReflectBean(cls).getPropertyDescriptor(propertyName);
    }
	
	
	private static ReflectBean getReflectBean(Class<?> cls) {
		ReflectBean reflectBean = reflectMap.get(cls);
		if (reflectBean == null) {
			reflectBean = new ReflectBean(cls);
			reflectMap.put(cls, reflectBean);
		}
		return reflectBean;
	}

}
