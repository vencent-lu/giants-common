/**
 *
 */
package com.giants.common.beanutils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.giants.common.lang.reflect.ReflectUtils;

/**
 * @author vencent.lu
 *
 */
public class PropertyUtils {

    public static Object getProperty(Object bean, String name)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
        PropertyDescriptor propertyDescriptor = ReflectUtils.getPropertyDescriptor(bean.getClass(), name);
        if (propertyDescriptor != null) {
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                return readMethod.invoke(bean);
            } else {
                return null;
            }
        } else {
            if (bean instanceof Map) {
                return ((Map<?, ?>) bean).get(name);
            } else {
                return null;
            }
        }
    }

}
