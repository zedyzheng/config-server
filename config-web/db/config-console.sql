CREATE TABLE `config_app` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `name` varchar(500) NOT NULL DEFAULT 'default' COMMENT '应用名',
  `org_name` varchar(64) NOT NULL DEFAULT 'default' COMMENT '部门名字',
  `owner_name` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ownerName',
  PRIMARY KEY (`id`),
  KEY `IX_app_id` (`app_id`(191)),
  KEY `IX_name` (`name`(191)),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

CREATE TABLE `config_app_namespace` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT 'namespace名字，注意，需要全局唯一',
  `app_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'app id',
  `comment` varchar(64) NOT NULL DEFAULT '' COMMENT '注释',
  PRIMARY KEY (`id`),
  KEY `IX_app_id` (`app_id`),
  KEY `name_app_id` (`name`,`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用namespace定义';

CREATE TABLE `config_serverconfig` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `app_namespace_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'NamespaceId',
  `app_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'app id',
  `item_key` varchar(128) NOT NULL DEFAULT 'default' COMMENT '配置项Key',
  `item_value` longtext NOT NULL COMMENT '配置项值',
  `comment` varchar(1024) DEFAULT '' COMMENT '注释',
  `status` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '同步状态 0:未同步,1:已同步',
  `sync_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '同步时间',
  PRIMARY KEY (`id`),
  KEY `IX_app_namespace_id` (`app_namespace_id`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置项目';

CREATE TABLE `config_operator_logs` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `entity_name` varchar(50) NOT NULL DEFAULT 'default' COMMENT '表名',
  `operator_name` varchar(50) NOT NULL DEFAULT 'default' COMMENT '操作类型',
  `comment` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志审计表';