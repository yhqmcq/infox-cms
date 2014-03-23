/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : infox

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2014-03-23 15:40:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `infox_sysmgr_action`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_action`;
CREATE TABLE `infox_sysmgr_action` (
  `id` varchar(255) NOT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `actionValue` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `MODULE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4p33cdfee5wud5fxev0cad2n3` (`MODULE_ID`),
  CONSTRAINT `FK_4p33cdfee5wud5fxev0cad2n3` FOREIGN KEY (`MODULE_ID`) REFERENCES `infox_sysmgr_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_action
-- ----------------------------

-- ----------------------------
-- Table structure for `infox_sysmgr_company`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_company`;
CREATE TABLE `infox_sysmgr_company` (
  `id` varchar(255) NOT NULL,
  `created` datetime DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sname` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `COMPANY_PID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t5urnt4gop9lfrgle7bkd9s1k` (`COMPANY_PID`),
  CONSTRAINT `FK_t5urnt4gop9lfrgle7bkd9s1k` FOREIGN KEY (`COMPANY_PID`) REFERENCES `infox_sysmgr_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_company
-- ----------------------------

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
INSERT INTO `infox_sysmgr_module` VALUES ('169104', '2014-03-23 14:38:25', 'Y', 'icon-standard-book-add', 'sysmgr/companyAction/company_main.do', null, '公司管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('197101', '2014-03-20 18:08:03', 'Y', 'icon-standard-application-xp-terminal', '', null, '系统管理', null, '1', null, 'R', null, null);
INSERT INTO `infox_sysmgr_module` VALUES ('238076', '2014-03-23 15:07:56', 'Y', 'icon-standard-layout-content', 'sysmgr/moduleAction/treegrid.do', null, '查询', null, '1', null, 'O', null, '976482');
INSERT INTO `infox_sysmgr_module` VALUES ('355171', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/roleAction/edit.do', null, '添加', null, '1', null, 'O', null, '607659');
INSERT INTO `infox_sysmgr_module` VALUES ('355178', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/companyAction/edit.do', null, '添加', null, '1', null, 'O', null, '169104');
INSERT INTO `infox_sysmgr_module` VALUES ('355212', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/roleAction/edit.do', null, '删除', null, '1', null, 'O', null, '607659');
INSERT INTO `infox_sysmgr_module` VALUES ('355219', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/companyAction/edit.do', null, '删除', null, '1', null, 'O', null, '169104');
INSERT INTO `infox_sysmgr_module` VALUES ('355323', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/roleAction/edit.do', null, '编辑', null, '1', null, 'O', null, '607659');
INSERT INTO `infox_sysmgr_module` VALUES ('355328', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/companyAction/edit.do', null, '编辑', null, '1', null, 'O', null, '169104');
INSERT INTO `infox_sysmgr_module` VALUES ('355434', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/roleAction/datagrid.do', null, '查询', null, '1', null, 'O', null, '607659');
INSERT INTO `infox_sysmgr_module` VALUES ('355435', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/userAction/set_permit.do', null, '授权', null, '1', null, 'O', null, '547696');
INSERT INTO `infox_sysmgr_module` VALUES ('355439', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/companyAction/treegrid.do', null, '查询', null, '1', null, 'O', null, '169104');
INSERT INTO `infox_sysmgr_module` VALUES ('355919', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/userAction/edit.do', null, '删除', null, '1', null, 'O', null, '446938');
INSERT INTO `infox_sysmgr_module` VALUES ('355928', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/userAction/edit.do', null, '编辑', null, '1', null, 'O', null, '446938');
INSERT INTO `infox_sysmgr_module` VALUES ('355939', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/userAction/datagrid.do', null, '查询', null, '1', null, 'O', null, '446938');
INSERT INTO `infox_sysmgr_module` VALUES ('355966', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/moduleAction/module_form.do', null, '添加或编辑页面', null, '1', null, 'O', null, '976482');
INSERT INTO `infox_sysmgr_module` VALUES ('355968', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/moduleAction/delete.do', null, '删除', null, '1', null, 'O', null, '976482');
INSERT INTO `infox_sysmgr_module` VALUES ('355969', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/moduleAction/edit.do', null, '编辑', null, '1', null, 'O', null, '976482');
INSERT INTO `infox_sysmgr_module` VALUES ('355978', '2014-03-23 14:58:04', 'Y', 'icon-standard-layout-content', 'sysmgr/userAction/edit.do', null, '添加', null, '1', null, 'O', null, '446938');
INSERT INTO `infox_sysmgr_module` VALUES ('446938', '2014-03-20 19:08:46', 'Y', 'icon-standard-user-gray', 'sysmgr/userAction/user_main.do', null, '用户管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('468147', '2014-03-23 09:53:27', 'Y', 'icon-standard-bin', 'sysmgr/filemanager/file_main.do', null, '文件管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('547696', '2014-03-22 22:50:15', 'Y', 'icon-standard-award-star-gold-2', 'sysmgr/userAction/user_permit_main.do', null, '用户授权', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('607659', '2014-03-22 19:26:58', 'Y', 'icon-standard-application-form', 'sysmgr/roleAction/role_main.do', null, '角色管理', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('626522', '2014-03-23 15:02:37', 'Y', 'icon-standard-layout-content', 'sysmgr/userAction/add.do', null, '添加', null, '1', null, 'O', null, '976482');
INSERT INTO `infox_sysmgr_module` VALUES ('693477', '2014-03-23 09:50:29', 'Y', 'icon-standard-basket', 'sysmgr/task/task_main.do', null, '定时作业', null, '1', null, 'F', null, '197101');
INSERT INTO `infox_sysmgr_module` VALUES ('976482', '2014-03-23 14:46:38', 'Y', 'icon-standard-world', 'sysmgr/moduleAction/module_main.do', null, '模块管理', null, '1', null, 'F', null, '197101');

-- ----------------------------
-- Table structure for `infox_sysmgr_role`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_role`;
CREATE TABLE `infox_sysmgr_role` (
  `id` varchar(255) NOT NULL,
  `created` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_role
-- ----------------------------
INSERT INTO `infox_sysmgr_role` VALUES ('939384', '2014-03-23 15:17:50', null, '超级管理员');

-- ----------------------------
-- Table structure for `infox_sysmgr_role_module`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_role_module`;
CREATE TABLE `infox_sysmgr_role_module` (
  `ROLE_ID` varchar(255) NOT NULL,
  `MODULE_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MODULE_ID`),
  KEY `FK_79369fro21h53ulf2mjhrh9wt` (`MODULE_ID`),
  KEY `FK_6i69hoe2klo148om3w4v556f1` (`ROLE_ID`),
  CONSTRAINT `FK_6i69hoe2klo148om3w4v556f1` FOREIGN KEY (`ROLE_ID`) REFERENCES `infox_sysmgr_role` (`id`),
  CONSTRAINT `FK_79369fro21h53ulf2mjhrh9wt` FOREIGN KEY (`MODULE_ID`) REFERENCES `infox_sysmgr_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_role_module
-- ----------------------------
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '169104');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '197101');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '238076');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355171');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355178');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355212');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355219');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355323');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355328');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355434');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355439');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355919');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355928');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355939');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355966');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355968');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355969');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '355978');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '446938');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '468147');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '547696');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '607659');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '626522');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '693477');
INSERT INTO `infox_sysmgr_role_module` VALUES ('939384', '976482');

