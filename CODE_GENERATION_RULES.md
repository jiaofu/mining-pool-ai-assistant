# 代码生成规则

为了保证代码质量和可维护性，我们制定了一些代码生成规则。这些规则旨在确保生成的代码符合我们的编码规范，并且易于管理和扩展。



## ✅ 必须事项

### 打的日志必须包含方法名字

**原因**：
- 方便排查问题
- 方便日志监控





## ❌ 禁止事项

### 1. 禁止生成内部类

**原因**：
- 不方便移动代码
- 不利于代码复用
- IDE 重构不友好
- 增加维护难度

**错误示例**：
```java
public class MyTest {
    // ❌ 错误：不要生成内部类
    private static class MyConfig {
        private String value;
    }
    
    // ❌ 错误：不要生成内部类
    public static class MyResult {
        private String data;
    }
}
```

**正确示例**：
```java
// ✅ 正确：创建独立文件
// MyConfig.java
package com.example.vo;

public class MyConfig {
    private String value;
}

// MyTest.java
package com.example.test;

import com.example.vo.MyConfig;

public class MyTest {
    // 使用独立的类
    private MyConfig config;
}
```

---

### 2. VO 类必须放在独立文件中

**位置规范**：
```
com.binance.pool.service/
├── vo/
│   ├── zec/          ZEC 相关 VO
│   ├── btc/          BTC 相关 VO
│   └── ...
└── test/
    └── MyTest.java   测试类
```

**示例**：
```java
// ✅ 正确：VO 放在 vo 包中
package com.binance.pool.service.vo.zec;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlphaRkPair {
    private String alpha;
    private String rk;
}
```

---

### 3. 配置类必须放在独立文件中

**位置规范**：
```
com.binance.pool.service/
└── vo/
    └── zec/
        ├── ShieldingConfig.java
        └── DeshieldingConfig.java
```

---

## ✅ 推荐做法

### 1. 使用 FastJSON 的 JSONObject 解析复杂 JSON

**场景**：临时解析 RPC 响应，不需要定义类

**示例**：
```java
// ✅ 正确：使用 JSONObject，不创建内部类
private String parseResponse(String response) {
    com.alibaba.fastjson.JSONObject json = JSON.parseObject(response);
    Object result = json.get("result");
    return JSON.toJSONString(result);
}
```

**不要这样做**：
```java
// ❌ 错误：不要为临时解析创建内部类
private static class ResponseWrapper {
    private Object result;
    private Object error;
}

private String parseResponse(String response) {
    ResponseWrapper wrapper = JSON.parseObject(response, ResponseWrapper.class);
    return JSON.toJSONString(wrapper.getResult());
}
```

---

### 2. 使用 Map 存储临时数据

**示例**：
```java
// ✅ 正确：使用 Map
Map<String, Object> data = new HashMap<>();
data.put("key", "value");

// ❌ 错误：不要为临时数据创建内部类
private static class TempData {
    private String key;
    private String value;
}
```

---

### 3. 复用已有的类

**示例**：
```java
// ✅ 正确：使用已有的 VO 类
import com.binance.pool.service.vo.zec.AlphaRkPair;

AlphaRkPair pair = new AlphaRkPair(alpha, rk);

// ❌ 错误：不要重复定义
private static class AlphaRk {  // 已经有 AlphaRkPair 了
    private String alpha;
    private String rk;
}
```

---

## 📋 检查清单

在生成代码之前，检查：

- [ ] 是否有内部类？
- [ ] VO 类是否在独立文件中？
- [ ] 配置类是否在独立文件中？
- [ ] 临时数据是否使用 Map 或 JSONObject？
- [ ] 是否复用了已有的类？

---

## 🎯 总结

1. **永远不要生成内部类**
2. **所有 VO 类必须是独立文件**
3. **临时数据使用 Map 或 JSONObject**
4. **优先复用已有的类**

---

**版本**: V1.0  
**最后更新**: 2026-03-11
