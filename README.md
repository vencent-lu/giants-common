# giants-common
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vencent-lu/giants-common/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vencent-lu/giants-common)

公用类库

## com.giants.common.beanutils.BeanUtils
继承于 org.apache.commons.beanutils.BeanUtils，增加 多属性复制、集合拷贝 等功能

## com.giants.common.codec.EncryptionDevice
加密器封装类 支持 MD5、RSA、DES 等加密算法

## com.giants.common.exception
异常定义包，包含以下 exception:
* GiantsException : 顶层异常
    * DataOperationException : 数据操作异常
    * DataValidationException : 数据验证失败异常
    * ViewException : 视图层异常
    * SystemException : 系统未知异常
    * BusinessException ： 业务异常
        * NotAuthorityException : 未授权异常
        * NotLoggedInException ：未登录异常
        * TokenCodeIllegalException : Token非法异常
        
## com.giants.common.fastjson.FastJson
FastJson 统一配置简化 FastJson 调用，注入fastJsonConfig 与spring mvc 统一FastJson配置

## com.giants.common.file.ImageUtil
图片处理工具类，包含图片缩放、添加水印 等功能

## com.giants.common.lang
工具类包，具体工具类如下:
* ChineseStringUtil : 中文字符工具类
* JavaUtil : JAVA工具类，主要包含 对象与字符串处理等功能
* NamingConventionsUtils : 命名转换工具类
* NumberUtils : 数字处理工具类
* StringUtil : 字符串处理工具类

## com.giants.common.lang.reflect.ReflectUtils
反射工具类，将反射结果缓存在内存中，提升级 反射调用性能

## com.giants.common.tools.moveorderservice.MoveOrderService
移动排序服务接口，支持 上移、下移、置顶(移至第一个)、置底(移至最后一个)
AbstractMoveOrderService 实现了默认的排序功能

## com.giants.common.tools.CategoryCodeTools
层级编码工具类，例如 地区、行业等多级树型结构编码生成工具类

## com.giants.common.tools.PageQueryCondition<T\>
分布查询对象 泛型T 指定查询条件对象，PageCondition 使用Map作为查询条件对象

## com.giants.common.SpringContextHelper
spring上下文工具类


