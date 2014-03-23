/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : infox

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2014-03-23 15:41:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `infox_sysmgr_user`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_user`;
CREATE TABLE `infox_sysmgr_user` (
  `id` varchar(255) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `DEPT_ID` varchar(255) DEFAULT NULL,
  `DETAIL_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ahmhkam4jkgngtunox5bb9t3u` (`DEPT_ID`),
  KEY `FK_pobv3fywcn9wxks9r4pmh8gut` (`DETAIL_ID`),
  CONSTRAINT `FK_pobv3fywcn9wxks9r4pmh8gut` FOREIGN KEY (`DETAIL_ID`) REFERENCES `infox_sysmgr_user_detail` (`id`),
  CONSTRAINT `FK_ahmhkam4jkgngtunox5bb9t3u` FOREIGN KEY (`DEPT_ID`) REFERENCES `infox_sysmgr_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_user
-- ----------------------------
INSERT INTO `infox_sysmgr_user` VALUES ('030759', 'test', '2014-03-23 15:18:32', 'test', '0', null, '719589');
INSERT INTO `infox_sysmgr_user` VALUES ('9999', 'admin', '2014-03-23 15:40:35', 'admin123', '0', null, '511519');
