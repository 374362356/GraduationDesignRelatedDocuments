/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.29-log : Database - pzh_meeting_management
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pzh_meeting_management` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pzh_meeting_management`;

/*Table structure for table `activity_person` */

DROP TABLE IF EXISTS `activity_person`;

CREATE TABLE `activity_person` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `conference_id` int(50) NOT NULL COMMENT '会议id',
  `person_id` int(50) NOT NULL COMMENT '参与人员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='活动人员表';

/*Data for the table `activity_person` */

insert  into `activity_person`(`id`,`conference_id`,`person_id`) values (1,1,5),(2,2,4),(3,3,3),(4,4,2),(5,5,1),(6,6,9),(7,7,6),(8,8,8),(9,3,7);

/*Table structure for table `conference` */

DROP TABLE IF EXISTS `conference`;

CREATE TABLE `conference` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '会议名称',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `place` varchar(200) NOT NULL COMMENT '举办地点',
  `host` varchar(200) NOT NULL COMMENT '举办方',
  `desc` varchar(200) DEFAULT NULL COMMENT '会议简要',
  `ongoing` int(10) DEFAULT NULL COMMENT '会议状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='会议表';

/*Data for the table `conference` */

insert  into `conference`(`id`,`name`,`start_time`,`end_time`,`place`,`host`,`desc`,`ongoing`) values (1,'展销会1','2021-01-25 17:45:47','2021-01-25 17:45:47','操场','群众','会议简要1',7),(2,'展销会2','2021-01-25 17:46:44','2021-01-25 17:46:44','操场','群众','会议简要1',7),(3,'展销会3','2021-01-25 17:47:34','2021-01-25 17:47:34','操场','群众','会议简要00',7),(4,'展销会4','2021-01-25 17:47:34','2021-01-25 17:47:34','操场','群众','会议简要41',7),(5,'展销会5','2021-01-25 17:47:00','2021-01-25 17:47:00','操场','群众567','会议简要21',7),(6,'a','2021-02-21 17:01:00','2021-02-22 18:02:00','b','cc','会议简要88',7),(7,'会议1','2021-04-01 16:00:00','2021-04-02 16:00:00','操场','图图','会议简要343',7),(8,'会议2','2021-04-01 16:00:00','2021-04-02 16:00:00','操场11','牛爷爷','会议简要12',7),(9,'会议3','2021-04-07 16:00:00','2021-04-08 16:00:00','成都','小新','会议简要22',7),(10,'会议测试','2021-04-13 16:00:00','2021-04-14 16:00:00','1601','qq','这是个简要',7);

/*Table structure for table `fileupload` */

DROP TABLE IF EXISTS `fileupload`;

