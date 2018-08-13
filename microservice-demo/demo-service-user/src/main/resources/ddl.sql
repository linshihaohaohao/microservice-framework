SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `demo_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(32) DEFAULT NULL COMMENT '账号',
  `password` varchar(32) DEFAULT NULL COMMENT '登录密码',
  `valid_flag` int(11) DEFAULT '1' COMMENT '有效性标注',
  `sign_in_time` datetime DEFAULT NULL COMMENT '注册时间',
  `introduction` text COMMENT '介绍',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `demo_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `token` varchar(32) DEFAULT NULL COMMENT 'token',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `valid_flag` int(11) DEFAULT '1' COMMENT '有效标识',
  `last_refresh_time` datetime DEFAULT NULL COMMENT '有效性最后刷新时间',
  PRIMARY KEY (`id`),
  KEY `idx_userId` (`user_id`) USING BTREE,
  KEY `idx_token` (`token`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='token表';