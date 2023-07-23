开发进度见Process.md文档

## SimpleRebac设计文档

 以下是Zanzibar整体架构

![img](https://docs.qingque.cn/image/api/external/load/out?code=eZQBfAdmstOCT_QgVHRj_9D98:-4835615104140032079eZQBfAdmstOCT_QgVHRj_9D98:1690096996361&identityId=1zDXFsi49lD)

在开始设计SimpleRebac之前，需要先理清楚模块，需要考虑的模块有

- 安全模块
- 存储模块
- web服务模块
- 主要逻辑实现模块

安全模块主要实现鉴权，控制访问权限，并能够发放appKey与secretKey

存储模块实现存储key、鉴权策略存储、三元组存储，建立对象之间的关联关系（图模型）

web服务主要用于对外服务，包括Http服务、RPC服务

主要逻辑实现模块要实现鉴权逻辑

## **存储模块**

### **关键定义**

- Store

一个Store代表一个存储模型，一般情况下App与Store是多对一的关系，因为不同应用之间可能有权限的关联关系

数据库定义：

```sql
CREATE TABLE `rebac_store`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '存储id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'store名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'store描述',
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'appKey',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

- Model

Model代表一个权限模型，一个Store下可以有多个权限模型，用于不同模块或不同策略

数据库定义：

```sql
CREATE TABLE `rebac_model`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '模型的id',
  `store_id` int NOT NULL COMMENT '模型对应store的id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的描述',
  `policy_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的权限策略id（MongoDB）'
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```



其中policy由于是个复杂对象，且可能是嵌套结构很深的对象，因此适合使用MongoDB来存储，policy_id用于关联MongoDB中的对象

- Relation

Relation用于构建对象之间的关系，对象的概念已经在前述Model中描述，Relation只是用于表述对象之间的关系，如user1与document1之间存在阅读或编辑关系

Relation在Zanzibar中通过使用三元组来表述，如`document:document1#reader@user:user1`

```sql
CREATE TABLE `rebac_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `objectType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象类型',
  `object` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象',
  `relation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关系',
  `subjectType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主体类型',
  `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联主体',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

- Model

```sql
CREATE TABLE `rebac_model`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '模型的id',
  `store_id` int NOT NULL COMMENT '模型对应store的id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的描述',
  `policy_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的权限策略id（mongodb）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

### **实体定义**