CREATE TABLE `fileupload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(100) NOT NULL,
  `file_url` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='文件上传下载表';

/*Data for the table `fileupload` */

insert  into `fileupload`(`id`,`file_name`,`file_url`,`create_time`) values (1,'test1','http://yhlssh.top/Test_Log4j.log','2021-03-25 09:21:35'),(4,'删除','http://yhlssh.top/luis.PNG','2021-03-25 13:43:13'),(5,'rose','http://yhlssh.top/rose.jpg','2021-03-25 13:46:43'),(6,'ico','http://yhlssh.top/favicon.ico','2021-03-25 13:48:40');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `content` varchar(50) NOT NULL COMMENT '发送内容',
  `staff_id` int(50) NOT NULL COMMENT '发送人员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='信息表';

/*Data for the table `message` */

insert  into `message`(`id`,`send_time`,`content`,`staff_id`) values (1,'2021-01-25 18:01:50','2021-01-25 18:01:50',5),(2,'2021-01-25 18:01:51','2021-01-25 18:01:51',4),(3,'2021-01-25 18:01:51','2021-01-25 18:01:51',3),(4,'2021-01-25 18:01:51','2021-01-25 18:01:51',2),(5,'2021-01-25 18:01:51','2021-01-25 18:01:51',1);

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `new_time` datetime NOT NULL COMMENT '发布时间',
  `content_title` varchar(100) NOT NULL COMMENT '发布标题',
  `content` varchar(500) NOT NULL COMMENT '发布内容',
  `publish_id` int(50) NOT NULL COMMENT '发布人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='公告表';

/*Data for the table `news` */

insert  into `news`(`id`,`new_time`,`content_title`,`content`,`publish_id`) values (1,'2021-01-25 18:07:00','公告，测试1','在一般星型模型中，用户信息和访问行为一般分别存放在维度表和事实表中。这里我们为了更加方便的解释 Doris 的数据模型，将两部分信息统一存放在一张表中。  表中的列按照是否设置了 AggregationType，分为 Key (维度列) 和 Value（指标列）。',1),(2,'2021-01-25 18:07:11','公告，测试2','当我们导入数据时，对于 Key 列相同的行会聚合成一行，而 Value 列会按照设置的 AggregationType 进行聚合。 AggregationType 目前有以下四种聚合方式：  SUM：求和，多行的 Value 进行累加。 ',3),(3,'2021-01-25 18:07:11','公告，测试3','3333',2),(4,'2021-01-25 18:07:11','公告，测试4','REPLACE：替代，下一批数据中的 Value 会替换之前导入过的行中的 Value。 MAX：保留最大值。 MIN：保留最小值。',8),(5,'2021-01-25 18:07:00','公告，测试5','没有设置 AggregationType 的，如 user_id、date、age ... 等称为 Key，而设置了 AggregationType 的称为 Value。  ',9),(7,'2021-03-18 06:29:00','发布测试','这是内容123',2),(8,'2021-04-13 07:10:41','是的','测试定时',6);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(64) NOT NULL COMMENT '权限名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int(11) NOT NULL COMMENT '是否启用',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL',
  `path` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `permission` */

insert  into `permission`(`id`,`p_name`,`create_time`,`status`,`url`,`path`) values (1,'all','2021-01-25 18:12:22',1,'/',NULL),(2,'conference','2021-01-25 18:12:22',1,'conference/findAll','/conference'),(3,'news','2021-01-25 18:12:22',1,'news/findAll','/news/**'),(4,'person','2021-01-25 18:12:22',1,'person/findAll','/person/**'),(5,'staff','2021-01-25 18:12:22',1,'staff/findAll','/staff/**'),(6,'suggestion','2021-03-29 13:56:37',1,'suggestion/findAll','/suggestion/**'),(7,'user','2021-03-29 13:56:52',1,'user/findAll','/user/**'),(8,'userInfo','2021-03-29 14:03:37',1,'user/getUserInfo','/user/**'),(9,'conference','2021-03-31 09:38:33',1,'conference/fuzzy','/conference'),(10,'conference','2021-03-31 09:39:40',1,'conference/conference_insert','/conference'),(11,'conference','2021-03-31 09:40:36',1,'conference/conference_update','/'),(12,'conference','2021-03-31 09:41:20',1,'conference/conference_delete','/'),(13,'suggestion','2021-04-08 10:16:34',1,'suggestion/findAll','/suggestion'),(14,'upload','2021-04-08 10:18:02',1,'upload/findFiles','/upload'),(15,'user','2021-04-08 14:24:59',1,'user/update','/user'),(16,'news','2021-04-08 17:48:28',1,'news/fuzzy','/news'),(17,'news','2021-04-08 17:49:07',1,'news/news_update','/'),(18,'news','2021-04-08 17:49:38',1,'news/news_delete','/'),(19,'news','2021-04-08 17:50:16',1,'news/findPublishName','/'),(20,'news','2021-04-08 17:50:58',1,'news/news_insert','/'),(21,'news','2021-04-08 17:51:28',1,'news/timing_insert','/'),(22,'person','2021-04-08 17:52:07',1,'person/toUpdate','/'),(23,'person','2021-04-08 17:52:48',1,'person/fuzzy','/'),(24,'person','2021-04-08 17:53:37',1,'person/selectAllName','/'),(25,'person','2021-04-08 17:54:07',1,'person/person_insert','/'),(26,'person','2021-04-08 17:54:42',1,'person/person_update','/'),(27,'person','2021-04-08 17:55:12',1,'person/person_delete','/'),(28,'staff','2021-04-08 17:56:11',1,'staff/fuzzy','/'),(29,'staff','2021-04-08 17:56:47',1,'staff/staff_insert','/'),(30,'staff','2021-04-08 17:56:48',1,'staff/staff_update','/'),(31,'staff','2021-04-08 17:57:40',1,'staff/staff_delete','/'),(32,'suggestion','2021-04-08 17:58:58',1,'suggestion/suggestion_delete','/'),(33,'suggestion','2021-04-08 17:59:00',1,'suggestion/fuzzy','/'),(34,'suggestion','2021-04-08 17:59:01',1,'suggestion/selectOne','/'),(35,'user','2021-04-08 18:01:13',1,'user/fuzzy','/'),(36,'user','2021-04-08 18:02:11',1,'user/user_insert','/'),(37,'user','2021-04-08 18:03:09',1,'user/user_delete','/'),(38,'user','2021-04-08 18:03:36',1,'user/toUpdate','/'),(39,'upload','2021-04-08 18:09:49',1,'upload/file_upload','/'),(40,'upload','2021-04-08 18:10:13',1,'upload/file_delete','/'),(41,'upload','2021-04-08 18:10:15',1,'upload/fuzzyFile','/'),(42,'upload','2021-04-08 18:12:41',1,'upload/file_update','/'),(43,'reach','2021-04-11 18:21:15',1,'reach/findAll','/'),(44,'reach','2021-04-11 18:23:33',1,'reach/reach_update','/'),(45,'reach','2021-04-11 18:24:12',1,'reach/findOne','/'),(46,'reach','2021-04-11 18:24:39',1,'reach/del_reach','/');

/*Table structure for table `person` */

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(100) NOT NULL COMMENT '姓名',
  `age` int(50) NOT NULL COMMENT '年龄',
  `gender` tinyint(1) NOT NULL COMMENT '性别',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(100) NOT NULL COMMENT '电话',
  `title` varchar(100) DEFAULT NULL COMMENT '职称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='参会人员表';

/*Data for the table `person` */

insert  into `person`(`id`,`p_name`,`age`,`gender`,`email`,`phone`,`title`) values (1,'王五',11,1,'3743623@qq.com','13512345678','叫兽1'),(2,'张三',22,0,'3743623@qq.com','13512345678','叫兽2'),(3,'王五',33,1,'3743623@qq.com','13512345678','叫兽3'),(4,'张三',44,1,'3743623@qq.com','13512345678','叫兽4'),(6,'测试',8,1,'123','15127384950','test'),(7,'567',8,1,'男','15182736459','嘿嘿'),(8,'user',1,1,'12@qq.com','13698216795','哈哈'),(9,'富贵',88,1,'123','15127384950','test');

/*Table structure for table `reach` */

DROP TABLE IF EXISTS `reach`;

CREATE TABLE `reach` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `reach_time` datetime NOT NULL COMMENT '到会时间',
  `confirm` int(10) NOT NULL COMMENT '到会确认',
  `user_id` int(50) NOT NULL COMMENT '参会人id',
  `staff_id` int(50) NOT NULL COMMENT '会议负责人员id',
  `conference_id` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='到会时间表';

/*Data for the table `reach` */

insert  into `reach`(`id`,`reach_time`,`confirm`,`user_id`,`staff_id`,`conference_id`) values (1,'2021-04-11 04:52:25',11,1,5,5),(2,'2021-04-11 04:48:19',12,2,4,6),(3,'2021-01-25 18:19:52',11,3,3,3),(4,'2021-01-25 18:19:52',11,4,2,2),(5,'2021-01-25 18:19:52',12,6,1,1),(6,'2021-04-11 18:15:21',12,7,6,6),(7,'2021-04-11 18:16:20',11,8,6,7),(8,'2021-04-11 10:20:05',12,9,6,8);

/*Table structure for table `reports` */

DROP TABLE IF EXISTS `reports`;

CREATE TABLE `reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `reports` */

