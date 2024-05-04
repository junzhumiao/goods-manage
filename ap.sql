/*
Navicat MySQL Data Transfer

Source Server         : jzm-novel
Source Server Version : 80032
Source Host           : localhost:3306
Source Database       : ap

Target Server Type    : MYSQL
Target Server Version : 80032
File Encoding         : 65001

Date: 2024-04-18 17:03:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ap_back_front_user
-- ----------------------------
DROP TABLE IF EXISTS `ap_back_front_user`;
CREATE TABLE `ap_back_front_user` (
  `back_user_id` bigint DEFAULT NULL,
  `font_user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_back_front_user
-- ----------------------------

-- ----------------------------
-- Table structure for ap_back_user
-- ----------------------------
DROP TABLE IF EXISTS `ap_back_user`;
CREATE TABLE `ap_back_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '2' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- ----------------------------
-- Records of ap_back_user
-- ----------------------------
INSERT INTO `ap_back_user` VALUES ('1', '管理员阿喵1号', 'admin', 'qhx20040818@163.com', '15069680202', '0', 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/2024/3/31/2088e39d7e854e548ddec3e6a54a6b34.png?Expires=4833921830&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=kWGKWrovhOe6RAIJk4SEPw2LISg%3D', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '内网IP', '2024-04-11 09:58:01', '2024-02-27 15:06:56', '0', '2024-02-27 15:07:03', '1', '2024-03-17 21:42:16', '管理员');
INSERT INTO `ap_back_user` VALUES ('2', '后台农户1', 'qhx2004', 'qhx20030118@163.com', '15069680201', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '25d55ad283aa400af464c76d713c07ad', '0', '0', '', '2024-03-17 18:27:44', '2024-03-17 18:27:49', '1', '2024-03-17 18:28:21', '1', '2024-03-31 17:38:51', '农户1号');
INSERT INTO `ap_back_user` VALUES ('101', '后台测试农户1', 'testFarmer1', '', '15069680203', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '11ea1cf4f9ed1fd9e94f451963c90365', '0', '0', '内网IP', '2024-03-30 19:22:12', null, '1', '2024-03-30 19:19:14', '1', '2024-03-30 19:19:21', '后台测试农户1');
INSERT INTO `ap_back_user` VALUES ('102', 'qhx2004', 'qhx2004', '', '15069680206', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', 'dfcc168f31ea580640f6588d46dbeffe', '0', '0', '', null, null, '1', '2024-03-31 12:06:08', '1', '2024-03-31 12:06:26', 'qhx20045');
INSERT INTO `ap_back_user` VALUES ('103', 'qhx2005', 'qhx2005', '', '15069680205', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '02d72324482c93f2a04f880984fe74bf', '0', '0', '内网IP', '2024-03-31 12:24:20', null, '1', '2024-03-31 12:14:16', null, '2024-03-31 12:24:20', '');
INSERT INTO `ap_back_user` VALUES ('104', '9号测试用户', 'qhx2009', '', '15069680209', '2', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '8f9cf925ed04dcf7931b09f185106b4e', '0', '0', '内网IP', '2024-03-31 22:30:20', null, '1', '2024-03-31 17:37:19', '1', '2024-03-31 17:49:00', '9号测试用户');

-- ----------------------------
-- Table structure for ap_category
-- ----------------------------
DROP TABLE IF EXISTS `ap_category`;
CREATE TABLE `ap_category` (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_name` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类名称',
  `create_by` bigint DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '商品分类状态（0正常 1停用）',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_category
-- ----------------------------
INSERT INTO `ap_category` VALUES ('1', '蔬菜类', '1', null, '2024-02-27 16:33:22', '2024-03-26 21:07:08', '0', '0');
INSERT INTO `ap_category` VALUES ('2', '水果类', '1', null, '2024-02-27 16:33:28', '2024-03-26 11:27:25', '0', '0');
INSERT INTO `ap_category` VALUES ('3', '谷物类', '1', '1', '2024-02-27 16:33:28', '2024-02-27 16:33:28', '0', '0');
INSERT INTO `ap_category` VALUES ('4', '畜牧类', '1', '1', '2024-02-27 16:33:28', '2024-02-27 16:33:28', '0', '0');
INSERT INTO `ap_category` VALUES ('5', '水产类', '1', '1', '2024-02-27 16:33:28', '2024-02-27 16:33:28', '0', '0');
INSERT INTO `ap_category` VALUES ('6', '海鲜养殖', '1', '1', '2024-02-27 16:33:28', '2024-03-19 09:37:17', '0', '0');
INSERT INTO `ap_category` VALUES ('7', '水产养殖类', '1', '1', '2024-03-19 09:38:23', '2024-03-19 09:38:36', '0', '0');
INSERT INTO `ap_category` VALUES ('8', '精品谷物', '1', '1', '2024-03-31 12:10:19', '2024-03-31 12:10:36', '0', '1');

-- ----------------------------
-- Table structure for ap_front_user
-- ----------------------------
DROP TABLE IF EXISTS `ap_front_user`;
CREATE TABLE `ap_front_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(30) COLLATE utf8mb4_general_ci DEFAULT '',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `invitation_code` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邀请码(同等于后台hash密码)',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- ----------------------------
-- Records of ap_front_user
-- ----------------------------
INSERT INTO `ap_front_user` VALUES ('1', '管理员阿喵前台账户', 'admin', 'qhx20040818@163.com', '15069680202', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '内网IP', '2024-03-17 18:20:14', '2024-02-27 15:06:56', '0', '2024-02-27 15:07:03', '1', null, '2024-03-31 17:08:42', '管理员');
INSERT INTO `ap_front_user` VALUES ('3', '前台测试用户2', '前台测试用户2', '', '15069680200', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', 'fcea920f7412b5da7be0cf42b8c93759', '0', '1', '', null, null, '', '2024-02-01 09:30:49', '', null, null, null);
INSERT INTO `ap_front_user` VALUES ('4', '前台测试用户3', '前台测试用户3', '', '15069680203', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '827ccb0eea8a706c4c34a16891f84e7b', '0', '1', '', null, null, '', '2024-04-01 15:30:15', '', null, null, null);
INSERT INTO `ap_front_user` VALUES ('102', '用户:0205', '用户:0205', '', '15069680205', '0', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '8d70d8ab2768f232ebe874175065ead3', '0', '0', '', null, '2024-03-30 09:37:02', '1', '2024-03-30 09:28:30', '1', '', '2024-03-30 09:37:02', '我很好吃');
INSERT INTO `ap_front_user` VALUES ('103', 'qhx2004', 'qhx2004', '', '15069680207', '2', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '1fb04daa7a2fa678eb87918785cf7475', '0', '0', '', null, null, '1', '2024-03-30 09:45:57', null, '', null, '');
INSERT INTO `ap_front_user` VALUES ('104', '9号测试用户', 'qhx2009', '', '15069680209', '1', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '8f9cf925ed04dcf7931b09f185106b4e', '0', '0', '', null, '2024-03-31 16:51:05', '1', '2024-03-31 16:42:31', '1', '', '2024-03-31 16:51:05', 'qhx2009');

-- ----------------------------
-- Table structure for ap_front_user_address
-- ----------------------------
DROP TABLE IF EXISTS `ap_front_user_address`;
CREATE TABLE `ap_front_user_address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '前台用户id',
  `contract_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人',
  `contract_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `contract_address` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系地址',
  `create_by` bigint DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_front_user_address
-- ----------------------------

-- ----------------------------
-- Table structure for ap_goods
-- ----------------------------
DROP TABLE IF EXISTS `ap_goods`;
CREATE TABLE `ap_goods` (
  `goods_id` bigint NOT NULL AUTO_INCREMENT COMMENT '农产品id',
  `goods_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '农产品名称',
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '产品描述',
  `tag` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '产品标签(包邮 | 以旧换新之类的)',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `shop_id` bigint DEFAULT NULL COMMENT '商品所属商家id。',
  `sold` int DEFAULT NULL COMMENT '已售产品数量',
  `balance` int DEFAULT NULL COMMENT '产品库存数量',
  `create_by` bigint DEFAULT NULL COMMENT '商品创建者(农户id)',
  `update_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `goods_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品的唯一编码',
  PRIMARY KEY (`goods_id`),
  UNIQUE KEY `goods_num` (`goods_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_goods
-- ----------------------------
INSERT INTO `ap_goods` VALUES ('2', '精品西红柿1', '精品 买一送一 非常划算', null, '20.24', '2', null, '500', '2', '1', '2024-03-18 20:26:55', '2024-03-31 19:55:23', '1', '0', '6e041ec4935948638ffdcd2927698590');
INSERT INTO `ap_goods` VALUES ('3', '精品西红柿2', '精品 买一送一 非常划算', null, '20.24', '2', null, '500', '2', '1', '2024-03-18 20:27:41', '2024-03-27 14:41:18', '0', '0', '79aba6d48a7842afa3279feae48d32c1');
INSERT INTO `ap_goods` VALUES ('4', '精品西红柿3', '精品 买一送一 非常划算', null, '20.34', '2', null, '500', '2', '1', '2024-03-19 08:12:57', '2024-03-26 22:18:13', '0', '0', '90b7641d263841a1b0273c67ac9c80e1');
INSERT INTO `ap_goods` VALUES ('5', '精品西红柿4', '精品 买一送一 非常划算', null, '20.24', '2', null, '500', '2', '1', '2024-03-19 08:17:41', '2024-03-27 12:06:27', '0', '0', 'fd88e7fe20a24bca8e73d0f7a08eb04c');
INSERT INTO `ap_goods` VALUES ('6', '精品西红柿5', '精品 买一送一 非常划算', null, '20.24', '2', null, '500', '2', '1', '2024-03-19 08:20:24', '2024-03-19 08:58:26', '0', '0', '4c2c8d3c599e48edb24e096e57b99ade');
INSERT INTO `ap_goods` VALUES ('7', '黄瓜6号', '黄瓜描述', null, '20.00', '2', null, '300', '1', '1', '2024-03-27 11:40:53', '2024-03-27 12:06:24', '0', '0', '98924dbeb6914a6197f4cf6502eb4b5d');
INSERT INTO `ap_goods` VALUES ('8', '精品黄瓜', '黄瓜 | 好吃', null, '20.10', '2', null, '40', null, '1', '2024-03-27 12:13:12', '2024-03-27 12:15:53', '0', '0', '1906cb2f60f94cf19cbe32936da1b7e1');
INSERT INTO `ap_goods` VALUES ('9', '精品soldiity', '无', null, '20.00', '2', null, '20', null, null, '2024-03-27 14:26:10', null, '0', '1', '9dccb67380fd4d9d974af6639807ee13');
INSERT INTO `ap_goods` VALUES ('13', '精品西瓜', '好吃 | 西瓜 | 好吃', null, '20.00', '101', null, '40', '101', null, '2024-03-30 20:13:39', null, '0', '0', 'd6e23efbbc034bafbe3f38423e9e51bd');
INSERT INTO `ap_goods` VALUES ('14', '黄瓜好吃', '101 | 101', null, '20.30', '101', null, '300', '1', null, '2024-03-31 12:08:54', null, '0', '0', '9ef5a6d5439f4b97af4f3e9af03bd8f7');

-- ----------------------------
-- Table structure for ap_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `ap_goods_category`;
CREATE TABLE `ap_goods_category` (
  `goods_id` bigint DEFAULT NULL COMMENT '产品id',
  `category_id` int DEFAULT NULL COMMENT '产品分类id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_goods_category
-- ----------------------------
INSERT INTO `ap_goods_category` VALUES ('3', '4');
INSERT INTO `ap_goods_category` VALUES ('2', '2');
INSERT INTO `ap_goods_category` VALUES ('4', '2');
INSERT INTO `ap_goods_category` VALUES ('6', '2');
INSERT INTO `ap_goods_category` VALUES ('7', '2');
INSERT INTO `ap_goods_category` VALUES ('8', '2');
INSERT INTO `ap_goods_category` VALUES ('9', '2');
INSERT INTO `ap_goods_category` VALUES ('13', '2');
INSERT INTO `ap_goods_category` VALUES ('14', '2');
INSERT INTO `ap_goods_category` VALUES ('4', '4');

-- ----------------------------
-- Table structure for ap_goods_collect_user
-- ----------------------------
DROP TABLE IF EXISTS `ap_goods_collect_user`;
CREATE TABLE `ap_goods_collect_user` (
  `collect_id` bigint NOT NULL COMMENT '收藏id',
  `goods_id` bigint DEFAULT NULL COMMENT '农产品id',
  `user_id` bigint DEFAULT NULL,
  `collect_time` datetime DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_goods_collect_user
-- ----------------------------

-- ----------------------------
-- Table structure for ap_goods_comment
-- ----------------------------
DROP TABLE IF EXISTS `ap_goods_comment`;
CREATE TABLE `ap_goods_comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品评论id',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `content` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论内容',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '删除标志（0代表存在 1代表删除）',
  `to_comment_user_id` bigint DEFAULT '-1' COMMENT '所回复目标评论的user_id',
  `to_comment_id` bigint DEFAULT '-1' COMMENT '所回复目标评论的id',
  `like_num` int DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_goods_comment
-- ----------------------------

-- ----------------------------
-- Table structure for ap_goods_img
-- ----------------------------
DROP TABLE IF EXISTS `ap_goods_img`;
CREATE TABLE `ap_goods_img` (
  `img_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品图片id',
  `img_url` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片网络地址',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '修改者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`img_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_goods_img
-- ----------------------------
INSERT INTO `ap_goods_img` VALUES ('3', 'https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/default.png', '2', '1', '1', '2024-03-27 09:31:42', '2024-03-27 09:31:45');
INSERT INTO `ap_goods_img` VALUES ('4', 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/goods/2024/3/27/30d325dba539497bbbc0cb126a98d468.png?Expires=4833584737&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=f7IwD2w6mKQCf7Ovpr34Q1aC9%2Fk%3D', '9', null, null, '2024-03-27 14:26:15', null);
INSERT INTO `ap_goods_img` VALUES ('5', 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/goods/2024/3/30/64e7f3fb683044b68c5b98f8713ef0d0.png?Expires=4833864818&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=xtl6tb7iihicRzV6ZZIrOJxxvV4%3D', '13', '101', null, '2024-03-30 20:13:39', null);
INSERT INTO `ap_goods_img` VALUES ('6', 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/goods/2024/3/31/57211546583f429e838c217776de4d5f.png?Expires=4833922133&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=TKGQ3AaKpDSEb5GaFXuNg%2BuNHV0%3D', '14', '1', null, '2024-03-31 12:08:54', null);

-- ----------------------------
-- Table structure for ap_goods_shopcar
-- ----------------------------
DROP TABLE IF EXISTS `ap_goods_shopcar`;
CREATE TABLE `ap_goods_shopcar` (
  `shopcar_id` bigint DEFAULT NULL COMMENT '购物车id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `amount` int DEFAULT NULL COMMENT '农产品数量',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_goods_shopcar
-- ----------------------------

-- ----------------------------
-- Table structure for ap_menu
-- ----------------------------
DROP TABLE IF EXISTS `ap_menu`;
CREATE TABLE `ap_menu` (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `menu_path` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单路径',
  `menu_icon` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单的图标',
  `menu_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单名字',
  `root_id` int DEFAULT NULL COMMENT '根menu_id',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_menu
-- ----------------------------
INSERT INTO `ap_menu` VALUES ('1', '/index', 'el-icon-s-home', '首页', '-1');
INSERT INTO `ap_menu` VALUES ('2', '/system', 'el-icon-s-tools', '系统管理', '-1');
INSERT INTO `ap_menu` VALUES ('4', '/system/farmer', 'el-icon-user', '农户管理', '2');
INSERT INTO `ap_menu` VALUES ('5', '/system/category', 'el-icon-menu', '产品分类管理', '2');
INSERT INTO `ap_menu` VALUES ('6', '/system/goods', 'el-icon-s-goods\r\nel-icon-s-goods\r\nel-icon-s-goods', '农产品管理', '2');
INSERT INTO `ap_menu` VALUES ('7', '/system/consult/info', 'el-icon-message', '咨询信息管理', '2');
INSERT INTO `ap_menu` VALUES ('8', '/system/consult/reply', 'el-icon-chat-dot-square', '咨询回复管理', '2');
INSERT INTO `ap_menu` VALUES ('9', '/system/user', 'el-icon-user-solid', '用户管理', '2');
INSERT INTO `ap_menu` VALUES ('10', '/system/order', 'el-icon-s-order', '订单管理', '2');
INSERT INTO `ap_menu` VALUES ('11', '/monitor', 'el-icon-monitor', '系统监控', '-1');
INSERT INTO `ap_menu` VALUES ('12', '/monitor/server', 'el-icon-cpu', '服务监控', '11');
INSERT INTO `ap_menu` VALUES ('13', '/monitor/druid', 'el-icon-coin', '数据监控', '11');
INSERT INTO `ap_menu` VALUES ('14', '/monitor/online', 'el-icon-s-data', '在线用户', '11');
INSERT INTO `ap_menu` VALUES ('15', '/monitor/chain', 'el-icon-data-analysis\r\nel-icon-data-analysis\r\n121', '区块链监控', '11');
INSERT INTO `ap_menu` VALUES ('16', '/monitor/cache', 'el-icon-data-board', '缓存监控', '11');

-- ----------------------------
-- Table structure for ap_message
-- ----------------------------
DROP TABLE IF EXISTS `ap_message`;
CREATE TABLE `ap_message` (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `sender_id` bigint DEFAULT NULL COMMENT '消息发送者id',
  `receiver_id` bigint DEFAULT NULL COMMENT '消息接受者(一般农户id)',
  `content` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '消息内容',
  `sender_time` datetime DEFAULT NULL COMMENT '发送这条消息的时间',
  `receiver_time` datetime(1) DEFAULT NULL COMMENT '消息接受时间',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_message
-- ----------------------------

-- ----------------------------
-- Table structure for ap_order
-- ----------------------------
DROP TABLE IF EXISTS `ap_order`;
CREATE TABLE `ap_order` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `buyer_id` bigint DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL COMMENT '商家id(后台商家id)',
  `goods_id` bigint DEFAULT NULL COMMENT '农产品id',
  `update_by` bigint DEFAULT NULL COMMENT '这个创建者id和修改者id，为了应对订单是后台用户创建时',
  `create_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '订单修改日期',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建日期',
  `del_flag` enum('0','1') COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否删除(''0'' 代表未删除,''1''代表删除)',
  `amount` int DEFAULT NULL COMMENT '订单交易数量',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '订单总价',
  `status` enum('0','1','2','3') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '订单状态(0 待支付,1 未发货 , 2 已发货 , 3 已完成)',
  `receiver_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人',
  `receiver_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货电话',
  `receiver_address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货地址',
  `express_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递名称',
  `express_num` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递单号',
  `sender_address` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sender_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发货人姓名',
  `sender_phone` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发货人电话',
  `refund` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否退款(''0'' 代表未退款,''1''代表退款)',
  `order_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单编号',
  `cancel` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否取消订单(''0'' 代表未取消订单,''1''代表取消订单)',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_num` (`order_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_order
-- ----------------------------
INSERT INTO `ap_order` VALUES ('2', '2', '2', '6', '1', '1', '2024-03-31 21:57:57', '2024-03-19 21:08:47', '0', '2', '2.00', '2', '曲先生', '15069680202', '山东省潍坊市区寒亭区高里街道西冯村', '韵达快递', '123123123', '发送人1地址', '发送人1号', '15069680201', '1', '6e041ec4935948638ffdcd2927698590', '1');

-- ----------------------------
-- Table structure for ap_role
-- ----------------------------
DROP TABLE IF EXISTS `ap_role`;
CREATE TABLE `ap_role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_role
-- ----------------------------
INSERT INTO `ap_role` VALUES ('0', '管理员');
INSERT INTO `ap_role` VALUES ('1', '农户');

-- ----------------------------
-- Table structure for ap_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `ap_role_menu`;
CREATE TABLE `ap_role_menu` (
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `menu_id` int DEFAULT NULL COMMENT '菜单id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_role_menu
-- ----------------------------
INSERT INTO `ap_role_menu` VALUES ('0', '2');
INSERT INTO `ap_role_menu` VALUES ('0', '3');
INSERT INTO `ap_role_menu` VALUES ('0', '4');
INSERT INTO `ap_role_menu` VALUES ('0', '1');
INSERT INTO `ap_role_menu` VALUES ('0', '5');
INSERT INTO `ap_role_menu` VALUES ('0', '6');
INSERT INTO `ap_role_menu` VALUES ('0', '7');
INSERT INTO `ap_role_menu` VALUES ('0', '8');
INSERT INTO `ap_role_menu` VALUES ('0', '9');
INSERT INTO `ap_role_menu` VALUES ('1', '1');
INSERT INTO `ap_role_menu` VALUES ('1', '2');
INSERT INTO `ap_role_menu` VALUES ('1', '6');
INSERT INTO `ap_role_menu` VALUES ('1', '7');
INSERT INTO `ap_role_menu` VALUES ('1', '8');
INSERT INTO `ap_role_menu` VALUES ('1', '10');
INSERT INTO `ap_role_menu` VALUES ('0', '10');
INSERT INTO `ap_role_menu` VALUES ('0', '11');
INSERT INTO `ap_role_menu` VALUES ('0', '12');
INSERT INTO `ap_role_menu` VALUES ('0', '13');
INSERT INTO `ap_role_menu` VALUES ('0', '14');
INSERT INTO `ap_role_menu` VALUES ('0', '15');
INSERT INTO `ap_role_menu` VALUES ('0', '16');

-- ----------------------------
-- Table structure for ap_shop
-- ----------------------------
DROP TABLE IF EXISTS `ap_shop`;
CREATE TABLE `ap_shop` (
  `user_id` bigint NOT NULL COMMENT '后台用户id(绑定的是后台农户)',
  `shop_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商家名称(指店铺名称,不是用户名称)',
  `collect_num` bigint DEFAULT '0' COMMENT '关注人数',
  `score_num` bigint DEFAULT '0' COMMENT '评分人数',
  `buy_num` bigint DEFAULT '0' COMMENT '总购买人数',
  `total_score` decimal(3,0) DEFAULT '0' COMMENT '总评分',
  `shop_avatar` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/2024/3/30/b6b1ad00fd91444597503aeed4906ee5.png?Expires=4833868450&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=1pYoALA6WcFLslU0nlFgcoVAUrY%3D' COMMENT '商家头像',
  `create_time` datetime DEFAULT NULL COMMENT '店铺创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '店铺修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ap_shop
-- ----------------------------
INSERT INTO `ap_shop` VALUES ('2', '测试店铺2', '0', '0', '0', '0', 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/2024/3/30/b6b1ad00fd91444597503aeed4906ee5.png?Expires=4833868450&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=1pYoALA6WcFLslU0nlFgcoVAUrY%3D', '2024-03-30 16:11:45', '2024-03-30 16:11:52');
INSERT INTO `ap_shop` VALUES ('101', '测试商家店铺2', '0', '0', '0', '0', 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/2024/3/30/b6b1ad00fd91444597503aeed4906ee5.png?Expires=4833868450&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=1pYoALA6WcFLslU0nlFgcoVAUrY%3D', null, null);
INSERT INTO `ap_shop` VALUES ('104', '海澜之家1.0', '0', '0', '0', '0', 'http://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/avatar/2024/3/31/4a9e98ed1e6540d8810678bd79ef6168.png?Expires=4833959683&OSSAccessKeyId=LTAI5tR8KtzLkvRki5b562jh&Signature=dXSOKjHw5dYDFS3xoqySTYu%2Fjf8%3D', null, null);

-- ----------------------------
-- Table structure for ap_shop_collect_user
-- ----------------------------
DROP TABLE IF EXISTS `ap_shop_collect_user`;
CREATE TABLE `ap_shop_collect_user` (
  `collect_id` int NOT NULL COMMENT '收藏ID',
  `user_id` bigint DEFAULT NULL COMMENT '前台用户id',
  `shop_id` bigint DEFAULT NULL COMMENT '关注商家id',
  `collect_time` datetime DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_shop_collect_user
-- ----------------------------

-- ----------------------------
-- Table structure for ap_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ap_user_role`;
CREATE TABLE `ap_user_role` (
  `user_id` bigint DEFAULT NULL COMMENT '后台用户id',
  `role_id` int DEFAULT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of ap_user_role
-- ----------------------------
INSERT INTO `ap_user_role` VALUES ('1', '0');
INSERT INTO `ap_user_role` VALUES ('2', '1');
INSERT INTO `ap_user_role` VALUES ('101', '1');
INSERT INTO `ap_user_role` VALUES ('102', '1');
INSERT INTO `ap_user_role` VALUES ('103', '1');
INSERT INTO `ap_user_role` VALUES ('104', '1');
