/*
 Navicat Premium Data Transfer

 Source Server         : 10.50.40.145
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 10.50.40.145:3306
 Source Schema         : dodo_db

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 12/04/2022 22:17:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for v7_test_gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `v7_test_gateway_route`;
CREATE TABLE `v7_test_gateway_route`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `app_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'APP ID',
  `route_Id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由key',
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转发URL',
  `predicates` json NOT NULL COMMENT '断言数据',
  `filters` json NOT NULL COMMENT '过滤数据',
  `order_num` int(1) NULL DEFAULT NULL COMMENT '顺序',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由描述',
  `create_time` bigint(10) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `modify_time` bigint(10) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人ID',
  `delete_flag` bit(1) NULL DEFAULT NULL COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '<dodo-server-app-manager>上架应用路由信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
