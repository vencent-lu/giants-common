/**
 * 
 */
package com.giants.common.tools.moveorderservice;

import com.giants.common.exception.BusinessException;
import com.giants.common.exception.GiantsException;

/**
 * @author vencent.lu
 *
 * Create Date:2014年3月26日
 */
public interface MoveOrderService<T> {
	
	/**
	 * 移动movedEntity到toEntity的前面
	 * @param movedEntity
	 * @param toEntity
	 * @throws BusinessException
	 */
	void movesToFront(T movedEntity,T toEntity) throws BusinessException;
	
	/**
	 * 移动movedEntity到toEntity的后面
	 * @param movedEntity
	 * @param toEntity
	 * @throws BusinessException
	 */
	void movesToBack(T movedEntity,T toEntity) throws BusinessException;
	
	/**
	 * 上移entity
	 * @param entity
	 * @throws GiantsException
	 */
	void moveUp(T entity) throws GiantsException;
	
	/**
	 * 下移entity
	 * @param entity
	 * @throws GiantsException
	 */
	void moveDown(T entity) throws GiantsException;

}
