-- ----------------------------
-- Table structure for config_app
-- ----------------------------
DROP TABLE [dbo].[config_app]
GO
CREATE TABLE [dbo].[config_app] (
[id] bigint NOT NULL IDENTITY(1,1) ,
[create_by] bigint NULL ,
[created_date] datetime2(7) NULL ,
[last_modified_by] bigint NULL ,
[last_modified_date] datetime2(7) NULL ,
[version] bigint NULL ,
[app_id] varchar(255) NULL ,
[name] varchar(255) NULL ,
[org_name] varchar(255) NULL ,
[owner_name] varchar(255) NULL
)

GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_app',
'COLUMN', N'app_id')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'应用id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'app_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'应用id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'app_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_app',
'COLUMN', N'name')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'应用名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'应用名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_app',
'COLUMN', N'org_name')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'部门名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'org_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'部门名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'org_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_app',
'COLUMN', N'owner_name')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'所属人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'owner_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'所属人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_app'
, @level2type = 'COLUMN', @level2name = N'owner_name'
GO

-- ----------------------------
-- Table structure for config_namespace
-- ----------------------------
DROP TABLE [dbo].[config_namespace]
GO
CREATE TABLE [dbo].[config_namespace] (
[id] bigint NOT NULL IDENTITY(1,1) ,
[create_by] bigint NULL ,
[created_date] datetime2(7) NULL ,
[last_modified_by] bigint NULL ,
[last_modified_date] datetime2(7) NULL ,
[version] bigint NULL ,
[app_id] varchar(255) NULL ,
[comment] varchar(255) NULL ,
[name] varchar(255) NULL
)

GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_namespace',
'COLUMN', N'app_id')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'appId'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_namespace'
, @level2type = 'COLUMN', @level2name = N'app_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'appId'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_namespace'
, @level2type = 'COLUMN', @level2name = N'app_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_namespace',
'COLUMN', N'comment')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'注释'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_namespace'
, @level2type = 'COLUMN', @level2name = N'comment'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'注释'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_namespace'
, @level2type = 'COLUMN', @level2name = N'comment'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_namespace',
'COLUMN', N'name')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'环境变量 dev,test,pre,prod'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_namespace'
, @level2type = 'COLUMN', @level2name = N'name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'环境变量 dev,test,pre,prod'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_namespace'
, @level2type = 'COLUMN', @level2name = N'name'
GO

-- ----------------------------
-- Table structure for config_operator_logs
-- ----------------------------
DROP TABLE [dbo].[config_operator_logs]
GO
CREATE TABLE [dbo].[config_operator_logs] (
[id] bigint NOT NULL IDENTITY(1,1) ,
[create_by] bigint NULL ,
[created_date] datetime2(7) NULL ,
[last_modified_by] bigint NULL ,
[last_modified_date] datetime2(7) NULL ,
[version] bigint NULL ,
[description] varchar(1000) NULL ,
[entity_name] varchar(255) NULL ,
[operator_name] varchar(255) NULL
)

GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_operator_logs',
'COLUMN', N'description')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'描述'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_operator_logs'
, @level2type = 'COLUMN', @level2name = N'description'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'描述'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_operator_logs'
, @level2type = 'COLUMN', @level2name = N'description'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_operator_logs',
'COLUMN', N'entity_name')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'表名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_operator_logs'
, @level2type = 'COLUMN', @level2name = N'entity_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'表名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_operator_logs'
, @level2type = 'COLUMN', @level2name = N'entity_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_operator_logs',
'COLUMN', N'operator_name')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'操作类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_operator_logs'
, @level2type = 'COLUMN', @level2name = N'operator_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'操作类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_operator_logs'
, @level2type = 'COLUMN', @level2name = N'operator_name'
GO

-- ----------------------------
-- Table structure for config_serverconfig
-- ----------------------------
DROP TABLE [dbo].[config_serverconfig]
GO
CREATE TABLE [dbo].[config_serverconfig] (
[id] bigint NOT NULL IDENTITY(1,1) ,
[create_by] bigint NULL ,
[created_date] datetime2(7) NULL ,
[last_modified_by] bigint NULL ,
[last_modified_date] datetime2(7) NULL ,
[version] bigint NULL ,
[app_id] varchar(255) NULL ,
[app_namespace_id] bigint NULL ,
[comment] varchar(255) NULL ,
[item_key] varchar(255) NULL ,
[item_value] varchar(255) NULL ,
[status] int NULL ,
[sync_date] datetime2(7) NULL
)

GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_serverconfig',
'COLUMN', N'app_id')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'appId'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'app_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'appId'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'app_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_serverconfig',
'COLUMN', N'comment')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'comment'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'comment'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_serverconfig',
'COLUMN', N'item_key')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'属性key'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'item_key'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'属性key'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'item_key'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_serverconfig',
'COLUMN', N'item_value')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'属性值'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'item_value'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'属性值'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'item_value'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_serverconfig',
'COLUMN', N'status')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'同步状态 0:未同步,1:已同步'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'status'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'同步状态 0:未同步,1:已同步'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'status'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
'SCHEMA', N'dbo',
'TABLE', N'config_serverconfig',
'COLUMN', N'sync_date')) > 0)
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'同步时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'sync_date'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'同步时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'config_serverconfig'
, @level2type = 'COLUMN', @level2name = N'sync_date'
GO

-- ----------------------------
-- Indexes structure for table config_app
-- ----------------------------

ALTER TABLE [dbo].[config_app] ADD PRIMARY KEY ([id])
GO

ALTER TABLE [dbo].[config_namespace] ADD PRIMARY KEY ([id])
GO

ALTER TABLE [dbo].[config_operator_logs] ADD PRIMARY KEY ([id])
GO

ALTER TABLE [dbo].[config_serverconfig] ADD PRIMARY KEY ([id])
GO



INSERT INTO [dbo].[config_role] ([name],[version], [create_by], [last_modified_by], [created_date], [last_modified_date]) VALUES (N'ROLE_admin',N'0',null, null, N'2017-04-20 14:14:16.000', N'2017-08-11 14:14:18.000')
GO
INSERT INTO [dbo].[config_role] ([name],[version], [create_by], [last_modified_by], [created_date], [last_modified_date]) VALUES (N'ROLE_user',N'0',null, null, N'2017-04-20 14:14:16.000', N'2017-08-11 14:14:18.000')
GO
