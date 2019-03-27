/*
Navicat MySQL Data Transfer

Source Server         : footstone
Source Server Version : 50725
Source Host           : rm-2ze7y0yf3930b5rna.mysql.rds.aliyuncs.com:3306
Source Database       : pass_footstone

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-03-27 13:53:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_data_exec
-- ----------------------------
DROP TABLE IF EXISTS `admin_data_exec`;
CREATE TABLE `admin_data_exec` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `exec_desc` varchar(255) COLLATE utf8_bin NOT NULL,
  `exec_state` tinyint(4) NOT NULL,
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `app_show_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `datasource_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `profile` varchar(255) COLLATE utf8_bin NOT NULL,
  `exec_type` varchar(255) COLLATE utf8_bin NOT NULL,
  `exec_num` int(10) NOT NULL,
  `exec_script` text COLLATE utf8_bin NOT NULL,
  `exec_result` longtext COLLATE utf8_bin NOT NULL,
  `create_username` varchar(255) COLLATE utf8_bin NOT NULL,
  `exec_username` varchar(255) COLLATE utf8_bin NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `create_display_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `exec_display_name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of admin_data_exec
-- ----------------------------

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `menu_urls` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '菜单连接',
  `menu_type` tinyint(4) NOT NULL COMMENT '菜单类型 1=菜单 2=按钮',
  `gmt_modified` datetime NOT NULL COMMENT '创建时间',
  `menu_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0=生效 1=失效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_admin_menu_menu_code` (`menu_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES ('1', 'app_list', '应用列表', '[\"/app/list\",\"/user/list\",\"/app/get\",\"/app/member/add\",\"/app/instances\"]', '1', '2019-03-20 16:44:05', '0');
INSERT INTO `admin_menu` VALUES ('2', 'app_add', '新建应用', '[\"/app/add\",\"/app/list\",\"/user/list\",\"/system/groups\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('3', 'props_list', '应用配置列表', '[\"/app/list\",\"/properties/list\"]', '1', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('4', 'props_add', '新建应用配置', '[\"/properties/add\",\"/properties/list\",\"/app/list\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('5', 'props_edit', '编辑应用配置', '[\"/properties/edit\",\"/properties/list\",\"/app/list\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('6', 'props_publish', '发布应用配置', '[\"/properties/publish\",\"/properties/list\",\"/app/list\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('7', 'props_offline', '下线应用配置', '[\"/properties/offline\",\"/properties/list\",\"/app/list\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('8', 'sys_props_list', '环境配置列表', '[\"/properties/system/list\"]', '1', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('9', 'sys_props_add', '新建环境配置', '[\"/properties/system/add\",\"/properties/system/list\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('10', 'datasource_list', '数据源列表', '[\"/datasource/list\",\"/app/list\"]', '1', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('11', 'datasource_add', '新建数据源', '[\"/datasource/add\",\"/datasource/list\",\"/app/list\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('12', 'iteration_list', '迭代列表', '[\"/iteration/list\",\"/app/list\"]', '1', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('13', 'iteration_add', '新建迭代', '[\"/iteration/add\",\"/iteration/list\",\"/app/list\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('14', 'package_list', '迭代列表', '[\"/app/list\"]', '1', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('15', 'package_add', '新建迭代', '[\"/app/list\",\"/iteration/list\",\"/deploy/packaging\",\"/deploy/modules\"]', '2', '2019-03-20 16:45:19', '0');
INSERT INTO `admin_menu` VALUES ('16', 'data_exec_commit', '数据变更申请', '[\"/datasource/exec/list\",\"/datasource/exec/commit\",\"/datasource/exec/get\",\"/datasource/exec/execute\"]', '0', '2019-03-26 11:19:35', '0');
INSERT INTO `admin_menu` VALUES ('17', 'data_exec_audit', '数据变更审核', '[\"/datasource/exec/pass\",\"/datasource/exec/reject\"]', '0', '2019-03-26 16:11:38', '0');

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '角色编码',
  `menu_code` varchar(60) COLLATE utf8_bin NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `relation_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0=生效 1=失效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu_role_menu_code` (`role_code`,`menu_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of admin_role_menu
-- ----------------------------
INSERT INTO `admin_role_menu` VALUES ('1', 'admin', 'app_list', '2019-03-20 16:44:31', '0');
INSERT INTO `admin_role_menu` VALUES ('2', 'admin', 'app_add', '2019-03-20 16:45:43', '0');
INSERT INTO `admin_role_menu` VALUES ('3', 'owner', 'app_list', '2019-03-20 17:23:06', '0');
INSERT INTO `admin_role_menu` VALUES ('4', 'owner', 'props_list', '2019-03-20 17:23:48', '0');
INSERT INTO `admin_role_menu` VALUES ('5', 'owner', 'props_add', '2019-03-20 17:24:02', '0');
INSERT INTO `admin_role_menu` VALUES ('6', 'owner', 'props_offline', '2019-03-20 17:24:17', '0');
INSERT INTO `admin_role_menu` VALUES ('7', 'owner', 'props_edit', '2019-03-20 17:24:29', '0');
INSERT INTO `admin_role_menu` VALUES ('8', 'owner', 'props_publish', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('9', 'admin', 'sys_props_list', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('10', 'admin', 'sys_props_add', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('11', 'admin', 'datasource_list', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('12', 'admin', 'datasource_add', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('13', 'owner', 'iteration_list', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('14', 'owner', 'iteration_add', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('15', 'owner', 'package_list', '2019-03-20 17:24:44', '0');
INSERT INTO `admin_role_menu` VALUES ('16', 'owner', 'package_add', '2019-03-21 13:23:44', '0');
INSERT INTO `admin_role_menu` VALUES ('17', 'owner', 'data_exec_commit', '2019-03-26 11:20:17', '0');
INSERT INTO `admin_role_menu` VALUES ('18', 'owner', 'data_exec_audit', '2019-03-26 16:12:47', '0');

-- ----------------------------
-- Table structure for admin_tag
-- ----------------------------
DROP TABLE IF EXISTS `admin_tag`;
CREATE TABLE `admin_tag` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tag_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '标签类型',
  `tag_ref` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '标签引用',
  `tag_code` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '标签编码',
  `tag_value` text COLLATE utf8_bin NOT NULL COMMENT '标签值',
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_type_ref_code` (`tag_type`,`tag_ref`,`tag_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of admin_tag
-- ----------------------------
INSERT INTO `admin_tag` VALUES ('1', 'project_developer', 'mmc-service', 'LiSu', 0x5375204C69, '2019-03-25 17:14:22');
INSERT INTO `admin_tag` VALUES ('2', 'project_developer', 'mmc-service', 'WangErQiang', 0x45725169616E672057616E67, '2019-03-25 17:31:18');
INSERT INTO `admin_tag` VALUES ('3', 'project_developer', 'mmc-service', 'LiJianBiao', 0x4A69616E4269616F204C69, '2019-03-25 17:54:15');
INSERT INTO `admin_tag` VALUES ('4', 'project_master', 'mmc-service', 'LiJianBiao', 0x4A69616E4269616F204C69, '2019-03-25 17:54:43');
INSERT INTO `admin_tag` VALUES ('5', 'project_tester', 'mmc-service', 'LiGen', 0x47656E204C69, '2019-03-25 17:54:49');

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `id` int(10) NOT NULL,
  `username` varchar(60) COLLATE utf8_bin NOT NULL,
  `role_code` varchar(60) COLLATE utf8_bin NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uk_user_role_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of admin_user_role
-- ----------------------------
INSERT INTO `admin_user_role` VALUES ('0', 'liuyan', 'owner', '2019-03-13 17:40:54');
INSERT INTO `admin_user_role` VALUES ('1', 'liuyan', 'admin', '2019-03-21 13:30:57');

-- ----------------------------
-- Table structure for app_config
-- ----------------------------
DROP TABLE IF EXISTS `app_config`;
CREATE TABLE `app_config` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '项目名称',
  `app_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `show_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '应用简称',
  `app_type` tinyint(4) NOT NULL COMMENT '项目类型',
  `username` varchar(100) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `rep_group` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目组',
  `rep_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称',
  `gmt_modified` datetime NOT NULL COMMENT '构建锁时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_config_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of app_config
-- ----------------------------

-- ----------------------------
-- Table structure for app_datasource
-- ----------------------------
DROP TABLE IF EXISTS `app_datasource`;
CREATE TABLE `app_datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `app_show_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `datasource_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `driver_class_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `profile` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `default_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0=默认 1=非默认',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pass_app_name_datasource_id` (`app_name`,`datasource_id`,`profile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of app_datasource
-- ----------------------------

-- ----------------------------
-- Table structure for app_properties
-- ----------------------------
DROP TABLE IF EXISTS `app_properties`;
CREATE TABLE `app_properties` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) COLLATE utf8_bin NOT NULL,
  `value` text COLLATE utf8_bin NOT NULL,
  `app_name` varchar(60) COLLATE utf8_bin NOT NULL,
  `app_show_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `profile` varchar(60) COLLATE utf8_bin NOT NULL,
  `label` varchar(60) COLLATE utf8_bin NOT NULL,
  `state` tinyint(4) NOT NULL,
  `edit_value` text COLLATE utf8_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_properties_app_name_profile_key` (`app_name`,`profile`,`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of app_properties
-- ----------------------------
INSERT INTO `app_properties` VALUES ('2', 'management.endpoints.web.exposure.include', 0x2A, 'application', null, 'dev', 'master', '1', 0x2A);
INSERT INTO `app_properties` VALUES ('3', 'footstone.datasource.uri', 0x687474703A2F2F6C6F63616C686F73743A38303830, 'application', null, 'dev', 'master', '1', 0x687474703A2F2F6C6F63616C686F73743A38303831);
INSERT INTO `app_properties` VALUES ('4', 'eureka.client.service-url.defaultZone', 0x687474703A2F2F6C6F63616C686F73743A383038302F657572656B61, 'application', null, 'dev', 'master', '1', 0x687474703A2F2F6C6F63616C686F73743A383038302F657572656B61);
INSERT INTO `app_properties` VALUES ('5', 'eureka.instance.metadata-map.zone', 0x646576, 'application', null, 'dev', 'master', '1', 0x646576);

-- ----------------------------
-- Table structure for deploy_packaging
-- ----------------------------
DROP TABLE IF EXISTS `deploy_packaging`;
CREATE TABLE `deploy_packaging` (
  `id` int(10) NOT NULL,
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `package_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0=构建中 1=构建成功 2=构建失败',
  `package_result` text COLLATE utf8_bin NOT NULL COMMENT '构建结果',
  `iteration_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `branch_name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_deploy_packaging_app_name_branch_name` (`app_name`,`branch_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of deploy_packaging
-- ----------------------------

-- ----------------------------
-- Table structure for project_iteration
-- ----------------------------
DROP TABLE IF EXISTS `project_iteration`;
CREATE TABLE `project_iteration` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '迭代名称',
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '项目名称',
  `app_show_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `branch_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '分支名称',
  `stage` tinyint(4) NOT NULL COMMENT '迭代阶段',
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of project_iteration
-- ----------------------------
