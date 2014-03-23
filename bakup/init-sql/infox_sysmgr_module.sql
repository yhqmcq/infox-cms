/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : infox

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2014-03-23 10:53:23
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
INSERT INTO `infox_sysmgr_module` VALUES ('468147', '2014-03-23 09:53:27', 'Y', 'icon-standard-bin', 'sysmgr/filemanager/file_main.do', null, '文件管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('547696', '2014-03-22 22:50:15', 'Y', 'icon-standard-award-star-gold-2', 'sysmgr/userAction/user_permit_main.do', null, '用户授权', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('607659', '2014-03-22 19:26:58', 'Y', 'icon-standard-application-form', 'sysmgr/roleAction/role_main.do', null, '角色管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('693477', '2014-03-23 09:50:29', 'Y', 'icon-standard-basket', 'sysmgr/task/task_main.do', null, '定时作业', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('976482', '2014-03-20 18:07:58', 'Y', 'icon-standard-world', 'sysmgr/moduleAction/module_main.do', null, '模块管理', null, '1', null, 'F', null, '197101');