insert  into `reports`(`id`,`name`,`value`) values (1,'开展会议','6'),(2,'公告发布','6'),(3,'用户人数','8'),(4,'建议条数','5'),(5,'工作人员','88'),(6,'测试','22');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(100) NOT NULL COMMENT '管理员姓名',
  `description` varchar(50) NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int(11) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`id`,`r_name`,`description`,`create_time`,`status`) values (1,'root','系统管理员','2021-01-25 18:22:24',1),(2,'admin','管理员','2021-01-25 18:22:24',1),(3,'user','普通用户','2021-01-25 18:22:25',1),(5,'test','测试','2021-04-02 09:58:34',1);

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `permission_id` int(11) NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='角色_权限表';

/*Data for the table `role_permission` */

insert  into `role_permission`(`id`,`role_id`,`permission_id`) values (1,1,1),(2,2,8),(3,2,4),(4,2,3),(5,2,5),(6,2,6),(7,2,13),(8,2,14),(9,2,15),(10,2,16),(13,2,7),(14,2,2),(15,2,9),(16,2,10),(17,2,11),(18,2,12),(19,3,8),(20,3,2),(21,3,3),(22,3,4),(23,3,9),(24,3,14),(25,2,17),(26,2,18),(27,2,19),(28,2,20),(29,2,21),(30,2,22),(31,2,23),(32,2,24),(33,2,25),(34,2,26),(35,2,27),(36,2,28),(37,2,29),(38,2,30),(39,2,31),(40,2,32),(41,2,33),(42,2,34),(43,2,35),(44,2,36),(45,2,37),(46,2,38),(47,2,39),(48,2,40),(49,2,41),(50,2,42),(51,2,43),(52,2,44),(53,2,45),(54,2,46),(55,3,6),(56,3,16),(57,3,19),(58,3,23),(59,3,24),(60,3,33),(61,3,41),(62,3,34),(63,3,38),(64,3,15),(65,3,43),(66,3,45);

/*Table structure for table `role_user` */

DROP TABLE IF EXISTS `role_user`;

