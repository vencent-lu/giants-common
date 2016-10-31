/**
 * 
 */
package com.giants.common.lang.reflect;

import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author vencent.lu
 *
 */
public class FieldUtils extends org.apache.commons.lang.reflect.FieldUtils {
	
	public static Field[] getAvailableFields(Class<?> clas) {
		Field[] fields = clas.getDeclaredFields();
		for (Class<?> clazz = clas.getSuperclass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			fields = (Field[]) ArrayUtils.addAll(fields,
					siftAvailableFields(fields, clazz.getDeclaredFields()));
		}
		return fields;
	}
	
	private static Field[] siftAvailableFields(Field[] subclassesFields,
			Field[] superclassFields) {
		Field[] availableFields = null;
		for (Field superclassField : superclassFields) {
			boolean overwrite = false;
			for (Field subclassesField : subclassesFields) {
				if (superclassField.getName().equals(subclassesField.getName())) {
					overwrite = true;
					break;
				}
			}
			if (!overwrite) {
				availableFields = (Field[]) ArrayUtils.add(availableFields,
						superclassField);
			}
		}
		return availableFields;
	}

}