-- ----------------------------
-- Table structure for `infox_sysmgr_task`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_task`;
CREATE TABLE `infox_sysmgr_task` (
  `id` varchar(255) NOT NULL,
  `created` datetime DEFAULT NULL,
  `creater_id` varchar(255) DEFAULT NULL,
  `creater_name` varchar(255) DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `task_enable` varchar(255) DEFAULT NULL,
  `task_job_class` varchar(255) DEFAULT NULL,
  `task_name` varchar(255) DEFAULT NULL,
  `task_remark` varchar(255) DEFAULT NULL,
  `task_type` varchar(255) DEFAULT NULL,
  `task_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_task
-- ----------------------------

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

-- ----------------------------
-- Table structure for `infox_sysmgr_user_role_permit`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_user_role_permit`;
CREATE TABLE `infox_sysmgr_user_role_permit` (
  `USER_ID` varchar(255) NOT NULL,
  `ROLE_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `FK_ow3q4x2xof9n6r8bph8t8bb04` (`ROLE_ID`),
  KEY `FK_akf60nrcuovftgjiucwre22cu` (`USER_ID`),
  CONSTRAINT `FK_akf60nrcuovftgjiucwre22cu` FOREIGN KEY (`USER_ID`) REFERENCES `infox_sysmgr_user` (`id`),
  CONSTRAINT `FK_ow3q4x2xof9n6r8bph8t8bb04` FOREIGN KEY (`ROLE_ID`) REFERENCES `infox_sysmgr_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_user_role_permit
-- ----------------------------
INSERT INTO `infox_sysmgr_user_role_permit` VALUES ('030759', '939384');
