/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : infox

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2014-03-23 15:41:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `infox_sysmgr_user_detail`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_user_detail`;
CREATE TABLE `infox_sysmgr_user_detail` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `truename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_user_detail
-- ----------------------------
INSERT INTO `infox_sysmgr_user_detail` VALUES ('511519', 'admin@admin.com', 'male', '', '超级管理员');
INSERT INTO `infox_sysmgr_user_detail` VALUES ('719589', 'test@admin.com', 'male', '123', 'test');
