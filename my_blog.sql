/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.25 : Database - my_blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`my_blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `my_blog`;

/*Table structure for table `blog` */

DROP TABLE IF EXISTS `blog`;

CREATE TABLE `blog` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `title` varchar(255) DEFAULT NULL COMMENT '博客标题',
  `author_id` int DEFAULT NULL COMMENT '博客作者id',
  `category_id` int DEFAULT NULL COMMENT '博客分类id',
  `content` text COMMENT '博客内容',
  `is_exit` tinyint DEFAULT '1' COMMENT '博客是否存在',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '博客创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `blog` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(18) DEFAULT NULL COMMENT '分类名字',
  `is_exit` tinyint DEFAULT '1' COMMENT '分类是否存在',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '分类创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `category` */

insert  into `category`(`id`,`name`,`is_exit`,`create_time`) values 
(1,'Java',1,'2021-09-01 17:59:17'),
(2,'C',1,'2021-09-01 17:59:21'),
(3,'C++',1,'2021-09-01 17:59:24'),
(4,'Python',1,'2021-09-01 17:59:31'),
(5,'其他',1,'2021-09-01 17:59:41'),
(6,'公告',1,'2021-09-03 22:42:05');

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `blog_id` int DEFAULT NULL COMMENT '评论所在博客的id',
  `comment_id` int DEFAULT NULL COMMENT '要回复的评论id',
  `sender_id` int DEFAULT NULL COMMENT '发送评论人的id',
  `receiver_name` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接受评论人的名字',
  `idx` int DEFAULT '1' COMMENT '评论的层级',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `is_exit` tinyint DEFAULT '1' COMMENT '评论是否存在',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `comment` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名字',
  `password` varchar(18) DEFAULT NULL COMMENT '用户密码',
  `is_exit` tinyint(1) DEFAULT '1' COMMENT '用户是否存在',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
