# giants-common 使用手册

`giants-common` 是一套面向 Java 后端项目的公用类库，覆盖 Bean 拷贝、加解密、异常体系、分页、字符串/数字/中文处理、反射缓存、图片处理、层级编码、排序服务、Spring 与 FastJSON 集成等常用能力。

本手册按模块组织，给出每个工具类的用途、核心 API 与可运行的示例代码。快速索引见 [README.md](README.md)。

---

## 目录

- [环境与依赖](#环境与依赖)
- [1. Bean 工具 BeanUtils / PropertyUtils](#1-bean-工具-beanutils--propertyutils)
- [2. 加解密 codec](#2-加解密-codec)
- [3. 异常体系 exception](#3-异常体系-exception)
- [4. 分页 Page / PageQueryCondition](#4-分页-page--pagequerycondition)
- [5. 语言工具 lang](#5-语言工具-lang)
- [6. 反射工具 reflect](#6-反射工具-reflect)
- [7. 集合工具 CollectionUtils](#7-集合工具-collectionutils)
- [8. 图片处理 ImageUtil](#8-图片处理-imageutil)
- [9. 层级编码 CategoryCodeTools](#9-层级编码-categorycodetools)
- [10. 移动排序 MoveOrderService](#10-移动排序-moveorderservice)
- [11. 关系实体比较 RelationEntity](#11-关系实体比较-relationentity)
- [12. 值提取 ValueExtractor](#12-值提取-valueextractor)
- [13. 断言 Assert](#13-断言-assert)
- [14. Spring 集成 SpringContextHelper](#14-spring-集成-springcontexthelper)
- [15. FastJSON 集成 FastJson](#15-fastjson-集成-fastjson)

---

## 环境与依赖

- JDK 8+（编译目标为 Java 8）
- 构建工具：Maven

引入坐标：

```xml
<dependency>
  <groupId>com.github.vencent-lu</groupId>
  <artifactId>giants-common</artifactId>
  <version>1.3.2</version>
</dependency>
```

必备传递依赖：`commons-beanutils`、`commons-lang`、`commons-collections`、`oro`、`slf4j-api`。

以下依赖被标记为 `optional`，用到相应模块时需自行引入：

- `com.alibaba:fastjson` —— 使用 [`FastJson`](#15-fastjson-集成-fastjson) 时需要。
- `org.springframework:spring-beans`、`spring-context` —— 使用 [`SpringContextHelper`](#14-spring-集成-springcontexthelper) 时需要。

---

## 1. Bean 工具 BeanUtils / PropertyUtils

`com.giants.common.beanutils.BeanUtils` 继承 Apache Commons `BeanUtils`，增加了多属性选择拷贝、集合批量拷贝、Bean 与 Map 互转、忽略 null 拷贝等能力，并内置了对 `Long/Short/Integer/Double/BigDecimal/Date` 的类型转换器。

常用静态方法：

| 方法 | 说明 |
|------|------|
| `copyProperties(dest, orig)` | 拷贝全部同名属性 |
| `copyProperties(dest, orig, String... propertyNames)` | 只拷贝指定属性 |
| `copyPropertiesIgnoreNull(dest, orig)` | 只拷贝源对象中非 null 的属性（适合做增量更新） |
| `copyCollectionProperties(dest, orig, copyToClass)` | 集合逐元素拷贝为目标类型的新实例 |
| `copyCollectionProperties(dest, orig, copyToClass, propertyNames)` | 集合逐元素按指定属性拷贝 |
| `copyBeanPropertiesToMap(dest, orig, propertyNames)` | Bean 转 Map，支持嵌套语法 `prop:{a,b}` |
| `copyMapPropertiesToBean(dest, orig)` | Map 转 Bean，递归处理嵌套 Map |
| `copyCollectionPropertiesToMap(dest, orig, propertyNames)` | 集合转 Map 列表 |
| `getNotNullPropertyNames(source)` | 返回值非 null 的属性名数组 |
| `getPropertyValue(bean, name)` / `getPropertyType(bean, name)` | 取属性值 / 属性类型（兼容 Map） |

示例：

```java
// 1) DTO 与实体之间拷贝
UserVO vo = new UserVO();
BeanUtils.copyProperties(vo, userEntity);

// 2) 只拷贝部分字段
BeanUtils.copyProperties(vo, userEntity, "id", "name", "email");

// 3) 增量更新：只覆盖请求里传了值的字段
BeanUtils.copyPropertiesIgnoreNull(existingEntity, updateRequest);

// 4) 集合批量转换 List<UserEntity> -> List<UserVO>
List<UserVO> voList = new ArrayList<>();
BeanUtils.copyCollectionProperties(voList, userEntityList, UserVO.class);

// 5) Bean 转 Map，含嵌套属性
Map<String, Object> map = new HashMap<>();
BeanUtils.copyBeanPropertiesToMap(map, order, "id", "amount", "user:{id,name}");
```

`com.giants.common.beanutils.PropertyUtils.getProperty(bean, name)` 提供轻量的单属性读取，若 bean 是 `Map` 则退化为 `Map.get`。

---

## 2. 加解密 codec

### 2.1 EncryptionDevice

`com.giants.common.codec.EncryptionDevice` 是加解密的统一入口，支持 **MD5 摘要**、**DES 对称加密**、**RSA 非对称加密**，并可选择 Base64 / Hex / URL-Safe Base64 编码输出。全部为静态方法。

**MD5 摘要：**

```java
String md5Hex    = EncryptionDevice.md5ByHexCode("password");     // 十六进制
String md5Base64 = EncryptionDevice.md5ByBase64Code("password");  // Base64
```

也可用更简单的 `com.giants.common.tools.EncryptionTools.MD5(String)`，直接返回 32 位小写十六进制串：

```java
String sign = EncryptionTools.MD5("data" + secret);
```

**DES 对称加密**（key 为共享密钥字符串）：

```java
String cipher = EncryptionDevice.encryptDesByBase64Code("hello", "myKey123");
String plain  = EncryptionDevice.decryptDesByBase64Code(cipher, "myKey123");

// 用于 URL 传参的安全 Base64
String urlCipher = EncryptionDevice.encryptDesByBase64CodeForUrl("hello", "myKey123");
String urlPlain  = EncryptionDevice.decryptDesByBase64CodeForUrl(urlCipher, "myKey123");

// 十六进制输出
String hexCipher = EncryptionDevice.encryptDesByHexCode("hello", "myKey123");
```

**RSA 非对称加密**：

```java
// 生成密钥对（常用 1024 / 2048 位）
RSAKeyPair keyPair = EncryptionDevice.generateRSAKeyPair(2048);
PublicKey  publicKey  = keyPair.getPublicKey();
PrivateKey privateKey = keyPair.getPrivateKey();

// 公钥加密、私钥解密
String cipher = EncryptionDevice.encryptRsaByBase64Code("secret", publicKey);
String plain  = EncryptionDevice.decryptRsaByBase64Code(cipher, privateKey);
```

`RSAKeyPair` / `PublicKey` / `PrivateKey` 均实现 `Serializable`，可持久化或缓存。大于密钥块长度的数据会自动分段处理。

### 2.2 编码工具 Base64 / Hex

`com.giants.common.codec.Base64` 支持标准、URL-Safe、有序三种字典及是否换行：

```java
String s   = Base64.encodeByString("data".getBytes());   // 标准 Base64
String url = Base64.encodeForUrl("data".getBytes());      // URL-Safe
boolean ok = Base64.isBase64(bytes);                       // 校验
```

`com.giants.common.codec.Hex` 负责字节数组与十六进制串互转：

```java
String hex    = Hex.encodeByString(bytes);
byte[] bytes2 = Hex.decodeByString(hex);
```

---

## 3. 异常体系 exception

所有异常继承自抽象类 `com.giants.common.exception.GiantsException`（`RuntimeException` 的子类），统一携带 **国际化消息 key（errorMessageKey）**、**消息参数（messageArgs）** 与 **错误码（buildErrorCode）**，便于与 Spring `MessageSource` 结合做多语言错误提示。

继承结构与错误码（定义于 `GiantsConstants`）：

```
GiantsException (abstract)
├─ DataOperationException      错误码 5   数据操作（增删改查）异常
├─ DataValidationException     错误码 2   数据校验失败，可累积字段级错误
├─ ViewException               错误码 6   视图层异常
├─ SystemException             错误码 127 系统未知异常
└─ BusinessException           错误码 3   业务异常
   ├─ NotLoggedInException     错误码 1   未登录
   ├─ NotAuthorityException    错误码 4   未授权
   └─ TokenCodeIllegalException 错误码 7  Token 非法/过期
```

`BusinessException` 及其子类构造函数带 `errorKey`（业务错误标识）与 `errorMessageKey`（i18n 消息 key）：

```java
// 业务异常
throw new BusinessException("ORDER_NOT_FOUND", "order.notfound", orderId);

// 未登录 / 未授权
throw new NotLoggedInException("NOT_LOGIN", "user.notLogin");
throw new NotAuthorityException("NO_PERMISSION", "user.noPermission");
```

`DataValidationException` 可累积多个字段错误，适合表单校验：

```java
DataValidationException ex = new DataValidationException("validation.failed");
ex.addFieldError(ex.createFieldError("email", "email.invalid", null));
ex.addFieldError(ex.createFieldError("age", "age.range", new Object[]{18, 60}));
if (!ex.getFieldErrors().isEmpty()) {
    throw ex;
}
```

统一处理时可用 `buildErrorCode()` 得到错误码、`getErrorMessageKey()` + `getMessageArgs()` 交给 `MessageSource` 渲染（见 [SpringContextHelper.getMessage](#14-spring-集成-springcontexthelper)）。

---

## 4. 分页 Page / PageQueryCondition

### 4.1 PageQueryCondition\<T> —— 查询条件

`com.giants.common.tools.PageQueryCondition<T>` 封装分页查询入参，泛型 `T` 为过滤条件对象。页码从 1 开始，默认每页 10 条，支持单列排序，并自动把 `orderBy` 的驼峰转下划线（便于拼 SQL）。

```java
// 以 UserFilter 作为查询条件
UserFilter filter = new UserFilter();
filter.setStatus("ACTIVE");

PageQueryCondition<UserFilter> condition =
        new PageQueryCondition<>(filter, 2, 20, "createTime", "DESC");

condition.getFilters();      // UserFilter 实例
condition.getStartOffSet();  // (2-1)*20 = 20，用于 SQL OFFSET
condition.getEndOffSet();    // 20，用于 SQL LIMIT
condition.getOrderBy();      // "create_time"（自动转下划线）
condition.getOrderSort();    // "DESC"
```

`com.giants.common.tools.PageCondition` 是 `PageQueryCondition<Map<String,Object>>` 的历史别名（已不推荐，用 Map 承载条件）。

### 4.2 Page\<T> —— 查询结果

`com.giants.common.tools.Page<T>` 承载单页结果与分页元数据，`getPageNo()` / `getOffset()` 内置越界保护。

```java
int total = userMapper.count(condition);
List<User> rows = userMapper.query(condition);

Page<User> page = new Page<>(condition.getPageNo(), condition.getPageSize(), total, rows);
page.getTotalPage();  // 总页数
page.getResults();    // 当前页数据
```

---

## 5. 语言工具 lang

### 5.1 StringUtil

`com.giants.common.lang.StringUtil` 提供丰富的字符串处理（全静态）。常用能力：

```java
StringUtil.isEmptyString(s);                 // null 或 trim 后为空
StringUtil.padl("7", 3, '0');                // "007" 左侧补齐
StringUtil.padr("7", 3, '0');                // "700" 右侧补齐
StringUtil.repeat(3, "ab");                  // "ababab"
StringUtil.camelToUnderline("userName");     // "user_name"
StringUtil.underlineToCamel("user_name");    // "userName"
StringUtil.capitalize("name");               // "Name"
StringUtil.isNumber("-12.5");                // true
StringUtil.toList("a, b, c");                // [a, b, c]
StringUtil.collectionToString(list, ",");    // 集合转分隔串
// 用 Bean 属性替换 ${xxx} 占位符
StringUtil.replaceTokensByBeanProperties("Hi ${name}", user);
```

### 5.2 NumberUtils

`com.giants.common.lang.NumberUtils` 主要做金额格式化，保留两位小数，null 返回 `"0.00"`：

```java
NumberUtils.formatMoney(1234.5D);                    // "1234.50"
NumberUtils.formatMoney(new BigDecimal("9.999"));    // "10.00"
```

### 5.3 ChineseStringUtil

`com.giants.common.lang.ChineseStringUtil` 按显示宽度（中文算 2）处理字符串：

```java
ChineseStringUtil.length("中文abc");            // 中文按 2 计
ChineseStringUtil.subString("中文abc", 0, 4);   // 按显示宽度截取
ChineseStringUtil.splitToList("长文本…", 10);   // 按宽度切块
```

### 5.4 NamingConventionsUtils

`com.giants.common.lang.NamingConventionsUtils` 处理命名转换：

```java
NamingConventionsUtils.initialsLowercase("UserName");   // "userName"
NamingConventionsUtils.getSettingMethodName("name");     // "setName"
```

---

## 6. 反射工具 reflect

`com.giants.common.lang.reflect.ReflectUtils` 将反射结果缓存于内存，显著提升重复反射调用性能。全部为静态方法：

```java
Class<?> clazz = ReflectUtils.classForName("java.util.Date"); // 支持基本类型名
Method[] methods = ReflectUtils.getMethods(clazz);
Method m = ReflectUtils.getMethod(clazz, "getTime");
PropertyDescriptor pd = ReflectUtils.getPropertyDescriptor(User.class, "name");
List<Class<?>> supers = ReflectUtils.getAllSuperclasses(User.class);
```

`com.giants.common.lang.reflect.FieldUtils` 继承 Apache Commons，新增 `getAvailableFields(Class)`，返回类及其所有父类的字段（去除被子类覆盖的同名字段）：

```java
Field[] fields = FieldUtils.getAvailableFields(User.class);
```

---

## 7. 集合工具 CollectionUtils

`com.giants.common.collections.CollectionUtils` 继承 Apache Commons，新增可变参数的并集：

```java
Collection<?> all = CollectionUtils.union(list1, list2, list3);
```

---

## 8. 图片处理 ImageUtil

`com.giants.common.file.ImageUtil` 提供缩放、定尺缩放（可留白）、裁剪、格式转换、灰度化、文字水印、图片水印等能力。每类操作都提供多种输入/输出重载（`File` / `InputStream` / `BufferedImage` / 文件名），并可直接返回 `BufferedImage` 或写入目标文件。

```java
// 按比例缩放并保存
ImageUtil.scale("in.jpg", "out.jpg", 0.5, "jpg");

// 定尺缩放到 200x200，不足处留白
ImageUtil.zoom("in.jpg", "thumb.jpg", 200, 200, "jpg", true);

// 裁剪：从 (10,10) 截取 100x100
ImageUtil.cropping("in.jpg", "crop.jpg", 10, 10, 100, 100, "jpg");

// 格式转换 png -> jpg
ImageUtil.convert("in.png", "jpg", "out.jpg");

// 灰度化
ImageUtil.gray("in.jpg", "gray.jpg", "jpg");

// 文字水印
ImageUtil.watermarkText("in.jpg", "out.jpg", "版权所有",
        "宋体", Font.BOLD, Color.GRAY, 24, 0, 0, 0.5f, "jpg");

// 图片水印
ImageUtil.watermarkImage("in.jpg", "out.jpg", new File("logo.png"),
        0, 0, 0.5f, "jpg");
```

水印方法中 `x` / `y` 为相对居中的偏移，`alpha` 为透明度（0.0~1.0）。

---

## 9. 层级编码 CategoryCodeTools

`com.giants.common.tools.CategoryCodeTools` 用于生成/解析树形层级编码（如地区、行业等）。格式字符串为 `"默认段长:第1级长,第2级长,..."`，例如 `"2:2,3,2"` 表示默认段长 2，第 1 级 2 位、第 2 级 3 位、第 3 级 2 位。根节点用空串表示。

```java
CategoryCodeTools tools = new CategoryCodeTools("2:2,2,2");

// 根下第一个子级编码
String first = tools.getNextLevelFirstCode(null);   // "01"
// 同级下一个编码
String next  = tools.getPlusOneCode("01");           // "02"
// 某编码的下一级第一个
String child = tools.getNextLevelFirstCode("02");    // "0201"
// 解析层级
int level = tools.getLevel("0201");                  // 2
// 取所有祖先+自身编码
String[] chain = tools.getAllLevelCodes("0201");     // ["02", "0201"]
```

配合 `CategoryCodeType` 接口（实现 `getCodeFormat()`）与 `CategoryCodeToolsHelper.getCategoryCodeTools(type)` 可按类型缓存工具实例，避免重复构造：

```java
public enum RegionCodeType implements CategoryCodeType {
    INSTANCE;
    @Override public String getCodeFormat() { return "2:2,2,2,3"; }
}

CategoryCodeTools tools =
        CategoryCodeToolsHelper.getCategoryCodeTools(RegionCodeType.INSTANCE);
```

---

## 10. 移动排序 MoveOrderService

`com.giants.common.tools.moveorderservice` 提供实体的上移、下移、移到某元素之前/之后的排序服务。使用步骤：

1. 在实体的排序字段上标注 `@Order`；如为树形结构，在父级引用字段上标注 `@Parent`。
2. 继承 `AbstractMoveOrderService<T>`，实现 4 个数据访问抽象方法。

```java
public class Menu {
    @Order
    private Integer sortNo;
    @Parent
    private Menu parent;
    // getter / setter ...
}

public class MenuMoveOrderService extends AbstractMoveOrderService<Menu> {

    @Override protected void updateEntity(Menu entity) {
        menuMapper.updateSortNo(entity);
    }
    @Override protected List<Menu> findNeedModifyOrderEntity(
            Object parent, Integer beginOrder, Integer endOrder) {
        return menuMapper.findByOrderRange(parent, beginOrder, endOrder);
    }
    @Override protected Integer findCurrentMaxOrder(Object parent) {
        return menuMapper.findMaxOrder(parent);
    }
    @Override protected Menu findEntityByOrder(Object parent, Integer order) {
        return menuMapper.findByOrder(parent, order);
    }
}
```

调用：

```java
MenuMoveOrderService service = new MenuMoveOrderService();
service.moveUp(menu);                 // 上移一位（在顶部则回绕到底部）
service.moveDown(menu);               // 下移一位
service.movesToFront(menuA, menuB);   // 把 A 移到 B 之前
service.movesToBack(menuA, menuB);    // 把 A 移到 B 之后
```

构造时会通过反射解析 `@Order` / `@Parent` 注解定位 getter/setter，无需手动配置。可覆写 `checkMovesLegitimacy` 增加移动约束。

---

## 11. 关系实体比较 RelationEntity

`com.giants.common.tools.db.relationship` 用于对比两个有序实体集合（新旧关系），得出需新增、删除、修改的实体，常用于「保存时同步关联表」的场景。

`RelationEntityCompare.compare(source, target)` 返回 `ComparableResult`，再交给 `RelationEntityHandler` 执行落库：

```java
public class RolePermissionHandler extends RelationEntityHandler<Permission> {
    @Override protected void add(Permission p)    { permMapper.insert(p); }
    @Override protected void remove(Permission p) { permMapper.delete(p); }
    // 需要更新时可覆写 modify(source, target)
}

ComparableResult<Permission> result =
        RelationEntityCompare.compare(oldPermissions, newPermissions);
int affected = new RolePermissionHandler().handle(result);
```

`handle()` 会自动对删除项调用 `remove`、新增项调用 `add`、共存项调用 `modify`，并返回处理总数。实体需实现 `Comparable`。

---

## 12. 值提取 ValueExtractor

`com.giants.common.tools.ValueExtractor<V, O>` 从对象列表中批量抽取某属性值，自动跳过 null：

```java
List<Long> ids = new ValueExtractor<Long, User>() {
    @Override public Long extractValue(User u) { return u.getId(); }
}.extractFrom(userList);
```

---

## 13. 断言 Assert

`com.giants.common.Assert` 用于参数校验，条件不满足时抛 `IllegalArgumentException`：

```java
Assert.notNull(user, "user 不能为空");
Assert.isTrue(amount > 0, "金额必须大于 0");
Assert.isNull(existing, "记录已存在");
```

---

## 14. Spring 集成 SpringContextHelper

`com.giants.common.SpringContextHelper` 实现 `ApplicationContextAware`，把它注册为 Bean 后即可在任意位置（含非 Spring 管理的类）静态获取 Bean 与国际化消息。

配置：

```xml
<bean class="com.giants.common.SpringContextHelper"/>
```

使用：

```java
// 按类型 / 名称取 Bean
UserService service = SpringContextHelper.getSpringBean(UserService.class);
Object bean = SpringContextHelper.getSpringBean("userService");

// 取国际化消息（依赖容器内的 MessageSource）
String msg = SpringContextHelper.getMessage("order.notfound", orderId);
```

这与异常体系天然契合：捕获 `GiantsException` 后用 `getErrorMessageKey()` + `getMessageArgs()` 交给 `getMessage(...)` 渲染最终提示。

---

## 15. FastJSON 集成 FastJson

`com.giants.common.fastjson.FastJson` 对 FastJSON 调用做统一封装：注入一个 `FastJsonConfig`（可与 Spring MVC 的 `FastJsonHttpMessageConverter` 复用同一份配置），使序列化/反序列化行为（日期格式、过滤器、特性开关等）全局一致。

配置：

```xml
<bean id="fastJsonConfig" class="com.alibaba.fastjson.support.config.FastJsonConfig">
    <property name="dateFormat" value="yyyy-MM-dd HH:mm:ss"/>
    <!-- 其余 serializeFilters / features 按需配置 -->
</bean>

<bean id="fastJson" class="com.giants.common.fastjson.FastJson">
    <property name="fastJsonConfig" ref="fastJsonConfig"/>
</bean>
```

使用：

```java
@Autowired
private FastJson fastJson;

String json = fastJson.toJSONString(order);
Order back  = fastJson.parseObject(json, Order.class);
JSONObject obj = fastJson.parseObject(json);
Object any = fastJson.parse(json);
```

> 需要引入 `com.alibaba:fastjson`（在本库中为 optional 依赖）。

---

## 附：错误码速查表

| 错误码 | 常量（GiantsConstants） | 异常类 | 含义 |
|--------|--------------------------|--------|------|
| 0   | ERROR_CODE_SUCCESS | — | 成功 |
| 1   | ERROR_CODE_NOT_LOGGED_IN | NotLoggedInException | 未登录 |
| 2   | ERROR_CODE_DATA_CHECK_FAILURE | DataValidationException | 数据校验失败 |
| 3   | ERROR_CODE_BUSINESS_EXCEPTION | BusinessException | 业务异常 |
| 4   | ERROR_CODE_NOT_AUTHORITY | NotAuthorityException | 未授权 |
| 5   | ERROR_CODE_DATA_OPERATION_EXCEPTION | DataOperationException | 数据操作异常 |
| 6   | ERROR_CODE_VIEW_EXCEPTION | ViewException | 视图层异常 |
| 7   | ERROR_CODE_TOKEN_EXCEPTION | TokenCodeIllegalException | Token 非法 |
| 127 | ERROR_CODE_SYSTEM_ERROR | SystemException | 系统未知异常 |




