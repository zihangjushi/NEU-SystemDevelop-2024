/*
 Navicat Premium Data Transfer

 Source Server         : my-dev
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 192.168.91.131:3306
 Source Schema         : t_department

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 03/07/2024 16:51:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_company
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company`  (
  `companyId` int NOT NULL AUTO_INCREMENT,
  `contactName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `companyName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `adminName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` tinyint(1) NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`companyId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_company
-- ----------------------------
INSERT INTO `t_company` VALUES (1, '何广', '上海电气有限公司', '1-526-195-7075', 'Quinlan Valdez', 0, '2023-11-07 14:05:28', '');
INSERT INTO `t_company` VALUES (2, '燕山', '北京红旗研究院', '1-433-535-7882', 'Noelle Brock', 1, '2024-03-26 00:16:44', 'Mauris quis turpis vitae purus gravida sagittis. Duis');
INSERT INTO `t_company` VALUES (3, '滕元', '辽宁科技所', '1-861-323-4850', 'Stella Mullins', 1, '2025-01-04 20:11:52', 'at, libero. Morbi accumsan laoreet');
INSERT INTO `t_company` VALUES (4, '易言定', '鞍山炼钢厂', '(333) 265-5208', 'Nigel Kinney', 0, '2025-01-27 18:36:06', 'Mauris magna. Duis dignissim tempor arcu. Vestibulum');
INSERT INTO `t_company` VALUES (5, '张想想', '山东拖拉机厂', '1-723-867-7952', 'Reese Irwin', 0, '2024-02-29 11:51:13', 'sodales nisi magna sed');
INSERT INTO `t_company` VALUES (6, '王毅', '湖南化肥厂', '(576) 712-8414', 'Craig Marshall', 1, '2024-03-26 08:44:14', 'aliquet. Phasellus fermentum convallis ligula. Donec luctus aliquet');
INSERT INTO `t_company` VALUES (7, '科林特', '德州制药有限公司', '1-293-715-3435', 'Sebastian Wheeler', 1, '2025-02-05 19:39:49', '');
INSERT INTO `t_company` VALUES (35, 'asd', 'asdf', 'asdf', 'asdf', 1, '2024-07-03 04:28:47', '');

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`  (
  `departmentId` int NOT NULL AUTO_INCREMENT,
  `serialId` int NOT NULL,
  `companyId` int NOT NULL,
  `departmentName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `adminName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phoneNumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` int NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`departmentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES (10001, 1, 1, '运维部门', 'Admin1', '1234567890', 1, '描述 1', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10002, 2, 1, '测试部门', 'Admin2', '1234567891', 0, '描述 2', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10003, 3, 1, '市场部门', 'Admin3', '1234567892', 1, '描述 3', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10004, 4, 1, '研发部门', 'Admin4', '1234567893', 0, '描述 4', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10005, 5, 1, '财务部门', 'Admin5', '1234567894', 1, '描述 5', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10006, 1, 2, '运维部门', 'Admin6', '1234567895', 0, '描述 6', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10007, 2, 2, '测试部门', 'Admin7', '1234567896', 1, '描述 7', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10008, 3, 2, '市场部门', 'Admin8', '1234567897', 0, '描述 8', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10009, 4, 2, '研发部门', 'Admin9', '1234567898', 1, '描述 9', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10010, 1, 3, '运维部门', 'Admin10', '1234567899', 1, '描述 10', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10011, 2, 3, '测试部门', 'Admin11', '1234567800', 0, '描述 11', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10012, 3, 3, '市场部门', 'Admin12', '1234567801', 1, '描述 12', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10013, 1, 4, '运维部门', 'Admin13', '1234567802', 0, '描述 13', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10014, 2, 4, '测试部门', 'Admin14', '1234567803', 1, '描述 14', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10015, 1, 5, '运维部门', 'Admin15', '1234567804', 1, '描述 15', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10016, 1, 6, '运维部门', 'Admin16', '1234567805', 0, '描述 16', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10017, 2, 6, '测试部门', 'Admin17', '1234567806', 1, '描述 17', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10018, 3, 6, '市场部门', 'Admin18', '1234567807', 0, '描述 18', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10019, 4, 6, '研发部门', 'Admin19', '1234567808', 1, '描述 19', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10020, 1, 7, '运维部门', 'Admin20', '1234567809', 1, '描述 20', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10021, 2, 7, '测试部门', 'Admin21', '1234567810', 0, '描述 21', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10022, 1, 8, '运维部门', 'Admin22', '1234567811', 1, '描述 22', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10023, 2, 8, '测试部门', 'Admin23', '1234567812', 0, '描述 23', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10024, 3, 8, '市场部门', 'Admin24', '1234567813', 1, '描述 24', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10025, 1, 9, '运维部门', 'Admin25', '1234567814', 0, '描述 25', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10026, 2, 9, '测试部门', 'Admin26', '1234567815', 1, '描述 26', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10027, 1, 10, '运维部门', 'Admin27', '1234567816', 1, '描述 27', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10028, 2, 10, '测试部门', 'Admin28', '1234567817', 0, '描述 28', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10029, 3, 10, '市场部门', 'Admin29', '1234567818', 1, '描述 29', '2024-06-23 14:07:00');
INSERT INTO `t_department` VALUES (10030, 6, 1, 'aaaa', '3', '2', 0, '', '2024-07-03 00:13:43');
INSERT INTO `t_department` VALUES (10033, 1, 16, 'aa', 'a', 'aa', 0, '', '2024-07-03 00:34:57');
INSERT INTO `t_department` VALUES (10034, 1, 24, '测试部门', '测试部门', '1242134234234', 0, '', '2024-07-03 02:37:53');
INSERT INTO `t_department` VALUES (10038, 1, 27, 'ces', 'sdf', 'ssd', 1, '', '2024-07-03 02:43:40');
INSERT INTO `t_department` VALUES (10039, 1, 28, 'asdf', 'sdf', 'sdf', 0, '', '2024-07-03 02:44:11');
INSERT INTO `t_department` VALUES (10040, 1, 29, '测试部门', '测试部门管理员', '12344567989', 1, '', '2024-07-03 03:52:28');
INSERT INTO `t_department` VALUES (10041, 1, 17, '11', '1', '1', 1, '', '2024-07-03 04:08:43');
INSERT INTO `t_department` VALUES (10042, 2, 17, '1', '1', '1', 1, '', '2024-07-03 04:09:10');
INSERT INTO `t_department` VALUES (10044, 1, 31, 'sadf', 'sdf', 'sdf', 0, '', '2024-07-03 04:20:16');
INSERT INTO `t_department` VALUES (10046, 1, 34, 'sadf', 'sdf', 'sadf', 0, '', '2024-07-03 04:22:49');
INSERT INTO `t_department` VALUES (10048, 1, 35, 'sdf', 'sdf', 'sd', 1, '', '2024-07-03 05:19:30');
INSERT INTO `t_department` VALUES (10049, 2, 35, 'sdf', 'sdf', 'sdf', 1, '', '2024-07-03 05:19:46');

SET FOREIGN_KEY_CHECKS = 1;