CREATE TABLE `role_user` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(20) NOT NULL COMMENT '角色编号',
  `user_id` varchar(20) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色_用户表';

/*Data for the table `role_user` */

insert  into `role_user`(`id`,`role_id`,`user_id`) values (1,'2','6'),(2,'1','1'),(3,'3','7'),(4,'3','2'),(5,'2','3'),(6,'2','4'),(7,'3','8');

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(100) NOT NULL COMMENT '姓名',
  `gender` tinyint(1) NOT NULL COMMENT '性别',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `phone` varchar(100) NOT NULL COMMENT '电话',
  `position` varchar(100) NOT NULL COMMENT '职务',
  `conference_id` int(50) NOT NULL COMMENT '会议编号',
  `leave` int(10) DEFAULT NULL COMMENT '是否请假',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='工作人员表';

/*Data for the table `staff` */

insert  into `staff`(`id`,`s_name`,`gender`,`email`,`phone`,`position`,`conference_id`,`leave`) values (1,'张三',1,'3743623@qq.com','13512345678','叫兽',1,9),(2,'赵六',0,'3743623@qq.com','13512345678','叫兽',2,9),(3,'李四',1,'3743623@qq.com','13512345678','叫兽',3,9),(4,'法外狂徒',0,'3743623@qq.com','13512345678','叫兽',6,9),(5,'王五',1,'3743623@qq.com','13512345678','叫兽',5,9),(6,'测试2',1,'123456@qq.com','15182937450','pp',7,8),(7,'测试2333',0,'1222@qq.com','15172638490','qq',7,9);

/*Table structure for table `suggestion` */

DROP TABLE IF EXISTS `suggestion`;

CREATE TABLE `suggestion` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `sugg_time` datetime NOT NULL COMMENT '时间',
  `content` varchar(200) NOT NULL COMMENT '建议内容',
  `person_id` int(50) NOT NULL COMMENT '建议人id',
  `staff_id` int(50) NOT NULL COMMENT '受理人员id',
  `suggestion_title` varchar(100) NOT NULL COMMENT '建议主题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='建议表';

/*Data for the table `suggestion` */

insert  into `suggestion`(`id`,`sugg_time`,`content`,`person_id`,`staff_id`,`suggestion_title`) values (1,'2021-01-25 18:27:14','与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；\n在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。',1,5,'主题1'),(2,'2021-01-25 18:27:14','控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；\n页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。',1,4,'主题3'),(3,'2021-01-25 18:27:14','简化流程：设计简洁直观的操作流程；\n清晰明确：语言表达清晰且表意明确，让用户快速理解进而作出决策；\n帮助用户识别：界面简单直白，让用户快速识别而非回忆，减少用户记忆负担。',1,3,'主题2'),(4,'2021-01-25 18:27:14','sugg4',2,2,'主题6'),(5,'2021-01-25 18:27:14','用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；\n结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。',4,1,'主题4');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `user_name` varchar(100) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `phone` varchar(100) DEFAULT NULL COMMENT '电话',
  `status` int(11) NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`nick_name`,`user_name`,`password`,`create_time`,`gender`,`phone`,`status`) values (1,'赵六','root','$2a$10$fO8Sgc7bBmmBTbqi4fJeSe1ggXz1.Xvs0dRheMq0.o0OzXICKKtrS','2021-01-25 18:31:56',0,'13512345678',1),(2,'张三1','user','123','2021-01-25 18:31:57',0,'13512345678',1),(3,'王五','user2','$2a$10$fO8Sgc7bBmmBTbqi4fJeSe1ggXz1.Xvs0dRheMq0.o0OzXICKKtrS','2021-01-25 18:31:57',0,'13512345678',1),(4,'张三','admin1','$2a$10$C0SYniEUqoeSqJUBpHU4juL0lhP4QTCABqjW/UwwjAGCQt4qByS1G','2021-01-25 18:31:57',1,'13512345678',1),(6,'mm','admin','$2a$10$aAEK7OB93VXcio5G3TTJGuLdpSUYB.sQqfi688lakT46LvA41Hn2q','2021-02-02 11:14:12',1,'15189263748',1),(7,'嘿嘿111','beauty','$2a$10$u.5gie.f91.YOpdZfuqlzuDAj7tV1VbzdlwWZ/zZjLFiJUpsWduNm','2021-02-28 07:01:18',0,'13512345670',1),(8,'3.31','test','$2a$10$f2mFW5czbNHoqwBPIqk5n.bxnsC7Hif2th2IX91SiwziO.rdxhwLW','2021-03-16 08:00:19',0,'15127384950',1),(9,'111','test111','$2a$10$rE6T8j13a/EgMC7CP73TOufOR9hksPMghspUXRZRIq3mjKkruDY9S','2021-04-01 01:52:53',1,'15176253848',-1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
