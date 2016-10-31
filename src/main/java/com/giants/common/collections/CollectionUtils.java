package com.giants.common.collections;

import java.util.Collection;

/**
 * @author vencent.lu
 *
 */
public class CollectionUtils extends
		org.apache.commons.collections.CollectionUtils {
	
	public static Collection<?> union(final Collection<?>... collections) {
		Collection<?> collection = null;
		if (collections != null && collections.length > 0) {
			collection = collections[0];
			for (int i = 1 ; i < collections.length; i++) {
				collection = union(collection, collections[i]);
			}
		}		
		return collection;
	}

}
