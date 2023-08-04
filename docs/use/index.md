---
# 同时设置导航名称和顺序，order 越小越靠前，默认为 0
group: 
  title: 使用文档
  order: 2
order: 1
title: 使用
---

在这里介绍如何使用SimpleRebac

SQL脚本

```sql

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rebac_key
-- ----------------------------
DROP TABLE IF EXISTS `rebac_key`;
CREATE TABLE `rebac_key`  (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT 'app的id',
                              `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rebac_model
-- ----------------------------
DROP TABLE IF EXISTS `rebac_model`;
CREATE TABLE `rebac_model`  (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '模型的id',
                                `store_id` int NOT NULL COMMENT '模型对应store的id',
                                `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的名称',
                                `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的描述',
                                `policy_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型的权限策略id（mongodb）',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rebac_relation
-- ----------------------------
DROP TABLE IF EXISTS `rebac_relation`;
CREATE TABLE `rebac_relation`  (
                                   `id` int NOT NULL AUTO_INCREMENT COMMENT '关系id',
                                   `model_id` int NOT NULL COMMENT '对应model的Id',
                                   `object_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象类型',
                                   `object` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象',
                                   `relation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关系',
                                   `subject_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主体类型',
                                   `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联主体',
                                   `triple` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关系定义的dsl，如document:doc1#reader@user:user1',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rebac_store
-- ----------------------------
DROP TABLE IF EXISTS `rebac_store`;
CREATE TABLE `rebac_store`  (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '存储id',
                                `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'store名称',
                                `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'store描述',
                                `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'appKey',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```
