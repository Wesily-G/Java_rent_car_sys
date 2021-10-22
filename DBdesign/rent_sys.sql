/*
 Navicat Premium Data Transfer

 Source Server         : rent_sys
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : rent_sys

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 11/09/2021 10:15:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car_assign_tbl
-- ----------------------------
DROP TABLE IF EXISTS `car_assign_tbl`;
CREATE TABLE `car_assign_tbl`  (
  `car_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `assign_in` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `assign_out` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `assign_time` date NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for car_model_tbl
-- ----------------------------
DROP TABLE IF EXISTS `car_model_tbl`;
CREATE TABLE `car_model_tbl`  (
  `model_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `model_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `model_brand` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `model_emissions` int(0) NOT NULL,
  `model_auto` tinyint(1) NOT NULL,
  `model_seats` int(0) NOT NULL,
  `model_price` double NOT NULL,
  `model_image` longblob NULL,
  PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_model_tbl
-- ----------------------------
INSERT INTO `car_model_tbl` VALUES ('model1', 'type1', 'model1', 'baoma', 432, 1, 3, 13, NULL);
INSERT INTO `car_model_tbl` VALUES ('model2', 'type1', 'model2', 'sdf', 42, 1, 2, 12, NULL);

-- ----------------------------
-- Table structure for car_tbl
-- ----------------------------
DROP TABLE IF EXISTS `car_tbl`;
CREATE TABLE `car_tbl`  (
  `car_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `net_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `model_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `car_license` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `car_state` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`car_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_tbl
-- ----------------------------
INSERT INTO `car_tbl` VALUES ('car1', 'net1', 'model1', '152134', 'idle');
INSERT INTO `car_tbl` VALUES ('car2', 'net1', 'model2', '3121', 'idle');

-- ----------------------------
-- Table structure for car_type_tbl
-- ----------------------------
DROP TABLE IF EXISTS `car_type_tbl`;
CREATE TABLE `car_type_tbl`  (
  `type_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type_describe` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_type_tbl
-- ----------------------------
INSERT INTO `car_type_tbl` VALUES ('type1', 'type1', 'asdf');
INSERT INTO `car_type_tbl` VALUES ('type2', 'type2', 'sadf');

-- ----------------------------
-- Table structure for discard_tbl
-- ----------------------------
DROP TABLE IF EXISTS `discard_tbl`;
CREATE TABLE `discard_tbl`  (
  `stuff_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `car_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `discard_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `discard_time` date NOT NULL,
  `discard_illustrate` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for discount_tbl
-- ----------------------------
DROP TABLE IF EXISTS `discount_tbl`;
CREATE TABLE `discount_tbl`  (
  `discount_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `discount_net` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `discount_model` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `discount` double NOT NULL,
  `discount_count` int(0) NOT NULL,
  `discount_start` date NOT NULL,
  `discount_end` date NOT NULL,
  PRIMARY KEY (`discount_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discount_tbl
-- ----------------------------
INSERT INTO `discount_tbl` VALUES ('dicount1', 'net1', 'model1', 0.7, 11, '2021-09-10', '2021-09-23');

-- ----------------------------
-- Table structure for net_tbl
-- ----------------------------
DROP TABLE IF EXISTS `net_tbl`;
CREATE TABLE `net_tbl`  (
  `net_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `net_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `net_city` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `net_address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `net_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`net_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of net_tbl
-- ----------------------------
INSERT INTO `net_tbl` VALUES ('net1', 'net1', 'shanghai', 'shanghai', '234123');
INSERT INTO `net_tbl` VALUES ('net2', 'net2', 'beijing', 'beijing', '13413413');

-- ----------------------------
-- Table structure for order_tbl
-- ----------------------------
DROP TABLE IF EXISTS `order_tbl`;
CREATE TABLE `order_tbl`  (
  `user_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `car_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_rent_net` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_rent_time` date NULL DEFAULT NULL,
  `order_return_net` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `order_return_time` date NULL DEFAULT NULL,
  `order_time` int(0) NULL DEFAULT NULL,
  `order_origin_price` double NULL DEFAULT NULL,
  `order_result_price` double NULL DEFAULT NULL,
  `order_prefer_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_tbl
-- ----------------------------
INSERT INTO `order_tbl` VALUES ('user32', 'car1', 'order1', 'net1', '2021-09-11', 'net2', '2021-09-11', NULL, 100, 100, 'prefer1', 'finish');
INSERT INTO `order_tbl` VALUES ('user32', 'car1', 'order2', 'net1', '2021-09-11', 'net2', '2021-09-11', NULL, 100, 100, 'prefer1', 'finish');
INSERT INTO `order_tbl` VALUES ('user32', 'car1', 'order3', 'net1', '2021-09-11', 'net1', '2021-09-11', NULL, NULL, NULL, 'prefer1', 'finish');

-- ----------------------------
-- Table structure for prefer_tbl
-- ----------------------------
DROP TABLE IF EXISTS `prefer_tbl`;
CREATE TABLE `prefer_tbl`  (
  `prefer_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prefer_net` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prefer_model` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prefer_amount` double NOT NULL,
  `prefer_start` date NOT NULL,
  `prefer_end` date NOT NULL,
  PRIMARY KEY (`prefer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prefer_tbl
-- ----------------------------
INSERT INTO `prefer_tbl` VALUES ('prefer1', 'net1', 'model1', 12, '2021-09-11', '2021-09-21');

-- ----------------------------
-- Table structure for stuff_tbl
-- ----------------------------
DROP TABLE IF EXISTS `stuff_tbl`;
CREATE TABLE `stuff_tbl`  (
  `stuff_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `net_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stuff_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `stuff_password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`stuff_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stuff_tbl
-- ----------------------------
INSERT INTO `stuff_tbl` VALUES ('stuff1', 'net1', 'stuff1', '123');
INSERT INTO `stuff_tbl` VALUES ('stuff12', NULL, 'stuff12', '12');

-- ----------------------------
-- Table structure for user_tbl
-- ----------------------------
DROP TABLE IF EXISTS `user_tbl`;
CREATE TABLE `user_tbl`  (
  `user_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_sex` tinyint(1) NOT NULL,
  `user_password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_mail` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_city` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_register_time` date NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_tbl
-- ----------------------------
INSERT INTO `user_tbl` VALUES ('user12', '12', 1, '12', '12', '12', '12', '2021-09-11');
INSERT INTO `user_tbl` VALUES ('user3', '张三', 1, '123', '110', '110@110.com', 'beijing', '2021-09-05');
INSERT INTO `user_tbl` VALUES ('user32', '李四', 1, '32', '126', '12@120.com', 'sz', '2021-09-04');

-- ----------------------------
-- View structure for state_tbl
-- ----------------------------
DROP VIEW IF EXISTS `state_tbl`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `state_tbl` AS select `a`.`car_id` AS `car_id`,`a`.`car_state` AS `car_state`,`b`.`model_name` AS `model_name`,`c`.`net_name` AS `net_name` from ((`car_tbl` `a` left join `car_model_tbl` `b` on((`a`.`model_id` = `b`.`model_id`))) left join `net_tbl` `c` on((`a`.`net_id` = `c`.`net_id`)));

SET FOREIGN_KEY_CHECKS = 1;
