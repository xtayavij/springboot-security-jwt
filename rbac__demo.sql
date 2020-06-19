/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.26-log : Database - rbac_demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rbac_demo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `rbac_demo`;

/*Table structure for table `persistent_logins` */

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `persistent_logins` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_pid` int(11) NOT NULL COMMENT '父菜单ID',
  `menu_pids` varchar(64) NOT NULL COMMENT '当前菜单所有父菜单',
  `is_leaf` tinyint(4) NOT NULL COMMENT '0:不是叶子节点，1:是叶子节点',
  `menu_name` varchar(16) NOT NULL COMMENT '菜单名称',
  `url` varchar(64) DEFAULT NULL COMMENT '跳转URL',
  `icon` varchar(45) DEFAULT NULL,
  `icon_color` varchar(16) DEFAULT NULL,
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `level` tinyint(4) NOT NULL COMMENT '菜单层级',
  `status` tinyint(4) NOT NULL COMMENT '0:启用,1:禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`menu_pid`,`menu_pids`,`is_leaf`,`menu_name`,`url`,`icon`,`icon_color`,`sort`,`level`,`status`) values (1,0,'0',0,'系统管理',NULL,NULL,NULL,1,1,0),(2,1,'1',1,'用户管理','/sysuser',NULL,NULL,1,2,0),(3,1,'1',1,'日志管理','/syslog',NULL,NULL,2,2,0),(4,1,'1',1,'业务一','/biz1',NULL,NULL,3,2,0),(5,1,'1',1,'业务二','/biz2',NULL,NULL,4,2,0),(6,1,'1',1,'hello','/hello',NULL,NULL,5,2,0);

/*Table structure for table `sys_org` */

DROP TABLE IF EXISTS `sys_org`;

CREATE TABLE `sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_pid` int(11) NOT NULL COMMENT '上级组织编码',
  `org_pids` varchar(64) NOT NULL COMMENT '所有的父节点id',
  `is_leaf` tinyint(4) NOT NULL COMMENT '0:不是叶子节点，1:是叶子节点',
  `org_name` varchar(32) NOT NULL COMMENT '组织名',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `phone` varchar(13) DEFAULT NULL COMMENT '电话',
  `email` varchar(32) DEFAULT NULL COMMENT '邮件',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `level` tinyint(4) NOT NULL COMMENT '组织层级',
  `status` tinyint(4) NOT NULL COMMENT '0:启用,1:禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统组织结构表';

/*Data for the table `sys_org` */

insert  into `sys_org`(`id`,`org_pid`,`org_pids`,`is_leaf`,`org_name`,`address`,`phone`,`email`,`sort`,`level`,`status`) values (1,0,'0',0,'总部',NULL,NULL,NULL,1,1,0),(2,1,'1',0,'研发部',NULL,NULL,NULL,1,2,0),(3,2,'1,2',1,'研发一部',NULL,NULL,NULL,1,3,0),(4,2,'1,2',1,'研发二部',NULL,NULL,NULL,2,3,0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色名称(汉字)',
  `role_desc` varchar(128) NOT NULL DEFAULT '0' COMMENT '角色描述',
  `role_code` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色的英文code.如：ADMIN',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '角色顺序',
  `status` int(11) DEFAULT NULL COMMENT '0表示可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '角色的创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`,`role_desc`,`role_code`,`sort`,`status`,`create_time`) values (1,'管理员','管理员','admin',1,0,'2019-12-23 22:56:48'),(2,'普通用户','普通用户','common',2,0,'2019-12-23 22:57:22');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`) values (1,1,2),(2,1,3),(3,2,4),(4,2,5),(5,1,4),(6,1,5),(7,1,6);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL DEFAULT '0' COMMENT '用户名',
  `password` varchar(64) NOT NULL DEFAULT '0' COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `org_id` int(11) NOT NULL COMMENT '组织id',
  `enabled` int(11) DEFAULT NULL COMMENT '0无效用户，1是有效用户',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT 'email',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`create_time`,`org_id`,`enabled`,`phone`,`email`) values (1,'yanfa1','$2a$10$7cLDVecg4gN.O.hLv3l6Luo8dMG7pNKQizhtOj.VF3oWc4ECLaMYe','2020-06-16 11:04:59',3,1,'',NULL),(2,'admin','$2a$10$UfOXeejOxoIP8GLGHPpJhO/Z/XJpFKabmAgRGTtBJfNw7wsea9P.e','2020-06-16 11:04:54',1,1,'18761628920',NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色自增id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户自增id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`role_id`,`user_id`) values (1,2,1),(2,1,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
