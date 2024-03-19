/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : managesystem

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 05/01/2024 08:43:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for achievement
-- ----------------------------
DROP TABLE IF EXISTS `achievement`;
CREATE TABLE `achievement`  (
  `achievement_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '成果名',
  `project_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目的外键',
  `contributer` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '贡献人的外键',
  `achieve_time` date NULL DEFAULT NULL,
  `ranking` int(0) NULL DEFAULT NULL,
  `classification` enum('发明专利','实用新型专利','外观专利','论文','软件著作权') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`achievement_name`) USING BTREE,
  INDEX `fk_achievement`(`project_id`, `contributer`) USING BTREE,
  CONSTRAINT `fk_achievement` FOREIGN KEY (`project_id`, `contributer`) REFERENCES `paticipantandproject` (`project_id`, `participant_name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of achievement
-- ----------------------------
INSERT INTO `achievement` VALUES ('成果1', 'project2', '张三', '2024-01-07', 1, '外观专利');
INSERT INTO `achievement` VALUES ('成果2', 'project3', '张三', '2023-11-21', 2, '实用新型专利');
INSERT INTO `achievement` VALUES ('成果3', 'project1', '王五', '2024-01-07', 4, '论文');
INSERT INTO `achievement` VALUES ('成果4', 'project2', '赵六', '2024-01-07', 5, '论文');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `client_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '委托单位名称',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `responsibleperson` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人姓名',
  `responsible_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人电话',
  `contact_person` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人电话',
  PRIMARY KEY (`client_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '委托方信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('大力有限公司', '中国广东省广州市', '大力王', '8619303029551', '大力哥', '12458963244');
INSERT INTO `client` VALUES ('委托方1', '北京市海淀区', '胡哥', '13484578952', '胡弟', '12487564217');
INSERT INTO `client` VALUES ('委托方2', '上海市黄浦区', '季生', '12487594214', '季嘉', '12478451247');
INSERT INTO `client` VALUES ('委托方名3', '中国广东省广州市', '邹生', '8619303029551', '邹男', '12347895621');
INSERT INTO `client` VALUES ('委托方名4', '中国广东省广州市', '小曾', '8619303029551', '小怎', '8619303029551');
INSERT INTO `client` VALUES ('飞鸿有限公司', '中国广东省广州市', '飞鸿', '8619303029551', '飞子', '13698562310');

-- ----------------------------
-- Table structure for director
-- ----------------------------
DROP TABLE IF EXISTS `director`;
CREATE TABLE `director`  (
  `director_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主任工号',
  `start_date` date NULL DEFAULT NULL COMMENT '上任时间',
  `term` int(0) NULL DEFAULT NULL COMMENT '任期',
  `researchername` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '研究人员外键',
  PRIMARY KEY (`director_no`) USING BTREE,
  UNIQUE INDEX `uc_researcherid`(`researchername`) USING BTREE,
  CONSTRAINT `outkeysearcher` FOREIGN KEY (`researchername`) REFERENCES `researcher` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '研究室主任信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of director
-- ----------------------------
INSERT INTO `director` VALUES ('director1', '2023-10-14', 2, '李四');
INSERT INTO `director` VALUES ('director2', '2024-01-17', 7, '王五');
INSERT INTO `director` VALUES ('director3', '2023-12-06', 2, '张三');
INSERT INTO `director` VALUES ('director4', '2023-12-13', 5, '赵六');
INSERT INTO `director` VALUES ('director5', '2024-01-06', 7, '王华');
INSERT INTO `director` VALUES ('director6', '2024-01-12', 8, '大顺');
INSERT INTO `director` VALUES ('director8', '2024-01-09', 25, NULL);
INSERT INTO `director` VALUES ('director9', '2023-07-03', 5, '范嘉豪');

-- ----------------------------
-- Table structure for office_space
-- ----------------------------
DROP TABLE IF EXISTS `office_space`;
CREATE TABLE `office_space`  (
  `space_id` int(0) NOT NULL AUTO_INCREMENT,
  `lab_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '研究室名称',
  `work_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '办公地址',
  `work_area` int(0) NULL DEFAULT NULL COMMENT '办公面积（平方）',
  PRIMARY KEY (`space_id`) USING BTREE,
  INDEX `office_space_ibfk_1`(`lab_name`) USING BTREE,
  CONSTRAINT `office_space_ibfk_1` FOREIGN KEY (`lab_name`) REFERENCES `research_lab` (`lab_name`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '办公场地信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of office_space
-- ----------------------------
INSERT INTO `office_space` VALUES (1, '光电实验室', '广州市', 1000);
INSERT INTO `office_space` VALUES (2, '化学实验室', '武汉市', 4520);
INSERT INTO `office_space` VALUES (3, '物理研究室', '厦门市', 100);
INSERT INTO `office_space` VALUES (4, '经济研究室', '腾冲市', 400);
INSERT INTO `office_space` VALUES (5, '计算中心', '成都市', 240);
INSERT INTO `office_space` VALUES (6, '物理研究室', '北京市', 124);

-- ----------------------------
-- Table structure for partner
-- ----------------------------
DROP TABLE IF EXISTS `partner`;
CREATE TABLE `partner`  (
  `partner_id` int(0) NOT NULL AUTO_INCREMENT,
  `project_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外键，一个项目有多个合作方',
  `partner_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '合作单位名称',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `responsiblepeople` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `responsible_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人电话',
  `contact_person` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`partner_id`) USING BTREE,
  INDEX `outkeyforprojectID`(`project_id`) USING BTREE,
  CONSTRAINT `outkeyforprojectID` FOREIGN KEY (`project_id`) REFERENCES `research_project` (`project_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '合作方信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of partner
-- ----------------------------
INSERT INTO `partner` VALUES (1, 'project1', '甲合作方', '广州市', '刘老板', '128944', '刘老板的助理', '19303029551');
INSERT INTO `partner` VALUES (2, 'project1', '乙合作方', '深圳市', '孙老板', '888866', '孙老板的助理', '13691982631');
INSERT INTO `partner` VALUES (3, 'project4', '丙合作方', '株洲市', '朱重八', '13694982641', '朱重八的弟弟', '18754234687');
INSERT INTO `partner` VALUES (4, 'project2', '丁合作方', '衡阳市', '朱光照', '15745789164', '朱光启', '14578216478');
INSERT INTO `partner` VALUES (6, 'project1', '沈合作方', '中国广东省广州市', '曾总', '19303029551', '曾经理', '19303029551');
INSERT INTO `partner` VALUES (8, 'project88', '合作方2', '中国广东省广州市', '负责人2', '19303056551', '联系人1', '18303029551');

-- ----------------------------
-- Table structure for paticipantandproject
-- ----------------------------
DROP TABLE IF EXISTS `paticipantandproject`;
CREATE TABLE `paticipantandproject`  (
  `project_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `participant_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`project_id`, `participant_name`) USING BTREE,
  INDEX `outkeyparticpant`(`participant_name`) USING BTREE,
  INDEX `projectID`(`project_id`) USING BTREE,
  CONSTRAINT `outkeyparticpant` FOREIGN KEY (`participant_name`) REFERENCES `projectpaticipant` (`paticipant_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `outkeyproject` FOREIGN KEY (`project_id`) REFERENCES `research_project` (`project_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paticipantandproject
-- ----------------------------
INSERT INTO `paticipantandproject` VALUES ('project1', '大顺');
INSERT INTO `paticipantandproject` VALUES ('project5', '大顺');
INSERT INTO `paticipantandproject` VALUES ('project88', '大顺');
INSERT INTO `paticipantandproject` VALUES ('project2', '张三');
INSERT INTO `paticipantandproject` VALUES ('project3', '张三');
INSERT INTO `paticipantandproject` VALUES ('project1', '王五');
INSERT INTO `paticipantandproject` VALUES ('project5', '王华');
INSERT INTO `paticipantandproject` VALUES ('project88', '范嘉豪');
INSERT INTO `paticipantandproject` VALUES ('project2', '赵六');

-- ----------------------------
-- Table structure for projectpaticipant
-- ----------------------------
DROP TABLE IF EXISTS `projectpaticipant`;
CREATE TABLE `projectpaticipant`  (
  `paticipant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键，也是科研人员的外键',
  `work_hour` double NULL DEFAULT NULL COMMENT '工作量',
  `join_time` date NULL DEFAULT NULL COMMENT '参加时间',
  `money` double NULL DEFAULT NULL COMMENT '可支配金额',
  PRIMARY KEY (`paticipant_name`) USING BTREE,
  CONSTRAINT `outkeyforproject` FOREIGN KEY (`paticipant_name`) REFERENCES `researcher` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of projectpaticipant
-- ----------------------------
INSERT INTO `projectpaticipant` VALUES ('大顺', 50, '2023-12-30', 120);
INSERT INTO `projectpaticipant` VALUES ('张三', 2, '2023-12-04', 1000);
INSERT INTO `projectpaticipant` VALUES ('王五', 66, '2023-11-06', 4520);
INSERT INTO `projectpaticipant` VALUES ('王华', 84.5, '2023-12-19', 200.4);
INSERT INTO `projectpaticipant` VALUES ('范嘉豪', 56, '2023-10-02', 12548);
INSERT INTO `projectpaticipant` VALUES ('赵六', 8, '2023-12-13', 8000);

-- ----------------------------
-- Table structure for quality_monitor
-- ----------------------------
DROP TABLE IF EXISTS `quality_monitor`;
CREATE TABLE `quality_monitor`  (
  `monitor_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '监测单位名称',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `responsible_person` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `responsible_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人电话',
  `contact_person` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人电话',
  PRIMARY KEY (`monitor_name`) USING BTREE,
  INDEX `monitor_name`(`monitor_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '质量监测方信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quality_monitor
-- ----------------------------
INSERT INTO `quality_monitor` VALUES ('质量有限公司', '中国广东省广州市', '刘翔', '8619303029551', '刘飞', '8619303029551');
INSERT INTO `quality_monitor` VALUES ('质量检测方A', '澳大利亚悉尼', '强尼爸爸', '88886656', '强尼', '12678945214');
INSERT INTO `quality_monitor` VALUES ('质量检测方B', '英国伦敦', '维尔胜管家', '45794217', '维尔胜', '12348756214');
INSERT INTO `quality_monitor` VALUES ('质量检测方C', '中国香港', '比尔森', '12487524', '惬尔斯', '12485743254');
INSERT INTO `quality_monitor` VALUES ('质量监测方Z', '中国广东省广州市', '大哥哥', '8619303029551', '小哥哥', '13691982631');

-- ----------------------------
-- Table structure for research_lab
-- ----------------------------
DROP TABLE IF EXISTS `research_lab`;
CREATE TABLE `research_lab`  (
  `lab_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '研究室名称',
  `lab_description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '研究方向介绍',
  `directorid` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外键',
  `secretaryname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外键',
  PRIMARY KEY (`lab_name`) USING BTREE,
  UNIQUE INDEX `lab_name`(`lab_name`) USING BTREE,
  UNIQUE INDEX `uc_directorid`(`directorid`) USING BTREE,
  INDEX `123`(`secretaryname`) USING BTREE,
  CONSTRAINT `123` FOREIGN KEY (`secretaryname`) REFERENCES `secretary` (`name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `research_lab_ibfk_2` FOREIGN KEY (`directorid`) REFERENCES `director` (`director_no`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '研究室信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of research_lab
-- ----------------------------
INSERT INTO `research_lab` VALUES ('光电实验室', '研究方向C', 'director3', '朱秘书');
INSERT INTO `research_lab` VALUES ('化学实验室', '化学材料', 'director5', '李秘书');
INSERT INTO `research_lab` VALUES ('数据库实验室', '数据库大方向', 'director6', '范小豪');
INSERT INTO `research_lab` VALUES ('新航空中心', '新航空方向', NULL, '朱秘书');
INSERT INTO `research_lab` VALUES ('物理研究室', '研究方向B', 'director2', '李秘书');
INSERT INTO `research_lab` VALUES ('经济研究室', '研究方向D', 'director4', '刘秘书');
INSERT INTO `research_lab` VALUES ('计算中心', '计算科学', 'director1', '张秘书');
INSERT INTO `research_lab` VALUES ('软件工程实验室', '软件工程概论', 'director9', '范大豪');

-- ----------------------------
-- Table structure for research_project
-- ----------------------------
DROP TABLE IF EXISTS `research_project`;
CREATE TABLE `research_project`  (
  `project_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_leader` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目负责人',
  `project_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目名称',
  `research_content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '研究内容',
  `total_funding` decimal(10, 2) NULL DEFAULT NULL COMMENT '经费总额',
  `start_date` date NULL DEFAULT NULL COMMENT '开工时间',
  `completion_date` date NULL DEFAULT NULL COMMENT '完成时间',
  `client_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '委托方编号',
  `monitor_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '质量监测方编号',
  PRIMARY KEY (`project_no`) USING BTREE,
  INDEX `research_project_ibfk_2`(`project_leader`) USING BTREE,
  INDEX `outerClient`(`client_name`) USING BTREE,
  INDEX `outermonitor`(`monitor_name`) USING BTREE,
  CONSTRAINT `outerClient` FOREIGN KEY (`client_name`) REFERENCES `client` (`client_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `outermonitor` FOREIGN KEY (`monitor_name`) REFERENCES `quality_monitor` (`monitor_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `research_project_ibfk_2` FOREIGN KEY (`project_leader`) REFERENCES `researcher` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '科研项目信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of research_project
-- ----------------------------
INSERT INTO `research_project` VALUES ('project1', '张三', '超导项目', '关于超导材料研究的内容', 2874.50, '2024-01-17', '2024-01-27', '委托方1', '质量检测方A');
INSERT INTO `research_project` VALUES ('project2', '张三', '团小梦项目', '关于全球市场经济的研究内容', 8854.20, '2023-09-11', '2023-11-19', '委托方2', '质量检测方A');
INSERT INTO `research_project` VALUES ('project3', '李四', '超高压输电项目', '关于高压输电电压电流的内容', 14578.40, '2023-10-18', '2024-02-15', '委托方1', '质量检测方C');
INSERT INTO `research_project` VALUES ('project4', '王华', '计算机重构项目', '关于计算机操作系统的重构项目', 28884.60, '2023-11-07', '2024-02-24', '委托方2', '质量检测方B');
INSERT INTO `research_project` VALUES ('project5', '王华', '数据库', '数据库开发', 12502.00, '2023-12-09', '2023-12-08', '飞鸿有限公司', '质量有限公司');
INSERT INTO `research_project` VALUES ('project6', '大顺', '数据库SQL', 'SQL研究', 1482.00, '2024-01-14', '2024-01-05', '飞鸿有限公司', '质量有限公司');
INSERT INTO `research_project` VALUES ('project88', '范嘉豪', '软工项目', '软工', 12589.00, '2023-08-04', '2024-01-21', '大力有限公司', '质量监测方Z');

-- ----------------------------
-- Table structure for researcher
-- ----------------------------
DROP TABLE IF EXISTS `researcher`;
CREATE TABLE `researcher`  (
  `researcher_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科研人员工号',
  `lab_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '研究室编号',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `gender` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `title` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职称',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `research_direction` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '研究方向',
  PRIMARY KEY (`researcher_no`, `name`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `researcher_ibfk_1`(`lab_name`) USING BTREE,
  CONSTRAINT `researcher_ibfk_1` FOREIGN KEY (`lab_name`) REFERENCES `research_lab` (`lab_name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '科研人员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of researcher
-- ----------------------------
INSERT INTO `researcher` VALUES ('01zhangsan', '光电实验室', '张三', '男', '讲师', 35, '计算机');
INSERT INTO `researcher` VALUES ('02lisi', '物理研究室', '李四', '男', '副教授', 40, '力学');
INSERT INTO `researcher` VALUES ('03wangwu', '物理研究室', '王五', '男', '副教授', 45, '相对论');
INSERT INTO `researcher` VALUES ('04zhaoliu', '经济研究室', '赵六', '男', '教授', 40, '材料力学');
INSERT INTO `researcher` VALUES ('05wanghua', '计算中心', '王华', '男', '副教授', 35, '量子力学');
INSERT INTO `researcher` VALUES ('06jingyin', '经济研究室', '菁英者', '男', '正教授', 48, '宏观经济学');
INSERT INTO `researcher` VALUES ('07dashun', '数据库实验室', '大顺', '男', '副教授', 49, '数据库');
INSERT INTO `researcher` VALUES ('08fanjiahao', '软件工程实验室', '范嘉豪', '男', '讲师', 28, '软工');

-- ----------------------------
-- Table structure for secretary
-- ----------------------------
DROP TABLE IF EXISTS `secretary`;
CREATE TABLE `secretary`  (
  `secretary_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '秘书工号',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `gender` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `hire_date` date NULL DEFAULT NULL COMMENT '聘用时间',
  `duty` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职责',
  PRIMARY KEY (`secretary_no`, `name`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `secretary_no`(`secretary_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '秘书信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of secretary
-- ----------------------------
INSERT INTO `secretary` VALUES ('secretary100', '范大豪', '女', 28, '2023-08-07', '整理内务和外务');
INSERT INTO `secretary` VALUES ('secretary111', '范小豪', '男', 22, '2024-01-07', '打招呼');
INSERT INTO `secretary` VALUES ('secretary12', '刘秘书', '男', 55, '2024-01-05', '洗碗');
INSERT INTO `secretary` VALUES ('secretary13', '李秘书', '男', 19, '2023-12-11', '事务处理');
INSERT INTO `secretary` VALUES ('secretary14', '王秘书', '男', 78, '2024-01-17', '打杂');
INSERT INTO `secretary` VALUES ('secretary19', '朱秘书', '男', 20, '2023-12-06', '管理软件');
INSERT INTO `secretary` VALUES ('secretary20', '曾秘书', '男', 48, '2024-01-09', '辅导作业');
INSERT INTO `secretary` VALUES ('secretary22', '张秘书', '男', 55, '2024-01-15', '洗碗');
INSERT INTO `secretary` VALUES ('secretary25', '耿秘书', '男', 48, '2023-12-12', '准备论文材料');
INSERT INTO `secretary` VALUES ('secretary26', '赵秘书', '女', 48, '2024-01-02', '上课');
INSERT INTO `secretary` VALUES ('secretary3', '王金则', '男', 26, '2024-01-13', '洗试管');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `age` int(0) NOT NULL,
  `chi` int(0) NOT NULL,
  `math` int(0) NOT NULL,
  `eng` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (6, '106', '陈八', 24, 87, 77, 33);
INSERT INTO `student` VALUES (7, '107', '朱九', 23, 91, 44, 56);
INSERT INTO `student` VALUES (8, '108', '小红', 8, 82, 77, 90);
INSERT INTO `student` VALUES (9, '109', '小强', 8, 78, 67, 88);
INSERT INTO `student` VALUES (10, '110', '大白', 17, 73, 72, 71);
INSERT INTO `student` VALUES (11, '111', '小花', 20, 73, 73, 88);
INSERT INTO `student` VALUES (12, '112', '小新', 7, 59, 59, 59);
INSERT INTO `student` VALUES (13, '113', '小黄', 14, 78, 99, 93);
INSERT INTO `student` VALUES (120, '105', '赵七', 17, 88, 54, 67);

-- ----------------------------
-- Table structure for sub_project
-- ----------------------------
DROP TABLE IF EXISTS `sub_project`;
CREATE TABLE `sub_project`  (
  `subproject_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目编号',
  `subproject_leader` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '子课题负责人',
  `completion_date` date NULL DEFAULT NULL COMMENT '完成时间要求',
  `available_funding` decimal(10, 2) NULL DEFAULT NULL COMMENT '可支配经费',
  `technical_indicators` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '技术指标',
  PRIMARY KEY (`subproject_id`) USING BTREE,
  INDEX `outkeyforleader`(`subproject_leader`) USING BTREE,
  INDEX `FK_subproject_participantandproject`(`project_no`, `subproject_leader`) USING BTREE,
  CONSTRAINT `FK_subproject_participantandproject` FOREIGN KEY (`project_no`, `subproject_leader`) REFERENCES `paticipantandproject` (`project_id`, `participant_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '子课题信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sub_project
-- ----------------------------
INSERT INTO `sub_project` VALUES ('1', 'project2', '张三', '2024-01-09', 15728.20, '技术指标1');
INSERT INTO `sub_project` VALUES ('2', 'project2', '张三', '2024-01-08', 42184.60, '技术指标2');
INSERT INTO `sub_project` VALUES ('3', 'project1', '王五', '2023-11-14', 57812.50, '技术指标3');
INSERT INTO `sub_project` VALUES ('5', 'project2', '张三', '2024-01-07', 15782.00, '技术指标11');
INSERT INTO `sub_project` VALUES ('6', 'project88', '范嘉豪', '2023-12-31', 1264.00, '指标4');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('fjh', '123');
INSERT INTO `user` VALUES ('admin', '123');
INSERT INTO `user` VALUES ('admin', '12345');

SET FOREIGN_KEY_CHECKS = 1;
