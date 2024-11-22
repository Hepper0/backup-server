drop table if exists bk_agent;
create table bk_agent(
                    agent_id int auto_increment primary key,
                    hostname varchar(100),
                    ip varchar(20),
                    remark varchar(500),
                    status int,
                    deleted int default 0,
                    create_time datetime default now(),
                    create_by varchar(64),
                    update_time datetime,
                    update_by varchar(64)
);
drop table if exists bk_agent_resource;
create table bk_agent_resource(
                      id int auto_increment primary key,
                      agent_id varchar(100),
                      resource_id varchar(100)
);
CREATE UNIQUE INDEX index_name ON bk_agent_resource (agent_id, resource_id);



drop table if exists bk_task;
create table bk_task(
                      task_id varchar(50) primary key,
                      ip varchar(30),
                      task_type int,
                      target varchar(100),
                      backup_path varchar(200),
                      start_time datetime,
                      end_time datetime,
                      status int,
                      remark varchar(500),
                      deleted int default 0,
                      create_time datetime default now(),
                      create_by varchar(64),
                      update_time datetime,
                      update_by varchar(64)
);

drop table if exists bk_scheduler;
create table bk_scheduler(
                     scheduler_id int auto_increment primary key,
                     name varchar(100),
                     min varchar(10),
                     hour varchar(10),
                     day varchar(10),
                     mon varchar(10),
                     week varchar(10),
                     status int,
                     remark varchar(500),
                     deleted int default 0,
                     create_time datetime default now(),
                     create_by varchar(64),
                     update_time datetime,
                     update_by varchar(64)
);

drop table if exists bk_agent_scheduler;
create table bk_agent_scheduler(
                     id int auto_increment primary key,
                     agent_id varchar(100),
                     scheduler varchar(100),
                     deleted int default 0,
                     create_time datetime default now(),
                     create_by varchar(64),
                     update_time datetime,
                     update_by varchar(64)
);
CREATE UNIQUE INDEX index_name ON bk_agent_scheduler (agent_id, scheduler);


drop if exists bk_agent_config;
create table bk_agent_config(
    id int auto_increment primary key,
    sf_host varchar(20),
    sf_port varchar(10),
    sf_ak varchar(100),
    sf_sk varchar(100),
    task_timeout int,
    redis_host varchar(20),
    redis_port varchar(10),
    redis_db varchar(5),
    redis_password varchar(50),
    state int default 1,
    create_time datetime default now(),
    update_time datetime
);
-- SCP测试环境配置
insert into bk_agent_config (sf_host, sf_port, sf_ak, sf_sk, task_timeout, redis_db) values('10.38.19.113', '4430', '4a78f43ad3b94dc5bc20fae500200a2b', '31659b3c6e5e43eca0ebc611d4ed36b6', 3600, 0);


-- 主菜单
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `remark`)
VALUES (5, '备份管理', 0, 0, 'agent', NULL, '', '', 1, 0, 'M', '0', '0', '', 'tree', 'admin', '代理管理目录');

-- 菜单
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, remark)
VALUES (2000, '代理管理', 5, 1, 'agent', 'agent/agent/index', NULL, '', 1, 0, 'C', '0', '0', 'server:agent:list', 'tree', 'admin', '代理菜单');

-- 按钮
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`)
VALUES (2001, '代理查询', 2000, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'server:agent:query', '#', 'admin');
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`)
VALUES (2002, '代理新增', 2000, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'server:agent:add', '#', 'admin');
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`)
VALUES (2003, '代理修改', 2000, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'server:agent:edit', '#', 'admin');
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`)
VALUES (2004, '代理删除', 2000, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'server:agent:remove', '#', 'admin');
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`)
VALUES (2005, '代理导出', 2000, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'server:agent:export', '#', 'admin');

-- 菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(3000, '任务管理', '5', '2', 'task', 'agent/task/index', 1, 0, 'C', '0', '0', 'server:task:list', 'job', 'admin', sysdate(), '', null, '备份任务菜单');

-- 按钮
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(3001, '备份任务查询', 3000, '1',  '#', '', 1, 0, 'F', '0', '0', 'server:task:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(3002, '备份任务新增', 3000, '2',  '#', '', 1, 0, 'F', '0', '0', 'server:task:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(3003, '备份任务修改', 3000, '3',  '#', '', 1, 0, 'F', '0', '0', 'server:task:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(3004, '备份任务删除', 3000, '4',  '#', '', 1, 0, 'F', '0', '0', 'server:task:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(3005, '备份任务导出', 3000, '5',  '#', '', 1, 0, 'F', '0', '0', 'server:task:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4000, '计划管理', '5', '3', 'scheduler', 'agent/scheduler/index', 1, 0, 'C', '0', '0', 'server:scheduler:list', 'build', 'admin', sysdate(), '', null, '备份计划菜单');


-- 按钮
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4001, '备份计划查询', 4000, '1',  '#', '', 1, 0, 'F', '0', '0', 'server:scheduler:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4002, '备份计划新增', 4000, '2',  '#', '', 1, 0, 'F', '0', '0', 'server:scheduler:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4003, '备份计划修改', 4000, '3',  '#', '', 1, 0, 'F', '0', '0', 'server:scheduler:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4004, '备份计划删除', 4000, '4',  '#', '', 1, 0, 'F', '0', '0', 'server:scheduler:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(4005, '备份计划导出', 4000, '5',  '#', '', 1, 0, 'F', '0', '0', 'server:scheduler:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(5000, '配置管理', '5', '4', 'config', 'agent/config/index', 1, 0, 'C', '0', '0', 'server:config:list', 'edit', 'admin', sysdate(), '', null, '代理配置菜单');


-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(5001, '代理配置查询', 5000, '1',  '#', '', 1, 0, 'F', '0', '0', 'server:config:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(5002,'代理配置新增', 5000, '2',  '#', '', 1, 0, 'F', '0', '0', 'server:config:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(5003,'代理配置修改', 5000, '3',  '#', '', 1, 0, 'F', '0', '0', 'server:config:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(5004,'代理配置删除', 5000, '4',  '#', '', 1, 0, 'F', '0', '0', 'server:config:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(5005,'代理配置导出', 5000, '5',  '#', '', 1, 0, 'F', '0', '0', 'server:config:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6000, '资源管理', '5', '5', 'resource', 'agent/resource/index', 1, 0, 'C', '0', '0', 'server:resource:list', 'tree-table', 'admin', sysdate(), '', null, '备份目标资源菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6001, '备份目标资源查询', 6000, '1',  '#', '', 1, 0, 'F', '0', '0', 'server:resource:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6002, '备份目标资源新增', 6000, '2',  '#', '', 1, 0, 'F', '0', '0', 'server:resource:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6003, '备份目标资源修改', 6000, '3',  '#', '', 1, 0, 'F', '0', '0', 'server:resource:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6004, '备份目标资源删除', 6000, '4',  '#', '', 1, 0, 'F', '0', '0', 'server:resource:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6005, '备份目标资源导出', 6000, '5',  '#', '', 1, 0, 'F', '0', '0', 'server:resource:export',       '#', 'admin', sysdate(), '', null, '');
