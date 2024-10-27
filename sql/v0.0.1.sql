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
                      resource_id varchar(100),
                      deleted int default 0,
                      create_time datetime default now(),
                      create_by varchar(64),
                      update_time datetime,
                      update_by varchar(64)
);

drop table if exists bk_task;
create table bk_task(
                      task_id int auto_increment primary key,
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



INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `remark`)
VALUES (5, '代理管理', 0, 5, 'agent', NULL, '', '', 1, 0, 'M', '0', '0', '', 'agent', 'admin', '代理管理目录');
INSERT INTO `bk`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, remark)
VALUES (2000, '代理', 5, 1, 'agent', 'agent/agent/index', NULL, '', 1, 0, 'C', '0', '0', 'server:agent:list', '#', 'admin', '代理菜单');
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
