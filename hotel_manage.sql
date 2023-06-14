/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : hotel_manage

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 25/05/2023 15:38:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`  (
  `Bno` int(0) NOT NULL,
  `Rno` int(0) NOT NULL,
  `Uno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Bmoney` int(0) NOT NULL,
  PRIMARY KEY (`Bno`) USING BTREE,
  INDEX `Uno`(`Uno`) USING BTREE,
  INDEX `Rno`(`Rno`) USING BTREE,
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`Uno`) REFERENCES `user` (`Uno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`Rno`) REFERENCES `reservation` (`Rno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bill
-- ----------------------------

-- ----------------------------
-- Table structure for bonuses
-- ----------------------------
DROP TABLE IF EXISTS `bonuses`;
CREATE TABLE `bonuses`  (
  `MessageId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Mid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `year` int(0) NULL DEFAULT NULL,
  `Sbonus` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`MessageId`) USING BTREE,
  INDEX `SId`(`SId`) USING BTREE,
  INDEX `Mid`(`Mid`) USING BTREE,
  CONSTRAINT `bonuses_ibfk_1` FOREIGN KEY (`SId`) REFERENCES `employee` (`Sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bonuses_ibfk_2` FOREIGN KEY (`Mid`) REFERENCES `manager` (`Mid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bonuses
-- ----------------------------

-- ----------------------------
-- Table structure for demand
-- ----------------------------
DROP TABLE IF EXISTS `demand`;
CREATE TABLE `demand`  (
  `Dno` int(0) NOT NULL,
  `Uno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Bno` int(0) NOT NULL,
  `Dperformer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Dinitiator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Dcontent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Dtime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Dmoney` int(0) NULL DEFAULT NULL,
  `Ddescription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Dno`) USING BTREE,
  INDEX `Uno`(`Uno`) USING BTREE,
  INDEX `Bno`(`Bno`) USING BTREE,
  CONSTRAINT `demand_ibfk_1` FOREIGN KEY (`Uno`) REFERENCES `user` (`Uno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `demand_ibfk_2` FOREIGN KEY (`Bno`) REFERENCES `bill` (`Bno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of demand
-- ----------------------------

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `Sid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Sname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Sposition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Sbasepay` int(0) NULL DEFAULT NULL,
  `Sbonus` int(0) NULL DEFAULT NULL,
  `Sworktime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------

-- ----------------------------
-- Table structure for ep_change
-- ----------------------------
DROP TABLE IF EXISTS `ep_change`;
CREATE TABLE `ep_change`  (
  `Rid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Sid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Mid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Sposition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Sworktime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Sbasepay` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Rid`) USING BTREE,
  INDEX `Sid`(`Sid`) USING BTREE,
  INDEX `Mid`(`Mid`) USING BTREE,
  CONSTRAINT `ep_change_ibfk_1` FOREIGN KEY (`Sid`) REFERENCES `employee` (`Sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ep_change_ibfk_2` FOREIGN KEY (`Mid`) REFERENCES `manager` (`Mid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ep_change
-- ----------------------------

-- ----------------------------
-- Table structure for evaluate
-- ----------------------------
DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate`  (
  `Eno` int(0) NOT NULL,
  `Dno` int(0) NOT NULL,
  `Uno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Estar` int(0) NOT NULL,
  `Einformation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Eno`) USING BTREE,
  INDEX `Uno`(`Uno`) USING BTREE,
  INDEX `Dno`(`Dno`) USING BTREE,
  INDEX `estar_index`(`Estar`) USING BTREE,
  CONSTRAINT `evaluate_ibfk_1` FOREIGN KEY (`Uno`) REFERENCES `user` (`Uno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `evaluate_ibfk_2` FOREIGN KEY (`Dno`) REFERENCES `demand` (`Dno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `Mid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Mname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Mposition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Mbasepay` int(0) NULL DEFAULT NULL,
  `Mbonus` int(0) NULL DEFAULT NULL,
  `Mworktime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Mid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manager
-- ----------------------------

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process`  (
  `Dno` int(0) NOT NULL,
  `Sid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Sname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Dinitiator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`Dno`) USING BTREE,
  INDEX `Sid`(`Sid`) USING BTREE,
  CONSTRAINT `process_ibfk_1` FOREIGN KEY (`Sid`) REFERENCES `employee` (`Sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of process
-- ----------------------------

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `Rno` int(0) NOT NULL,
  `Uno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Rtype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Rmoney` int(0) NOT NULL,
  `Rperiod` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Rconfiguration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Rno`) USING BTREE,
  INDEX `Uno`(`Uno`) USING BTREE,
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`Uno`) REFERENCES `user` (`Uno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------

-- ----------------------------
-- Table structure for roomcheck
-- ----------------------------
DROP TABLE IF EXISTS `roomcheck`;
CREATE TABLE `roomcheck`  (
  `KFRZnumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `KFtime` datetime(0) NOT NULL,
  `TFtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`KFRZnumber`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roomcheck
-- ----------------------------

-- ----------------------------
-- Table structure for roomtype
-- ----------------------------
DROP TABLE IF EXISTS `roomtype`;
CREATE TABLE `roomtype`  (
  `LXnumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `LX` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `KFZnumber` decimal(3, 0) NOT NULL,
  `KFYnumber` decimal(3, 0) NOT NULL,
  PRIMARY KEY (`LXnumber`) USING BTREE,
  INDEX `kfynumber_LX_index`(`LX`, `KFYnumber`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roomtype
-- ----------------------------

-- ----------------------------
-- Table structure for signed
-- ----------------------------
DROP TABLE IF EXISTS `signed`;
CREATE TABLE `signed`  (
  `QDnumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `KFRZnumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Uno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`QDnumber`) USING BTREE,
  INDEX `Uno`(`Uno`) USING BTREE,
  INDEX `KFRZnumber`(`KFRZnumber`) USING BTREE,
  CONSTRAINT `signed_ibfk_1` FOREIGN KEY (`Uno`) REFERENCES `user` (`Uno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `signed_ibfk_2` FOREIGN KEY (`KFRZnumber`) REFERENCES `roomcheck` (`KFRZnumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of signed
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `Uno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `VIPno` int(0) NOT NULL,
  `Uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Usex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Umail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Uno`) USING BTREE,
  INDEX `VIPno`(`VIPno`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`VIPno`) REFERENCES `vip` (`VIPno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for vip
-- ----------------------------
DROP TABLE IF EXISTS `vip`;
CREATE TABLE `vip`  (
  `VIPno` int(0) NOT NULL,
  `VIPlevel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `VIPpower` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `VIPduration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `VIPpoints` int(0) NULL DEFAULT NULL,
  `VIPinformation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`VIPno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip
-- ----------------------------

-- ----------------------------
-- View structure for demand_view
-- ----------------------------
DROP VIEW IF EXISTS `demand_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `demand_view` AS select `demand`.`Dno` AS `处理单号`,`demand`.`Dperformer` AS `处理人`,`demand`.`Dinitiator` AS `需求发起人`,`demand`.`Dcontent` AS `处理内容`,`demand`.`Dtime` AS `处理时间`,`demand`.`Dmoney` AS `金额`,`demand`.`Ddescription` AS `内容描述` from `demand`;

-- ----------------------------
-- View structure for user_vip
-- ----------------------------
DROP VIEW IF EXISTS `user_vip`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_vip` AS select `u`.`Uname` AS `姓名`,`u`.`Usex` AS `性别`,`u`.`Umail` AS `邮箱`,`u`.`Uno` AS `身份证号`,`v`.`VIPlevel` AS `会员等级`,`v`.`VIPpower` AS `会员权限`,`v`.`VIPduration` AS `会员时长`,`v`.`VIPpoints` AS `会员积分`,`v`.`VIPinformation` AS `会员信息` from (`user` `u` join `vip` `v`) where (`u`.`VIPno` = `v`.`VIPno`);

-- ----------------------------
-- Procedure structure for max_employee_money
-- ----------------------------
DROP PROCEDURE IF EXISTS `max_employee_money`;
delimiter ;;
CREATE PROCEDURE `max_employee_money`(IN thisyear INT)
BEGIN
	SELECT MAX(Sbonus) FROM bonuses WHERE year=thisyear;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for room_type_number
-- ----------------------------
DROP PROCEDURE IF EXISTS `room_type_number`;
delimiter ;;
CREATE PROCEDURE `room_type_number`(IN type VARCHAR(255))
BEGIN
	SELECT KFYnumber FROM roomtype WHERE LX=type;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table reservation
-- ----------------------------
DROP TRIGGER IF EXISTS `update_roomtype_number`;
delimiter ;;
CREATE TRIGGER `update_roomtype_number` AFTER INSERT ON `reservation` FOR EACH ROW BEGIN
    UPDATE roomtype 
    SET KFYnumber = KFYnumber - 1 
    WHERE LX = NEW.rtype;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table reservation
-- ----------------------------
DROP TRIGGER IF EXISTS `insert_room_check`;
delimiter ;;
CREATE TRIGGER `insert_room_check` AFTER INSERT ON `reservation` FOR EACH ROW BEGIN
INSERT INTO roomcheck (KFRZnumber, KFtime) VALUES (NEW.Rno, NOW());
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table reservation
-- ----------------------------
DROP TRIGGER IF EXISTS `update_vip_points`;
delimiter ;;
CREATE TRIGGER `update_vip_points` AFTER INSERT ON `reservation` FOR EACH ROW BEGIN
UPDATE vip, user
SET vip.VIPpoints = vip.VIPpoints + NEW.Rmoney
WHERE vip.VIPno = user.VIPno AND user.Uno = NEW.Uno;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
