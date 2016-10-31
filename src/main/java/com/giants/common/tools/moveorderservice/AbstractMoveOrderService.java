/**
 * 
 */
package com.giants.common.tools.moveorderservice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giants.common.exception.BusinessException;
import com.giants.common.exception.GiantsException;
import com.giants.common.lang.reflect.FieldUtils;

/**
 * @author vencent.lu
 *
 * Create Date:2014年3月29日
 */
public abstract class AbstractMoveOrderService<T> implements MoveOrderService<T> {
	
	protected final Logger  logger = LoggerFactory.getLogger(this.getClass());
	
	private enum Position {
		BACK(),
		FRONT(),
		UP(),
		DOWN();				
		Position() {
		}		
	}
	
	private Method getOrderMethod;
	private Method setOrderMethod;
	private Method getParentEntityMethod;
		
	
	/**
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public AbstractMoveOrderService() {
		super();
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		Field[] fields = FieldUtils.getAvailableFields(entityClass);
		try {
			for (Field field : fields) {
				Order order = field.getAnnotation(Order.class);
				if (order != null) {
					this.getOrderMethod = entityClass
							.getDeclaredMethod(new StringBuffer("get").append(
									StringUtils.capitalize(field.getName()))
									.toString());
					this.setOrderMethod = entityClass.getDeclaredMethod(
							new StringBuffer("set").append(
									StringUtils.capitalize(field.getName()))
									.toString(), field.getType());
				}
				Parent parent = field.getAnnotation(Parent.class);
				if (parent != null) {
					this.getParentEntityMethod = entityClass
							.getDeclaredMethod(new StringBuffer("get").append(
									StringUtils.capitalize(field.getName()))
									.toString());
				}
			}
		} catch (Exception e) {
			this.logger.warn("init annotation on failure!", e);
		}
		
	}

	/**
	 * 取得实体排序值
	 * @param entity
	 * @return
	 */
	protected Integer getEntityOrder(T entity) {
		if (this.getOrderMethod != null) {
			Object order;
			try {
				order = this.getOrderMethod.invoke(entity);
				if (order instanceof Integer) {
					return (Integer)order;
				} else if (order instanceof Short) {
					return ((Short)order).intValue();
				} else if (order instanceof Long) {
					return ((Long)order).intValue();
				} else {
					throw new BusinessException("getEntityOrderMethodOnFailure", "moveOrderService.errorMessage.getEntityOrderMethodOnFailure");
				}
			} catch (Exception e) {
				this.logger.error("getEntityOrder on failure!", e);
				throw new BusinessException("getEntityOrderMethodOnFailure", "moveOrderService.errorMessage.getEntityOrderMethodOnFailure", e);
			}			
		} else {
			this.logger.error("order property does not exist!");
			throw new BusinessException("notFindOrderProperty", "moveOrderService.errorMessage.notFindOrderProperty");
		}
	}
	
	/**
	 * 设置实体排序值
	 * @param entity
	 * @param order
	 */
	protected void setEntityOrder(T entity, Integer order) {
		if (this.setOrderMethod != null) {
			Class<?> parameterType = this.setOrderMethod.getParameterTypes()[0];
			try {
				if (parameterType == int.class
						|| parameterType == Integer.class) {
					this.setOrderMethod.invoke(entity, order);
				} else if (parameterType == short.class
						|| parameterType == Short.class) {
					this.setOrderMethod.invoke(entity, order.shortValue());
				} else if (parameterType == long.class
						|| parameterType == Long.class) {
					this.setOrderMethod.invoke(entity, order.longValue());
				} else {
					throw new BusinessException("setEntityOrderMethodOnFailure", "moveOrderService.errorMessage.setEntityOrderMethodOnFailure");
				}
			} catch (Exception e) {
				this.logger.error("setEntityOrder on failure!", e);
				throw new BusinessException("setEntityOrderMethodOnFailure", "moveOrderService.errorMessage.setEntityOrderMethodOnFailure", e);
			}
		} else {
			this.logger.error("order property does not exist!");
			throw new BusinessException("notFindOrderProperty", "moveOrderService.errorMessage.notFindOrderProperty");
		}
	}

	protected Object getParentEntity(T entity) {
		if (this.getParentEntityMethod != null) {
			try {
				return this.getParentEntityMethod.invoke(entity);
			} catch (Exception e) {
				this.logger.error("getParentEntity on failure!", e);
				throw new BusinessException("getParentEntityMethodOnFailure", "moveOrderService.errorMessage.getParentEntityMethodOnFailure", e);
			}
		}
		return null;
	}
	
	/**
	 * 更新实体
	 * @param entity
	 */
	protected abstract void updateEntity(T entity);
	
