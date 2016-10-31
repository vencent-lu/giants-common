/**
 * 
 */
package com.giants.common.lang.reflect;

import java.util.Arrays;

/**
 * @author vencent.lu
 *
 */
public class MethodKey {
	
	private String name;
	
	private Class<?>[] parameterTypes;

	public MethodKey(String name, Class<?>... parameterTypes) {
		super();
		this.name = name;
		this.parameterTypes = parameterTypes;
	}

	public String getName() {
		return name;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(parameterTypes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodKey other = (MethodKey) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(parameterTypes, other.parameterTypes))
			return false;
		return true;
	}
	
	

}
