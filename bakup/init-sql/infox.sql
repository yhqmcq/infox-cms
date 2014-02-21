/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50518
Source Host           : localhost:3306
Source Database       : infox

Target Server Type    : MYSQL
Target Server Version : 50518
File Encoding         : 65001

Date: 2014-02-21 13:53:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `infox_sysmgr_emp`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_emp`;
CREATE TABLE `infox_sysmgr_emp` (
  `id` varchar(255) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `lastmod` datetime DEFAULT NULL,
  `modifyer` varchar(255) DEFAULT NULL,
  `orgname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `truename` varchar(255) DEFAULT NULL,
  `ORG_PID` varchar(255) DEFAULT NULL,
  `onlineState` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCB2970897D4D9D9F` (`ORG_PID`),
  CONSTRAINT `FKCB2970897D4D9D9F` FOREIGN KEY (`ORG_PID`) REFERENCES `infox_sysmgr_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_emp
-- ----------------------------
INSERT INTO `infox_sysmgr_emp` VALUES ('043242', 'guest', '2014-02-10 17:22:16', null, null, 'guest@infox.com', '2014-02-10 17:22:16', null, '', 'guest', 'male', 'Y', '020-666666', 'guest', null, '0');
INSERT INTO `infox_sysmgr_emp` VALUES ('242319', 'test1', '2014-02-10 19:06:07', null, null, '', '2014-02-10 19:06:07', null, '', 'test1', 'male', 'N', '', 'test1', null, null);
INSERT INTO `infox_sysmgr_emp` VALUES ('436405', '12', '2014-02-15 10:32:34', null, null, '', '2014-02-15 10:32:34', null, '', '12', 'male', 'Y', '', '12', null, null);
INSERT INTO `infox_sysmgr_emp` VALUES ('457861', null, '2014-02-13 20:53:42', null, null, null, '2014-02-13 20:53:42', null, null, null, null, null, null, null, null, null);
INSERT INTO `infox_sysmgr_emp` VALUES ('684340', 'admin', '2014-02-10 19:04:32', null, null, 'service@infox.com', '2014-02-10 19:04:32', null, '', 'admin', 'male', 'Y', '020-888888', '超级管理员', null, '1');
INSERT INTO `infox_sysmgr_emp` VALUES ('705303', '12', '2014-02-13 20:19:34', null, null, '', '2014-02-13 20:19:34', null, '', '12', 'male', 'Y', '', '12', null, null);