	/**
	 * 返回同一级分类下排序事情从beginOrder 到 endOrder的实体.
	 * 如果不存在层次关系 或 根目录, parentEntity 传入 null
	 * @param parentEntity
	 * @param beginOrder
	 * @param endOrder
	 * @return
	 */
	protected abstract List<T> findNeedModifyOrderEntity(Object parentEntity, Integer beginOrder, Integer endOrder);
	
	/**
	 * 查找当前最大排序值
	 * 如果不存在层次关系 或 根目录, parentEntity 传入 null
	 * @param parentEntity
	 * @return
	 */
	protected abstract Integer findCurrentMaxOrder(Object parentEntity);
	
	/**
	 * 根据排序值返回实体
	 * 如果不存在层次关系 或 根目录, parentEntity 传入 null
	 * @param parentEntity
	 * @param order
	 * @return
	 */
	protected abstract T findEntityByOrder(Object parentEntity ,Integer order);
	
	/**
	 * 检查移动合法性,检查不通过直接抛出 BusinessException
	 * @param movedEntity
	 * @param toEntity
	 * @throws BusinessException
	 */
	protected void checkMovesLegitimacy(T movedEntity, T toEntity) throws BusinessException{
		
	}
	
	private void moves(T movedEntity, T toEntity, Position position)
			throws BusinessException {
		int toOrder = this.getEntityOrder(toEntity);
		int movedOrder = this.getEntityOrder(movedEntity);
		this.checkMovesLegitimacy(movedEntity, toEntity);

		int beginOrder = movedOrder > toOrder ? (position == Position.BACK ? toOrder + 1
				: toOrder)
				: movedOrder;
		int endOrder = movedOrder > toOrder ? movedOrder
				: (position == Position.FRONT ? toOrder - 1 : toOrder);
		int newMovedOrder = movedOrder > toOrder ? (position == Position.BACK ? toOrder + 1
				: toOrder)
				: (position == Position.FRONT ? toOrder - 1 : toOrder);
		int orderAdd = movedOrder > toOrder ? 1 : -1;

		List<T> needModifyOrderEntitys = this.findNeedModifyOrderEntity(
				this.getParentEntity(movedEntity), beginOrder, endOrder);
		for (T entity : needModifyOrderEntitys) {
			if (!entity.equals(movedEntity)) {
				this.setEntityOrder(entity, this.getEntityOrder(entity)
						+ orderAdd);
			} else {
				this.setEntityOrder(entity, newMovedOrder);
			}
			this.updateEntity(entity);
		}

	}
	
	private void move(T entity, Position position) throws GiantsException{
		int orderAdd = position == Position.UP ? -1 : 1;
		int swapOrder = this.getEntityOrder(entity) + orderAdd;
		int maxOrder = this.findCurrentMaxOrder(this.getParentEntity(entity));
		
		if (swapOrder < 1) {
			T toEntity = this.findEntityByOrder(this.getParentEntity(entity), maxOrder);			
			if (toEntity == null) {
				throw new BusinessException("orderNotExist",
						"moveOrderService.errorMessage.orderNotExist", maxOrder);
			}
			this.moves(entity, toEntity, Position.BACK);
			return;
		}
		
		if (swapOrder > maxOrder) {
			T toEntity = this.findEntityByOrder(this.getParentEntity(entity), 1);
			if (toEntity == null) {
				throw new BusinessException("orderNotExist",
						"moveOrderService.errorMessage.orderNotExist", 1);
			}
			this.moves(entity, toEntity, Position.FRONT);
			return;
		}

		T swapEntity = this.findEntityByOrder(this.getParentEntity(entity), swapOrder);
		if (swapEntity == null) {
			throw new BusinessException("orderNotExist",
					"moveOrderService.errorMessage.orderNotExist", swapOrder);
		}
		Integer currentOrder = this.getEntityOrder(entity);
		this.setEntityOrder(entity, this.getEntityOrder(swapEntity));
		this.updateEntity(entity);
		this.setEntityOrder(swapEntity, currentOrder);
		this.updateEntity(swapEntity);
	}

	@Override
	public void movesToFront(T movedEntity, T toEntity)
			throws BusinessException {
		this.moves(movedEntity, toEntity, Position.FRONT);
	}

	@Override
	public void movesToBack(T movedEntity, T toEntity) throws BusinessException {
		this.moves(movedEntity, toEntity, Position.BACK);
	}

	@Override
	public void moveUp(T entity) throws GiantsException {
		this.move(entity, Position.UP);
	}

	@Override
	public void moveDown(T entity) throws GiantsException {
		this.move(entity, Position.DOWN);
	}

}
