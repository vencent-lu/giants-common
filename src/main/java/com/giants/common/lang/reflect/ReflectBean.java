/**
 * 
 */
package com.giants.common.lang.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Class缓存信息bean,按需缓存,第一次使用时就进行缓存
 * @author vencent.lu
 *
 */
public class ReflectBean {
	
	private Class<?> beanClass;
	
	private boolean isGetInterfaces = false;
	private Class<?>[] interfaces;
	
	private boolean isGetSuperclass = false;
	private Class<?> superclass;
	
	private boolean isGetMethods = false;
	private Method[] methods;
	
	private boolean isGetDeclaredMethods = false;
	private Method[] declaredMethods;
	
	private Map<MethodKey, Boolean> methodIsGetMap;
	private Map<MethodKey, Method> methodMap;
	
	private Map<MethodKey, Boolean> methodInterfaceIsGetMap;
	private Map<MethodKey, Class<?>> methodInterfaceMap;
	
	public ReflectBean(Class<?> beanClass) {
		super();
		this.beanClass = beanClass;
	}

	public Class<?>[] getInterfaces() {
		if (!this.isGetInterfaces) {
			this.interfaces = this.beanClass.getInterfaces();
			this.isGetInterfaces = true;
		}
		return this.interfaces;
	}
	
	public Class<?> getInterface(String interfaceName) {
		Class<?>[] interfaces = this.getInterfaces();
		for(Class<?> inter : interfaces) {
			if (inter.getName().equals(interfaceName)) {
				return inter;
			}
		}
		return null;
	}

	public Class<?> getSuperclass() {
		if (!this.isGetSuperclass) {
			this.superclass = this.beanClass.getSuperclass();
			this.isGetSuperclass = true;
		}
		return superclass;
	}

	public Method[] getMethods() {
		if (!this.isGetMethods) {
			this.methods = this.beanClass.getMethods();
			this.isGetMethods = true;
		}
		return methods;
	}

	public Method[] getDeclaredMethods() {
		if (!this.isGetDeclaredMethods) {
			this.declaredMethods = this.beanClass.getDeclaredMethods();
			this.isGetDeclaredMethods = true;
		}
		return declaredMethods;
	}
	
	 public Method getMethod(String name, Class<?>... parameterTypes) {
		 MethodKey methodKey = new MethodKey(name, parameterTypes);
		 Map<MethodKey, Boolean> methodIsGetMap = this.getMethodIsGetMap();
		 if (methodIsGetMap.get(methodKey) != null) {
			 return this.getMethodMap().get(methodKey);
		 }
		 Map<MethodKey, Method> methodMap = this.getMethodMap();
		 if (this.methods != null && this.methods.length > 0) {
			for (Method meth : this.methods) {
				if (meth.getName().equals(name)
						&& meth.getParameterTypes().equals(parameterTypes)) {
					methodMap.put(methodKey, meth);
					methodIsGetMap.put(methodKey, true);
					return meth;
				}
			}
			methodIsGetMap.put(methodKey, true);
			return null;
		 } else {
			try {
				Method method = this.beanClass.getMethod(name, parameterTypes);
				methodIsGetMap.put(methodKey, true);
				methodMap.put(methodKey, method);
				 return method;
			} catch (NoSuchMethodException e) {
				methodIsGetMap.put(methodKey, true);
				return null;
			}
		 }
	 }
	 
	public Class<?> findMethodInterface(String name,
			Class<?>... parameterTypes) {
		MethodKey methodKey = new MethodKey(name, parameterTypes);
		Map<MethodKey, Boolean> methodInterfaceIsGetMap = this.getMethodInterfaceIsGetMap();
		if (methodInterfaceIsGetMap.get(methodKey) != null) {
			return this.getMethodInterfaceMap().get(methodKey);
		}			
		Map<MethodKey, Class<?>> methodInterfaceMap = this.getMethodInterfaceMap();
		Class<?>[] inters = this.getInterfaces();
		if (inters == null || inters.length == 0) {
			return null;
		}
		for(Class<?> inter : inters) {
			try {
				ReflectUtils.getMethod(inter, name, parameterTypes);
				methodInterfaceMap.put(methodKey, inter);
				methodInterfaceIsGetMap.put(methodKey, true);
				return inter;
			} catch (NoSuchMethodException e) {}
		}
		methodInterfaceIsGetMap.put(methodKey, true);
		return null;
	}
		 
	private Map<MethodKey, Boolean> getMethodIsGetMap() {
		if (this.methodIsGetMap == null) {
			this.methodIsGetMap = new HashMap<MethodKey, Boolean>();
		}
		return this.methodIsGetMap;
	}

	private Map<MethodKey, Method> getMethodMap() {
		 if (this.methodMap == null) {
			 this.methodMap = new HashMap<MethodKey, Method>();
		 }
		 return this.methodMap;
	 }
	
	private Map<MethodKey, Boolean> getMethodInterfaceIsGetMap() {
		if (this.methodInterfaceIsGetMap == null) {
			this.methodInterfaceIsGetMap = new HashMap<MethodKey, Boolean>();
		}
		return this.methodInterfaceIsGetMap;
	}

	private Map<MethodKey, Class<?>> getMethodInterfaceMap() {
		if (this.methodInterfaceMap == null) {
			this.methodInterfaceMap = new HashMap<MethodKey, Class<?>>();
		}
		return methodInterfaceMap;
	}
		
}
