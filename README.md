# giants-common

[![Maven Central](https://img.shields.io/maven-central/v/com.github.vencent-lu/giants-common.svg)](https://central.sonatype.com/artifact/com.github.vencent-lu/giants-common)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

面向 Java 后端项目的公用类库，覆盖 Bean 拷贝、加解密、异常体系、分页、字符串/数字/中文处理、反射缓存、图片处理、层级编码、排序服务，以及 Spring 与 FastJSON 集成等常用能力。

- JDK 8+，基于 Maven 构建
- 详尽的用法与示例见 **[使用手册 USAGE.md](USAGE.md)**

## 快速开始

```xml
<dependency>
  <groupId>com.github.vencent-lu</groupId>
  <artifactId>giants-common</artifactId>
  <version>1.3.2</version>
</dependency>
```

```java
// Bean 拷贝
BeanUtils.copyProperties(userVO, userEntity);

// MD5 摘要
String sign = EncryptionTools.MD5("data" + secret);

// 分页查询条件
PageQueryCondition<UserFilter> condition =
        new PageQueryCondition<>(filter, 1, 20, "createTime", "DESC");

// 抛出业务异常（携带 i18n 消息 key）
throw new BusinessException("ORDER_NOT_FOUND", "order.notfound", orderId);
```

> `fastjson`、`spring-beans`、`spring-context` 为 optional 依赖，使用 `FastJson`、`SpringContextHelper` 时需自行引入。

## 模块一览

| 模块 | 入口类 | 说明 | 手册 |
|------|--------|------|------|
| Bean 工具 | `beanutils.BeanUtils` / `PropertyUtils` | 多属性/集合拷贝、Bean↔Map 互转、忽略 null 拷贝 | [↗](USAGE.md#1-bean-工具-beanutils--propertyutils) |
| 加解密 | `codec.EncryptionDevice` | MD5、DES、RSA 加解密，Base64/Hex 编码 | [↗](USAGE.md#2-加解密-codec) |
| 异常体系 | `exception.GiantsException` | 统一错误码 + i18n 消息 key 的异常层级 | [↗](USAGE.md#3-异常体系-exception) |
| 分页 | `tools.PageQueryCondition` / `tools.Page` | 分页查询条件与结果封装 | [↗](USAGE.md#4-分页-page--pagequerycondition) |
| 语言工具 | `lang.StringUtil` / `NumberUtils` / `ChineseStringUtil` / `NamingConventionsUtils` | 字符串、数字、中文、命名转换 | [↗](USAGE.md#5-语言工具-lang) |
| 反射工具 | `lang.reflect.ReflectUtils` / `FieldUtils` | 带缓存的反射，提升调用性能 | [↗](USAGE.md#6-反射工具-reflect) |
| 集合工具 | `collections.CollectionUtils` | 扩展 Apache Commons 集合工具 | [↗](USAGE.md#7-集合工具-collectionutils) |
| 图片处理 | `file.ImageUtil` | 缩放、裁剪、格式转换、灰度、水印 | [↗](USAGE.md#8-图片处理-imageutil) |
| 层级编码 | `tools.CategoryCodeTools` | 树形多级编码生成与解析 | [↗](USAGE.md#9-层级编码-categorycodetools) |
| 移动排序 | `tools.moveorderservice.MoveOrderService` | 上移、下移、置前、置后 | [↗](USAGE.md#10-移动排序-moveorderservice) |
| 关系实体比较 | `tools.db.relationship.RelationEntityCompare` | 新旧集合对比出增/删/改 | [↗](USAGE.md#11-关系实体比较-relationentity) |
| 值提取 | `tools.ValueExtractor` | 从对象列表批量抽取属性值 | [↗](USAGE.md#12-值提取-valueextractor) |
| 断言 | `Assert` | 参数校验断言 | [↗](USAGE.md#13-断言-assert) |
| Spring 集成 | `SpringContextHelper` | 静态获取 Bean 与 i18n 消息 | [↗](USAGE.md#14-spring-集成-springcontexthelper) |
| FastJSON 集成 | `fastjson.FastJson` | 统一 FastJSON 配置与调用 | [↗](USAGE.md#15-fastjson-集成-fastjson) |

## 错误码速查

| 错误码 | 异常类 | 含义 |
|--------|--------|------|
| 1 | `NotLoggedInException` | 未登录 |
| 2 | `DataValidationException` | 数据校验失败 |
| 3 | `BusinessException` | 业务异常 |
| 4 | `NotAuthorityException` | 未授权 |
| 5 | `DataOperationException` | 数据操作异常 |
| 6 | `ViewException` | 视图层异常 |
| 7 | `TokenCodeIllegalException` | Token 非法 |
| 127 | `SystemException` | 系统未知异常 |

## 许可证

[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
