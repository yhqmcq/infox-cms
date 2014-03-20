/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : infox

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2014-03-20 22:07:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `infox_sysmgr_module`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_module`;
CREATE TABLE `infox_sysmgr_module` (
  `id` varchar(255) NOT NULL,
  `created` datetime DEFAULT NULL,
  `disused` varchar(255) DEFAULT NULL,
  `iconCls` varchar(255) DEFAULT NULL,
  `linkUrl` varchar(255) DEFAULT NULL,
  `moduleDescription` varchar(255) DEFAULT NULL,
  `moduleName` varchar(255) DEFAULT NULL,
  `moduleValue` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `MODULE_PID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_nvie26q7jsda4qr0bk0ptbvvu` (`MODULE_PID`),
  CONSTRAINT `FK_nvie26q7jsda4qr0bk0ptbvvu` FOREIGN KEY (`MODULE_PID`) REFERENCES `infox_sysmgr_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_module
-- ----------------------------
INSERT INTO `infox_sysmgr_module` VALUES ('169104', '2014-03-20 19:10:26', 'Y', 'icon-standard-book-add', 'sysmgr/companyAction/company_main.do', null, '公司管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('197101', '2014-03-20 18:08:03', 'Y', 'icon-standard-application-xp-terminal', '', null, '系统管理', null, '1', null, 'R', null, null);
INSERT INTO `infox_sysmgr_module` VALUES ('446937', '2014-03-20 19:08:04', 'Y', 'icon-standard-application-form', 'sysmgr/companyAction/company_main.do', null, '公司管理', null, '1', null, 'F', null, null);
INSERT INTO `infox_sysmgr_module` VALUES ('446938', '2014-03-20 19:08:46', 'Y', 'icon-standard-user-gray', 'sysmgr/userAction/user_main.do', null, '用户管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('976482', '2014-03-20 18:07:58', 'Y', 'icon-standard-world', 'sysmgr/moduleAction/module_main.do', null, '模块管理', null, '1', null, 'F', null, '197101');
