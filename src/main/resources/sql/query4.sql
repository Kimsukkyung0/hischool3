-- MariaDB dump 10.19  Distrib 10.11.2-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: hi_school_test
-- ------------------------------------------------------
-- Server version	10.11.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aca_result`
--

DROP TABLE IF EXISTS `aca_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aca_result` (
                              `result_id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `user_id` bigint(20) NOT NULL,
                              `subject_id` bigint(20) NOT NULL,
                              `year` char(4) NOT NULL DEFAULT '',
                              `semester` tinyint(4) NOT NULL,
                              `mid_final` tinyint(4) NOT NULL COMMENT '중간(1), 기말(2)',
                              `score` tinyint(4) NOT NULL,
                              `rating` tinyint(4) NOT NULL,
                              `class_rank` tinyint(4) NOT NULL,
                              `whole_rank` int(11) NOT NULL,
                              PRIMARY KEY (`result_id`),
                              UNIQUE KEY `user_id` (`user_id`,`subject_id`,`year`,`semester`,`mid_final`),
                              KEY `FK_user_TO_aca_result_1` (`user_id`),
                              KEY `FK_subject_TO_aca_result_1` (`subject_id`),
                              CONSTRAINT `FK_subject_TO_aca_result_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
                              CONSTRAINT `FK_user_TO_aca_result_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aca_result`
--

LOCK TABLES `aca_result` WRITE;
/*!40000 ALTER TABLE `aca_result` DISABLE KEYS */;
INSERT INTO `aca_result` VALUES
                             (2,2,117,'2023',1,1,52,8,1,52),
                             (3,2,126,'2023',1,1,40,3,19,123),
                             (4,2,10,'2023',1,1,52,6,7,70),
                             (5,2,140,'2023',1,1,95,5,18,136),
                             (6,2,10,'2023',1,2,89,4,9,11),
                             (7,2,117,'2023',1,2,60,4,18,155),
                             (8,2,140,'2023',1,2,80,1,16,80),
                             (9,2,126,'2023',1,2,31,5,11,69);
/*!40000 ALTER TABLE `aca_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mock_result`
--

DROP TABLE IF EXISTS `mock_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mock_result` (
                               `result_id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `user_id` bigint(20) NOT NULL,
                               `subject_id` bigint(20) NOT NULL,
                               `year` char(4) NOT NULL DEFAULT '0',
                               `mon` tinyint(4) NOT NULL DEFAULT 0,
                               `standard_score` tinyint(4) unsigned DEFAULT NULL,
                               `rating` tinyint(4) NOT NULL,
                               `percent` tinyint(4) DEFAULT NULL,
                               PRIMARY KEY (`result_id`),
                               UNIQUE KEY `user_id` (`user_id`,`subject_id`,`year`,`mon`),
                               KEY `FK_user_TO_mock_result_1` (`user_id`),
                               KEY `FK_subject_TO_mock_result_1` (`subject_id`),
                               CONSTRAINT `FK_subject_TO_mock_result_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
                               CONSTRAINT `FK_user_TO_mock_result_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mock_result`
--

LOCK TABLES `mock_result` WRITE;
/*!40000 ALTER TABLE `mock_result` DISABLE KEYS */;
INSERT INTO `mock_result` VALUES
                              (1,2,4,'2023',3,53,4,29),
                              (2,2,5,'2023',3,193,7,9),
                              (3,2,7,'2023',3,NULL,1,NULL),
                              (4,2,9,'2023',3,NULL,7,NULL),
                              (9,2,4,'2023',6,53,4,29),
                              (10,2,5,'2023',6,193,7,9),
                              (11,2,7,'2023',6,NULL,1,NULL),
                              (12,2,9,'2023',6,NULL,7,NULL);
/*!40000 ALTER TABLE `mock_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sbj_category`
--

DROP TABLE IF EXISTS `sbj_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sbj_category` (
                                `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `nm` varchar(10) NOT NULL,
                                `type` tinyint(4) NOT NULL COMMENT '내신 1, 모의고사 2',
                                PRIMARY KEY (`category_id`),
                                UNIQUE KEY `nm` (`nm`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sbj_category`
--

LOCK TABLES `sbj_category` WRITE;
/*!40000 ALTER TABLE `sbj_category` DISABLE KEYS */;
INSERT INTO `sbj_category` VALUES
                               (11,'과학탐구영역',1),
                               (17,'교양',1),
                               (1,'국어',1),
                               (2,'국어',2),
                               (15,'기술·가정/정보',1),
                               (9,'사회탐구영역',1),
                               (3,'수학',1),
                               (4,'수학',2),
                               (6,'영어',1),
                               (5,'영어',2),
                               (14,'예술',1),
                               (16,'제 2외국어/한문',1),
                               (18,'제 2외국어/한문',2),
                               (12,'직업탐구',2),
                               (13,'체육',1),
                               (10,'탐구영역',2),
                               (7,'한국사',1),
                               (8,'한국사',2);
/*!40000 ALTER TABLE `sbj_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school` (
                          `school_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `nm` varchar(50) NOT NULL,
                          `logo` varchar(100) NOT NULL,
                          `school_cd` char(7) NOT NULL,
                          PRIMARY KEY (`school_id`),
                          UNIQUE KEY `nm` (`nm`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES
                         (1,'함지고등학교','hamji.png','7240273'),
                         (2,'오성고등학교','오성고등학교.png','7240099'),
                         (3,'영진고등학교','youngjin.png','7240098'),
                         (4,'청구고등학교','cheonggu.png','7240103');
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
                           `subject_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `category_id` bigint(20) NOT NULL,
                           `nm` varchar(20) NOT NULL,
                           PRIMARY KEY (`subject_id`),
                           UNIQUE KEY `category_id` (`category_id`,`nm`),
                           KEY `FK_sbj_category_TO_subject_1` (`category_id`),
                           CONSTRAINT `FK_sbj_category_TO_subject_1` FOREIGN KEY (`category_id`) REFERENCES `sbj_category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES
                          (117,1,'공통국어1'),
                          (118,1,'공통국어2'),
                          (122,1,'독서 토론과 글쓰기'),
                          (2,1,'독서와 작문'),
                          (123,1,'매체 의사소통'),
                          (3,1,'문학'),
                          (120,1,'문학과 영상'),
                          (124,1,'언어생활 탐구'),
                          (119,1,'주제탐구독서'),
                          (121,1,'직무 의사소통'),
                          (1,1,'화법과 언어'),
                          (4,2,'국어'),
                          (133,3,'경제 수학'),
                          (126,3,'공통수학1'),
                          (127,3,'공통수학2'),
                          (37,3,'기본수학1'),
                          (125,3,'기본수학2'),
                          (131,3,'기하'),
                          (128,3,'대수'),
                          (129,3,'미적분Ⅰ'),
                          (132,3,'미적분Ⅱ'),
                          (136,3,'수학과 문화'),
                          (138,3,'수학과제 탐구'),
                          (137,3,'실용 통계'),
                          (134,3,'인공지능 수학'),
                          (135,3,'직무 수학'),
                          (130,3,'확률과 통계'),
                          (5,4,'수학'),
                          (7,5,'영어'),
                          (140,6,'공통영어1'),
                          (141,6,'공통영어2'),
                          (8,6,'기본영어1'),
                          (139,6,'기본영어2'),
                          (151,6,'미디어 영어'),
                          (152,6,'세계문화와 영어'),
                          (150,6,'실생활 영어 회화'),
                          (148,6,'심화 영어 독해와 작문'),
                          (147,6,'심화영어'),
                          (145,6,'영미문학읽기'),
                          (142,6,'영어 독해와 작문'),
                          (146,6,'영어 발표와 토론'),
                          (143,6,'영어Ⅰ'),
                          (144,6,'영어Ⅱ'),
                          (149,6,'직무 영어'),
                          (10,7,'한국사1'),
                          (153,7,'한국사2'),
                          (9,8,'한국사'),
                          (23,9,'경제'),
                          (159,9,'국제 관계의 이해'),
                          (163,9,'금융과 경제생활'),
                          (165,9,'기후변화와 지속 가능한 세계'),
                          (157,9,'도시의 미래 탐구'),
                          (19,9,'동아시아 역사기행'),
                          (158,9,'법과 사회'),
                          (162,9,'사회문제 탐구'),
                          (27,9,'사회와 문화'),
                          (21,9,'세계사'),
                          (17,9,'세계시민과 지리'),
                          (160,9,'여행지리'),
                          (161,9,'역사로 탐구하는 현대 세계'),
                          (164,9,'윤리문제 탐구'),
                          (13,9,'윤리와 사상'),
                          (11,9,'인문학과 윤리'),
                          (25,9,'정치'),
                          (154,9,'통합사회1'),
                          (155,9,'통합사회2'),
                          (15,9,'한국지리 탐구'),
                          (156,9,'현대사회와 윤리'),
                          (24,10,'경제'),
                          (20,10,'동아시아사'),
                          (30,10,'물리학1'),
                          (28,10,'사회문화'),
                          (34,10,'생명과학1'),
                          (12,10,'생활과 윤리'),
                          (18,10,'세계 지리'),
                          (22,10,'세계사'),
                          (14,10,'윤리와 사상'),
                          (26,10,'정치와법'),
                          (36,10,'지구과학1'),
                          (16,10,'한국 지리'),
                          (32,10,'화학1'),
                          (178,11,'과학의 역사와 문화'),
                          (168,11,'과학탐구실험1'),
                          (169,11,'과학탐구실험2'),
                          (179,11,'기후변화와 환경생태'),
                          (29,11,'물리학'),
                          (171,11,'물질과 에너지'),
                          (33,11,'생명과학'),
                          (176,11,'생물의 유전'),
                          (172,11,'세포와 물질대사'),
                          (170,11,'역학과 에너지'),
                          (180,11,'융합과학 탐구'),
                          (174,11,'전자기와 양자'),
                          (35,11,'지구과학'),
                          (173,11,'지구시스템과학'),
                          (166,11,'통합과학1'),
                          (167,11,'통합과학2'),
                          (177,11,'행성우주과학'),
                          (31,11,'화학'),
                          (175,11,'화학 반응의 세계'),
                          (50,12,'공업 일반'),
                          (49,12,'농업 기초 기술'),
                          (51,12,'상업 경제'),
                          (48,12,'성공적인 직업생활'),
                          (52,12,'수산·해운 산업기초'),
                          (53,12,'인간발달'),
                          (58,13,'스포츠 과학'),
                          (57,13,'스포츠 문화'),
                          (59,13,'스포츠 생활1'),
                          (60,13,'스포츠 생활2'),
                          (56,13,'운동과 건강'),
                          (54,13,'체육1'),
                          (55,13,'체육2'),
                          (62,14,'미술'),
                          (67,14,'미술 감상과 비평'),
                          (66,14,'미술 창작'),
                          (69,14,'미술과 매체'),
                          (63,14,'연극'),
                          (61,14,'음악'),
                          (65,14,'음악 감상과 비평'),
                          (64,14,'음악 연주와 창작'),
                          (68,14,'음악과 미디어'),
                          (70,15,'기술⋅가정'),
                          (79,15,'데이터과학'),
                          (71,15,'로봇과 공학세계'),
                          (75,15,'생애 설계와 자립'),
                          (72,15,'생활과학 탐구'),
                          (80,15,'소프트웨어와 생활'),
                          (76,15,'아동 발달과 부모'),
                          (78,15,'인공지능 기초'),
                          (77,15,'정보'),
                          (74,15,'지식재산일반'),
                          (73,15,'창의공학설계'),
                          (81,16,'독일어'),
                          (89,16,'독일어 회화'),
                          (39,16,'독일어Ⅰ'),
                          (86,16,'러시아어'),
                          (94,16,'러시아어 회화'),
                          (44,16,'러시아어Ⅰ'),
                          (88,16,'베트남어'),
                          (96,16,'베트남어 회화'),
                          (46,16,'베트남어Ⅰ'),
                          (83,16,'스페인어'),
                          (91,16,'스페인어 회화'),
                          (41,16,'스페인어Ⅰ'),
                          (97,16,'심화 독일어'),
                          (102,16,'심화 러시아어'),
                          (104,16,'심화 베트남어'),
                          (99,16,'심화 스페인어'),
                          (103,16,'심화 아랍어'),
                          (101,16,'심화 일본어'),
                          (100,16,'심화 중국어'),
                          (98,16,'심화 프랑스어'),
                          (87,16,'아랍어'),
                          (95,16,'아랍어 회화'),
                          (45,16,'아랍어Ⅰ'),
                          (85,16,'일본어'),
                          (93,16,'일본어 회화'),
                          (43,16,'일본어Ⅰ'),
                          (84,16,'중국어'),
                          (92,16,'중국어 회화'),
                          (42,16,'중국어Ⅰ'),
                          (82,16,'프랑스어'),
                          (90,16,'프랑스어 회화'),
                          (40,16,'프랑스어Ⅰ'),
                          (105,16,'한문'),
                          (47,16,'한문Ⅰ'),
                          (106,16,'한문고전읽기'),
                          (112,17,'교육의 이해'),
                          (110,17,'논리와 사고'),
                          (116,17,'논술'),
                          (114,17,'보건'),
                          (113,17,'삶과 종교'),
                          (108,17,'생태와 환경'),
                          (115,17,'인간과 경제활동'),
                          (111,17,'인간과 심리'),
                          (109,17,'인간과 철학'),
                          (107,17,'진로와 직업'),
                          (181,18,'독일어Ⅰ'),
                          (186,18,'러시아어Ⅰ'),
                          (188,18,'베트남어Ⅰ'),
                          (183,18,'스페인어Ⅰ'),
                          (187,18,'아랍어Ⅰ'),
                          (185,18,'일본어Ⅰ'),
                          (184,18,'중국어Ⅰ'),
                          (182,18,'프랑스어Ⅰ'),
                          (189,18,'한문Ⅰ');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tc_sbj`
--

DROP TABLE IF EXISTS `tc_sbj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tc_sbj` (
                          `user_id` bigint(20) NOT NULL,
                          `subject_id` bigint(20) NOT NULL,
                          UNIQUE KEY `user_id` (`user_id`,`subject_id`),
                          KEY `FK_user_TO_tc_sbj_1` (`user_id`),
                          KEY `FK_subject_TO_tc_sbj_1` (`subject_id`),
                          CONSTRAINT `FK_subject_TO_tc_sbj_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
                          CONSTRAINT `FK_user_TO_tc_sbj_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tc_sbj`
--

LOCK TABLES `tc_sbj` WRITE;
/*!40000 ALTER TABLE `tc_sbj` DISABLE KEYS */;
INSERT INTO `tc_sbj` VALUES
                         (1,4),
                         (1,5),
                         (1,7),
                         (1,9),
                         (1,10),
                         (1,117),
                         (1,126),
                         (1,140);
/*!40000 ALTER TABLE `tc_sbj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
                        `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `class_id` bigint(20) NOT NULL,
                        `email` varchar(50) NOT NULL,
                        `pw` varchar(100) NOT NULL,
                        `nm` varchar(20) NOT NULL,
                        `pic` varchar(100) DEFAULT NULL,
                        `birth` date NOT NULL,
                        `phone` char(13) NOT NULL,
                        `address` varchar(200) NOT NULL,
                        `detail_addr` varchar(200) NOT NULL,
                        `role` varchar(10) NOT NULL COMMENT '관리자(ROLE_ADMIN), 선생님(ROLE_TC), 학생(ROLE_STD)',
                        `apr_pic` varchar(100) DEFAULT NULL,
                        `apr_yn` tinyint(4) NOT NULL DEFAULT 0 COMMENT '승인대기(0),승인완료(1)',
                        `del_yn` tinyint(4) NOT NULL DEFAULT 0 COMMENT '삭제처리(1)',
                        `join_date` datetime NOT NULL DEFAULT current_timestamp(),
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `email` (`email`),
                        KEY `FK_class_TO_user_1` (`class_id`),
                        CONSTRAINT `FK_class_TO_user_1` FOREIGN KEY (`class_id`) REFERENCES `van` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
                       (1,1,'tc@gmail.com','{bcrypt}$2a$10$rbkBJbTyMuiRBDKn85jfZ.xGSGOl.8WpYbtbtPamNQHRthvsjjeZ6','김선생','4ea6ede8-3f10-4429-9180-7a046b793ba7.png','2023-08-08','010-0000-0000','대구광역시 중구 중앙대로 394','제일빌딩 5F','ROLE_TC','baabfa7b-42ea-4936-94bc-4a8ca86988b8.png',1,0,'2023-08-08 14:40:54'),
                       (2,1,'std@gmail.com','{bcrypt}$2a$10$XN7XV8QarAnFEzbUKpLYj./ev9jACcamc0WzfBBva3SpZC5.DtVxG','김학생','bff7ac5d-af7b-4042-b608-1dff52e33800.png','2023-08-10','010-0000-0000','대구광역시 중구 중앙대로 394','제일빌딩 5F','ROLE_STD','ff4a7aa4-6b8c-4554-b39e-c6f8eb2b4840.png',1,0,'2023-08-10 11:10:25'),
                       (3,1,'lbarbery0@soundcloud.com','$2a$04$9HWVke9DZZ2gE9vjrG6jCeJXW5Vd2Pe0Ehp9xZdhcgbr3v/YIcD8K','김학생',NULL,'2004-12-29','010-0000-0000','대구광역시 남구','제일빌딩 5F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (4,1,'mtoun1@gnu.org','$2a$04$B9uZSKuuz28flCS8anxq1.VezfBrQND07W/y6m9lzVu.EqMwq6Zem','여학생',NULL,'2005-11-14','010-0000-0000','대구광역시 남구','제일빌딩 2F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (5,1,'mvanni2@yellowbook.com','$2a$04$ahVl9/v9PyliFj20LlCT5uyVcwlZTwFfv4N1omXZ9LacQ/GEYwdbK','박학생',NULL,'2004-12-10','010-0000-0000','대구광역시 수성구','제일빌딩 3F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (6,1,'mbradforth3@shareasale.com','$2a$04$piVywQCucD/6uxCxrAgbGO6NqZP0DmQf3/PkLe9FHiH.MvBwdClW2','최학생',NULL,'2005-08-09','010-0000-0000','대구광역시 남구','제일빌딩 4F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (7,1,'rmacgorman4@whitehouse.gov','$2a$04$CZt2zmmZVwlHPcXFcQ5..ej0fuAZs/koFcTze4vgNESS5PzwXznJi','이학생',NULL,'2005-12-26','010-0000-0000','대구광역시 달서구','제일빌딩 3F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (8,1,'nenrietto5@home.pl','$2a$04$.iRZejPeHWa8aC7wMIHu9OsE3IdOP7bGchIUr5rZNGIwMx/ytb7p.','이학생',NULL,'2005-01-03','010-0000-0000','대구광역시 서구','제일빌딩 1F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (9,1,'spannett6@amazon.de','$2a$04$KUKupthqiRA5XffL/kjdY.bw7D7FOf8aY3knbzSJdR9y0zwC1UJ2.','남학생',NULL,'2005-05-26','010-0000-0000','대구광역시 수성구','제일빌딩 2F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (10,1,'aladewig7@google.com.hk','$2a$04$OoVcleTb.NsCHngIl6mkF.kDcXzjdOEuddz0/IF5.v1o9HgXoRSOu','박학생',NULL,'2004-09-09','010-0000-0000','대구광역시 서구','제일빌딩 3F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (11,1,'akunze8@alibaba.com','$2a$04$zGQB.C3HqKkOA/sD9YzcMe/3TtiNpkdDaM8YwQke87D1hqsPv2GwG','최학생',NULL,'2004-05-20','010-0000-0000','대구광역시 북구','제일빌딩 1F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (12,1,'cditch9@theglobeandmail.com','$2a$04$1o0oUisDabZJr9yxz0KmJ.fCqu8sDpFsXz.XzieeR1siHxkFmpBAS','김학생',NULL,'2004-02-20','010-0000-0000','대구광역시 수성구','제일빌딩 2F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (13,1,'nfonzonea@usa.gov','$2a$04$wNFYEhIRCWESUHrgzLTX8eSPS4X7J7y1zJFyH9E7c6Lmq3hJjS69q','김학생',NULL,'2005-03-23','010-0000-0000','대구광역시 서구','제일빌딩 5F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (14,1,'xbrooktonb@ustream.tv','$2a$04$i73ZN46WW8g7nACo7u97ee.bR.M43CuZJ82n5wePYIZK5hy7zo4zW','최학생',NULL,'2005-10-09','010-0000-0000','대구광역시 중구','제일빌딩 4F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (15,1,'iranniec@tmall.com','$2a$04$uWkHv1VnYab3v.zNoPoXoeto8I68UkD7cstd4265B9hz1yLdPiTeq','남학생',NULL,'2005-12-07','010-0000-0000','대구광역시 남구','제일빌딩 3F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (16,1,'fenneverd@slate.com','$2a$04$t6O8aULXgFCyFUI0S5KzKOaP1PJBukzWkQOCFfaqd5ZioLsmZI8Va','이학생',NULL,'2004-10-09','010-0000-0000','대구광역시 북구','제일빌딩 2F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (17,1,'chegele@barnesandnoble.com','$2a$04$kwvJTcNhGM8zFngm/FABF.NJUzOcK4amx5nk7frGuiS45M8739ATm','남학생',NULL,'2005-08-12','010-0000-0000','대구광역시 남구','제일빌딩 4F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (18,1,'jbeeswingf@microsoft.com','$2a$04$4aTEz7N4UCQR2U3VYvxI4OdvGkRqbmkxkOzWPhtPkZ9TyvTGB6oSq','여학생',NULL,'2004-05-29','010-0000-0000','대구광역시 중구','제일빌딩 5F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (19,1,'bmytong@skyrock.com','$2a$04$YJTaVp1TTaZzFPxRiyPYXem8duE3lSF00I0nLw6WnJX8gunqDM6R.','박학생',NULL,'2004-02-12','010-0000-0000','대구광역시 달서구','제일빌딩 3F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (20,1,'dhowsh@vinaora.com','$2a$04$rE1JSKXk8vJhVhxPvz19b.IejzO.3z9ngWsBvfAT8QWdpq2qITboC','박학생',NULL,'2004-12-27','010-0000-0000','대구광역시 남구','제일빌딩 3F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (21,1,'ktrencheri@narod.ru','$2a$04$05SL4UR85ryRSlE6VQolM.Mgv7Fn5w85qkhUNhCo/EQbi/mBRwUz6','여학생',NULL,'2004-07-19','010-0000-0000','대구광역시 달서구','제일빌딩 1F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27'),
                       (22,1,'tsircombej@youtu.be','$2a$04$8hnMsJ3Pj/0ipqyEIV2KaeYQBnyG9Ba.phwMUOFQ86pV/dGWc8Umm','여학생',NULL,'2005-01-29','010-0000-0000','대구광역시 중구','제일빌딩 1F','ROLE_STD',NULL,1,0,'2023-08-10 11:35:27');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `van`
--

DROP TABLE IF EXISTS `van`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `van` (
                       `class_id` bigint(20) NOT NULL AUTO_INCREMENT,
                       `school_id` bigint(20) NOT NULL,
                       `year` char(4) NOT NULL DEFAULT '',
                       `grade` char(1) NOT NULL DEFAULT '',
                       `class` char(2) NOT NULL DEFAULT '',
                       PRIMARY KEY (`class_id`),
                       UNIQUE KEY `school_id` (`school_id`,`year`,`grade`,`class`),
                       KEY `FK_school_TO_class_1` (`school_id`),
                       CONSTRAINT `FK_school_TO_class_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `van`
--

LOCK TABLES `van` WRITE;
/*!40000 ALTER TABLE `van` DISABLE KEYS */;
INSERT INTO `van` VALUES
                      (1,1,'2023','1','1'),
                      (2,1,'2023','1','2'),
                      (3,1,'2023','1','3'),
                      (4,1,'2023','1','4'),
                      (5,1,'2023','1','5'),
                      (6,1,'2023','1','6'),
                      (7,1,'2023','1','7'),
                      (8,1,'2023','2','1'),
                      (9,1,'2023','2','2'),
                      (10,1,'2023','2','3'),
                      (11,1,'2023','2','4'),
                      (12,1,'2023','2','5'),
                      (13,1,'2023','2','6'),
                      (14,1,'2023','2','7'),
                      (15,1,'2023','2','8'),
                      (16,1,'2023','3','1'),
                      (17,1,'2023','3','2'),
                      (18,1,'2023','3','3'),
                      (19,1,'2023','3','4'),
                      (20,1,'2023','3','5'),
                      (21,1,'2023','3','6'),
                      (22,1,'2023','3','7'),
                      (23,1,'2023','3','8');
/*!40000 ALTER TABLE `van` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-10 12:55:27