-- ----------------------------
-- Table structure for `infox_sysmgr_emp_online`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_emp_online`;
CREATE TABLE `infox_sysmgr_emp_online` (
  `id` varchar(255) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `empid` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `logindate` datetime DEFAULT NULL,
  `truename` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_emp_online
-- ----------------------------
INSERT INTO `infox_sysmgr_emp_online` VALUES ('40288483445297730144529773a00000', 'admin', '684340', '本地', '2014-02-21 11:57:41', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a4e34c0000', 'admin', '684340', '本地', '2014-02-21 12:12:21', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a504e30002', 'admin', '684340', '本地', '2014-02-21 12:12:30', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a5cb130004', 'admin', '684340', '本地', '2014-02-21 12:13:21', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a5f1040006', 'admin', '684340', '本地', '2014-02-21 12:13:30', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a60f8e0008', 'admin', '684340', '本地', '2014-02-21 12:13:38', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a80878000a', 'admin', '684340', '本地', '2014-02-21 12:15:47', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a8a8c8000c', 'admin', '684340', '本地', '2014-02-21 12:16:28', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a9a7e1000e', 'admin', '684340', '本地', '2014-02-21 12:17:34', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452a9e2500010', 'admin', '684340', '本地', '2014-02-21 12:17:49', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452ab19b10012', 'admin', '684340', '本地', '2014-02-21 12:19:08', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452a4e3014452ac037d0014', 'admin', '684340', '本地', '2014-02-21 12:20:08', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452b052014452b052cf0000', 'admin', '684340', '本地', '2014-02-21 12:24:51', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452b052014452c047c30002', 'guest', '043242', '本地', '2014-02-21 12:42:16', 'guest', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452b052014452c09c2b0004', 'admin', '684340', '本地', '2014-02-21 12:42:38', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452d29b014452d29be20000', 'admin', '684340', '本地', '2014-02-21 13:02:18', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452d29b014452d2ca160002', 'admin', '684340', '本地', '2014-02-21 13:02:29', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452d29b014452d50b350004', 'admin', '684340', '本地', '2014-02-21 13:04:57', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452d29b014452dbc7b40006', 'admin', '684340', '本地', '2014-02-21 13:12:19', '超级管理员', '1');
INSERT INTO `infox_sysmgr_emp_online` VALUES ('402884834452d29b014452dbe2270008', 'admin', '684340', '本地', '2014-02-21 13:12:25', '超级管理员', '1');

-- ----------------------------
-- Table structure for `infox_sysmgr_emp_role`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_emp_role`;
CREATE TABLE `infox_sysmgr_emp_role` (
  `ROLE_ID` varchar(36) NOT NULL,
  `EMP_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`EMP_ID`,`ROLE_ID`),
  KEY `FKD465AE6C2C7F9376` (`ROLE_ID`),
  KEY `FKD465AE6C9D60099C` (`EMP_ID`),
  CONSTRAINT `FKD465AE6C2C7F9376` FOREIGN KEY (`ROLE_ID`) REFERENCES `infox_sysmgr_role` (`id`),
  CONSTRAINT `FKD465AE6C9D60099C` FOREIGN KEY (`EMP_ID`) REFERENCES `infox_sysmgr_emp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_emp_role
-- ----------------------------
INSERT INTO `infox_sysmgr_emp_role` VALUES ('948364', '684340');

-- ----------------------------
-- Table structure for `infox_sysmgr_menu`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_menu`;
CREATE TABLE `infox_sysmgr_menu` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `iconCls` varchar(255) DEFAULT NULL,
  `lastmod` datetime DEFAULT NULL,
  `modifyer` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `MENU_PID` varchar(255) DEFAULT NULL,
  `disused` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9A08259E4480AEC6` (`MENU_PID`),
  CONSTRAINT `FK9A08259E4480AEC6` FOREIGN KEY (`MENU_PID`) REFERENCES `infox_sysmgr_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_menu
-- ----------------------------
INSERT INTO `infox_sysmgr_menu` VALUES ('068671', null, '2014-02-13 10:39:25', null, 'sysmgr/employee/emp_grant_main.do', 'icon-standard-arrow-undo', '2014-02-13 10:39:25', null, '用户授权', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('136992', null, '2014-02-17 23:03:40', null, 'sysmgr/data/data_main.do', 'icon-standard-database-save', '2014-02-17 23:03:40', null, '数据备份', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('200025', null, '2014-02-13 10:31:53', null, 'sysmgr/task/task_main.do', 'icon-standard-clock-red', '2014-02-13 10:31:53', null, '定时作业', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('229865', null, '2014-02-21 12:21:23', null, 'sysmgr/org/org_main.do', 'icon-standard-chart-organisation', '2014-02-21 12:21:23', null, '组织管理', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('547345', null, '2014-02-21 12:21:57', null, 'sysmgr/employee/emp_main.do', 'icon-hamburg-my-account', '2014-02-21 12:21:57', null, '用户管理', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('675303', null, '2014-02-18 14:50:50', null, 'sysmgr/filemanager/file_main.do', 'icon-standard-folder-database', '2014-02-18 14:50:50', null, '文件管理', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('784026', null, '2014-02-17 23:13:29', null, 'sysmgr/emponline/online_main.do', 'icon-standard-star', '2014-02-17 23:13:29', null, '登陆历史', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('837844', null, '2014-02-13 10:39:12', null, 'druid/druid.do', 'icon-standard-database-link', '2014-02-13 10:39:12', null, 'Druid监控', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('863930', null, '2014-02-13 10:39:16', null, 'sysmgr/menu/menu_main.do', 'icon-standard-application-side-boxes', '2014-02-13 10:39:16', null, '资源管理', '1', null, null, 'F', '876715', 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('876715', null, '2014-02-10 21:28:13', null, '', 'icon-standard-application-xp-terminal', '2014-02-10 21:28:13', null, '系统管理', '1', null, null, 'R', null, 'Y');
INSERT INTO `infox_sysmgr_menu` VALUES ('878344', null, '2014-02-13 10:39:21', null, 'sysmgr/role/role_main.do', 'icon-standard-award-star-silver-3', '2014-02-13 10:39:21', null, '角色管理', '1', null, null, 'F', '876715', 'Y');

-- ----------------------------
-- Table structure for `infox_sysmgr_org`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_org`;
CREATE TABLE `infox_sysmgr_org` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ename` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `iconCls` varchar(255) DEFAULT NULL,
  `lastmod` datetime DEFAULT NULL,
  `modifyer` varchar(255) DEFAULT NULL,
  `pname` varchar(255) DEFAULT NULL,
  `sname` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `ORG_PID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCB2996A57D4D9D9F` (`ORG_PID`),
  CONSTRAINT `FKCB2996A57D4D9D9F` FOREIGN KEY (`ORG_PID`) REFERENCES `infox_sysmgr_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_org
-- ----------------------------
INSERT INTO `infox_sysmgr_org` VALUES ('365852', '121', '2014-02-15 11:13:07', null, '', '12', '', '12', null, '2014-02-15 11:13:07', null, '', '', '', null);

-- ----------------------------
-- Table structure for `infox_sysmgr_role`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_role`;
CREATE TABLE `infox_sysmgr_role` (
  `id` varchar(36) NOT NULL,
  `createdatetime` datetime DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `iconCls` varchar(255) DEFAULT NULL,
  `modifyer` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `PID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9A0A90B5DA6FAF7D` (`PID`),
  CONSTRAINT `FK9A0A90B5DA6FAF7D` FOREIGN KEY (`PID`) REFERENCES `infox_sysmgr_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_role
-- ----------------------------
INSERT INTO `infox_sysmgr_role` VALUES ('948364', '2014-02-10 16:24:15', null, '拥有所有权限', 'ext_group', null, '超级管理员角色', null, null);

-- ----------------------------
-- Table structure for `infox_sysmgr_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `infox_sysmgr_role_menu`;
CREATE TABLE `infox_sysmgr_role_menu` (
  `ROLE_ID` varchar(36) NOT NULL,
  `MENU_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MENU_ID`),
  KEY `FK104B5EA9A11DEC16` (`MENU_ID`),
  KEY `FK104B5EA92C7F9376` (`ROLE_ID`),
  CONSTRAINT `FK104B5EA92C7F9376` FOREIGN KEY (`ROLE_ID`) REFERENCES `infox_sysmgr_role` (`id`),
  CONSTRAINT `FK104B5EA9A11DEC16` FOREIGN KEY (`MENU_ID`) REFERENCES `infox_sysmgr_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infox_sysmgr_role_menu
-- ----------------------------
INSERT INTO `infox_sysmgr_role_menu` VALUES ('948364', '068671');
INSERT INTO `infox_sysmgr_role_menu` VALUES ('948364', '229865');
INSERT INTO `infox_sysmgr_role_menu` VALUES ('948364', '547345');
INSERT INTO `infox_sysmgr_role_menu` VALUES ('948364', '837844');
INSERT INTO `infox_sysmgr_role_menu` VALUES ('948364', '863930');
INSERT INTO `infox_sysmgr_role_menu` VALUES ('948364', '876715');
INSERT INTO `infox_sysmgr_role_menu` VALUES ('948364', '878344');

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
INSERT INTO `infox_sysmgr_task` VALUES ('954465', '2014-02-18 13:15:21', '', '', '* * 21 * * ? ', '4028848344436aa80144436b7c240001', 'Y', 'com.infox.sysmgr.job.SchedulerBackupDB', '数据库备份', '数据库备份', '2', '定时备份数据库');
