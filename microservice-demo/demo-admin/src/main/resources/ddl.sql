SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin_sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_menu`;
CREATE TABLE `admin_sys_menu` (
  `menu_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_code` varchar(64) DEFAULT NULL COMMENT '菜单编码',
  `menu_name` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `menu_label` varchar(64) DEFAULT NULL COMMENT '菜单显示的名称',
  `ifleaf` varchar(5) DEFAULT NULL COMMENT '是否子叶 0否 1是',
  `menu_level` int(6) DEFAULT NULL COMMENT '菜单级别',
  `display_order` int(6) DEFAULT NULL COMMENT '菜单排序',
  `function_path` varchar(256) DEFAULT NULL COMMENT '菜单功能路径',
  `function_desc` varchar(256) DEFAULT NULL COMMENT '功能描述',
  `menu_type` varchar(20) DEFAULT NULL COMMENT '菜单类型 app:app类菜单 pc:pc类菜单',
  `icon_img` varchar(255) DEFAULT NULL COMMENT '图标的路径',
  `menu_url` varchar(255) DEFAULT NULL COMMENT 'vw链接(app时连接页面)',
  `parents_id` int(10) DEFAULT NULL COMMENT '父节点ID',
  `parents_code` varchar(64) DEFAULT NULL COMMENT '父节点编码',
  `menu_seq` varchar(256) DEFAULT NULL COMMENT '菜单序列',
  `app_code` varchar(20) DEFAULT NULL COMMENT '所属应用类型',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '所属子系统编码',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2366 DEFAULT CHARSET=utf8 COMMENT='菜单表';

INSERT INTO `admin_sys_menu` VALUES (0, 'menu_sys', '系统管理', '系统管理', '0', 0, 0, '#', '系统管理', 'pc', NULL, NULL, -1, 'pcRoot', '.0.', 'mall_common', NULL);
INSERT INTO `admin_sys_menu` VALUES (2020, 'auth_manage', '权限管理', '权限管理', '0', 1, 1, '#', '权限管理', 'pc', NULL, NULL, 0, 'pcRoot', '.2020.', 'mall_common', NULL);
INSERT INTO `admin_sys_menu` VALUES (2021, 'menu_manage', '菜单管理', '菜单管理', '1', 2, 1, '/menuForm', '菜单管理', 'pc', '', '', 2020, 'auth_manage', '.2020.2021.', 'mall_common', NULL);
INSERT INTO `admin_sys_menu` VALUES (2131, 'mall_erp_role_manage_pc', '角色管理', '角色管理', '1', 2, 2, '/role/roleList', '角色管理', 'pc', '', '', 2020, 'auth_manage', '.2020.2131.', 'mall_common', 'mall_common');
INSERT INTO `admin_sys_menu` VALUES (2341, 'user_role_manage', '用户角色管理', '用户角色管理', '1', 2, 3, '/userRole/userRoleList', '', 'pc', NULL, NULL, 2020, 'pcRoot', '.2020.2341.', 'mall_common', NULL);
INSERT INTO `admin_sys_menu` VALUES (2352, 'dict_namage', '业务字典', '业务字典', '1', 2, 5, '/dict/dictList', '业务字典', 'pc', NULL, NULL, 2020, 'pcRoot', '.2020.2352.', 'mall_common', NULL);

-- ----------------------------
-- Table structure for `admin_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_role`;
CREATE TABLE `admin_sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_code` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `app_code` varchar(64) DEFAULT NULL COMMENT '所属应用系统编码',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

INSERT INTO `admin_sys_role` VALUES ('1', 'sysadmin', '系统管理员', NULL, '系统管理员,菜单、权限、用户、授权管理、管理', '0', '2017-7-4 14:20:00');


-- ----------------------------
-- Table structure for `admin_sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_role_menu`;
CREATE TABLE `admin_sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `menu_code` varchar(64) DEFAULT NULL COMMENT '菜单编码',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';

INSERT INTO `admin_sys_role_menu` VALUES ('1', '2020', 'auth_manage', 'sysadmin', '2018-1-5 11:45:53');
INSERT INTO `admin_sys_role_menu` VALUES ('1', '2021', 'auth_manage', 'sysadmin', '2018-1-5 11:45:53');
INSERT INTO `admin_sys_role_menu` VALUES ('1', '2131', 'auth_manage', 'sysadmin', '2018-1-5 11:45:53');
INSERT INTO `admin_sys_role_menu` VALUES ('1', '2341', 'auth_manage', 'sysadmin', '2018-1-5 11:45:53');
INSERT INTO `admin_sys_role_menu` VALUES ('1', '2352', 'auth_manage', 'sysadmin', '2018-1-5 11:45:53');

-- ----------------------------
-- Table structure for `admin_sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_user_role`;
CREATE TABLE `admin_sys_user_role` (
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '人员ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_code` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员角色关系表';

INSERT INTO `admin_sys_user_role` VALUES ('0', '1', 'pc_main', '0', '2017-6-19 15:25:12');

-- ----------------------------
-- Table structure for `admin_sys_dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_dict_type`;
CREATE TABLE `admin_sys_dict_type` (
  `dict_type_code` varchar(32) NOT NULL COMMENT '业务字典编码',
  `dict_type_bane` varchar(100) DEFAULT NULL COMMENT '业务字典名称',
  PRIMARY KEY (`dict_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for `admin_sys_dict_entry`
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_dict_entry`;
CREATE TABLE `admin_sys_dict_entry` (
  `dict_type_code` varchar(32) NOT NULL COMMENT '业务字典子选项',
  `dict_code` varchar(32) NOT NULL,
  `dict_name` varchar(200) DEFAULT NULL COMMENT '业务字典子选项名称',
  `status` int(1) DEFAULT NULL COMMENT '状态（1使用中/0已废弃）',
  `sort_no` int(4) DEFAULT NULL COMMENT '排序编码',
  PRIMARY KEY (`dict_type_code`,`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for `admin_sys_log_info`
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_log_info`;
CREATE TABLE `admin_sys_log_info` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `user_ip` varchar(256) DEFAULT NULL COMMENT '来源IP',
  `broswer` varchar(256) DEFAULT NULL COMMENT '浏览器类型',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `time_consume` int(11) DEFAULT NULL COMMENT '执行时间（ms）',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `description` varchar(5000) DEFAULT NULL COMMENT '操作结果',
  `exception_str` text COMMENT '异常信息',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';