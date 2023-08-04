-- MariaDB dump 10.19  Distrib 10.11.2-MariaDB, for Win64 (AMD64)
--
-- Host: 192.168.0.144    Database: team_c
-- ------------------------------------------------------
-- Server version	10.11.3-MariaDB

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
  KEY `FK_user_TO_aca_result_1` (`user_id`),
  KEY `FK_subject_TO_aca_result_1` (`subject_id`),
  CONSTRAINT `FK_subject_TO_aca_result_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
  CONSTRAINT `FK_user_TO_aca_result_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aca_result`
--

LOCK TABLES `aca_result` WRITE;
/*!40000 ALTER TABLE `aca_result` DISABLE KEYS */;
INSERT INTO `aca_result` VALUES
(1,1,1,'2023',1,1,100,1,1,1),
(2,1,1,'2023',1,1,100,1,1,1),
(3,1,1,'2023',1,1,100,1,1,1),
(4,1,1,'2023',1,2,98,1,2,3),
(5,1,3,'2023',1,2,95,1,3,5),
(6,1,3,'2023',2,1,100,1,1,1),
(7,1,37,'2023',1,1,40,5,57,120),
(8,1,37,'2023',2,1,60,4,30,98),
(9,1,37,'2023',1,2,67,4,20,83),
(11,1,37,'2023',2,2,99,1,2,3),
(12,1,3,'2023',1,1,99,1,2,3),
(13,1,3,'2023',2,2,99,1,2,1),
(19,40,117,'2023',1,1,80,2,12,42),
(20,40,117,'2023',1,2,82,3,15,51),
(21,40,118,'2023',2,1,82,1,8,24),
(22,40,118,'2023',1,2,82,4,20,65),
(23,40,37,'2023',1,1,70,4,16,50),
(24,40,125,'2023',1,2,82,5,20,70),
(25,40,126,'2023',2,1,82,4,16,60),
(26,40,127,'2023',2,2,82,2,5,24),
(27,40,10,'2023',1,1,73,6,24,87),
(28,40,153,'2023',1,2,52,6,23,70),
(29,40,154,'2023',2,1,62,6,20,95),
(30,40,155,'2023',2,2,32,7,25,101),
(31,40,8,'2023',1,1,89,1,2,11),
(32,40,139,'2023',1,2,94,1,3,12),
(33,40,140,'2023',2,1,92,2,2,8),
(34,40,141,'2023',2,2,98,1,2,16),
(35,40,166,'2023',1,1,74,6,20,50),
(36,40,167,'2023',1,2,72,6,22,31),
(37,40,168,'2023',2,1,81,3,6,24),
(38,40,169,'2023',2,2,75,4,10,27);
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
  KEY `FK_user_TO_mock_result_1` (`user_id`),
  KEY `FK_subject_TO_mock_result_1` (`subject_id`),
  CONSTRAINT `FK_subject_TO_mock_result_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
  CONSTRAINT `FK_user_TO_mock_result_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mock_result`
--

LOCK TABLES `mock_result` WRITE;
/*!40000 ALTER TABLE `mock_result` DISABLE KEYS */;
INSERT INTO `mock_result` VALUES
(3,1,4,'2023',6,110,1,3),
(4,1,7,'2023',12,0,3,30),
(5,1,6,'2023',9,0,3,30),
(6,1,5,'2023',9,90,3,30),
(7,1,35,'2023',3,6,9,1),
(8,1,9,'2023',3,6,9,1),
(9,1,32,'2023',3,6,9,1),
(10,1,34,'2023',3,6,9,1),
(11,1,36,'2023',9,1,1,1),
(12,1,22,'2023',9,1,1,1),
(13,2,4,'2023',3,134,1,97),
(14,2,6,'2023',3,129,2,90),
(15,2,7,'2023',3,NULL,1,NULL),
(16,2,26,'2023',3,75,1,99),
(17,2,24,'2023',3,61,3,86),
(18,2,9,'2023',3,NULL,2,NULL),
(19,2,7,'2023',6,NULL,2,NULL),
(20,2,4,'2023',6,113,4,71),
(21,2,6,'2023',6,91,5,45),
(22,2,16,'2023',6,62,3,87),
(23,2,18,'2023',6,60,3,84),
(24,2,9,'2023',3,NULL,3,NULL),
(25,2,4,'2023',9,143,2,81),
(26,2,6,'2023',9,132,2,60),
(27,2,7,'2023',9,NULL,3,NULL),
(28,2,16,'2023',9,66,3,83),
(29,2,18,'2023',9,72,3,87),
(30,2,9,'2023',9,NULL,3,NULL),
(31,2,48,'2023',9,80,1,94),
(32,2,49,'2023',9,50,4,62),
(33,2,181,'2023',9,72,3,70),
(34,3,4,'2023',3,91,4,71),
(35,3,6,'2023',3,71,6,42),
(36,3,7,'2023',3,NULL,3,NULL),
(37,3,16,'2023',3,40,5,83),
(38,3,14,'2023',3,63,5,87),
(39,3,9,'2023',3,NULL,5,NULL),
(40,3,48,'2023',3,70,3,51),
(41,3,49,'2023',3,82,4,61),
(42,3,181,'2023',3,20,8,10),
(43,3,4,'2023',6,101,3,78),
(44,3,6,'2023',6,70,6,45),
(45,3,7,'2023',6,NULL,3,NULL),
(46,3,16,'2023',6,40,7,40),
(47,3,14,'2023',6,63,5,80),
(48,3,9,'2023',6,NULL,5,NULL),
(49,3,48,'2023',6,70,2,75),
(50,3,49,'2023',6,82,8,18),
(51,3,181,'2023',6,20,8,10),
(52,3,4,'2023',9,124,2,88),
(53,3,6,'2023',9,73,4,70),
(54,3,7,'2023',9,NULL,2,NULL),
(55,3,16,'2023',9,32,7,32),
(56,3,14,'2023',9,52,6,80),
(57,3,9,'2023',9,NULL,3,NULL),
(58,3,48,'2023',9,72,6,81),
(59,3,49,'2023',9,80,8,20),
(60,3,181,'2023',9,20,9,10),
(61,40,9,'2023',3,120,1,1),
(62,40,4,'2023',3,110,2,9),
(63,40,6,'2023',3,97,4,39),
(64,40,34,'2023',3,107,3,21),
(65,40,36,'2023',3,108,3,21),
(66,40,36,'2023',6,88,5,55),
(67,2,9,'2023',6,NULL,2,NULL),
(75,1,1,'2023',9,10,1,1),
(76,40,2,'2023',3,120,1,1);
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
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sbj_category`
--

LOCK TABLES `sbj_category` WRITE;
/*!40000 ALTER TABLE `sbj_category` DISABLE KEYS */;
INSERT INTO `sbj_category` VALUES
(1,'국어',1),
(2,'국어',2),
(3,'수학',1),
(4,'수학',2),
(5,'영어',2),
(6,'영어',1),
(7,'한국사',1),
(8,'한국사',2),
(9,'사회탐구영역',1),
(10,'탐구영역',2),
(11,'과학탐구영역',1),
(12,'직업탐구',2),
(13,'체육',1),
(14,'예술',1),
(15,'기술·가정/정보',1),
(16,'제 2외국어/한문',1),
(17,'교양',1),
(18,'제 2외국어/한문',2);
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
(2,'오성고등학교','osung.png','7240099'),
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
(1,1,'화법과 언어'),
(2,1,'독서와 작문'),
(3,1,'문학'),
(4,2,'국어'),
(5,4,'수학(가형)'),
(6,4,'수학(나형)'),
(7,5,'영어'),
(8,6,'기본영어1'),
(9,8,'한국사'),
(10,7,'한국사1'),
(11,9,'인문학과 윤리'),
(12,10,'생활과 윤리'),
(13,9,'윤리와 사상'),
(14,10,'윤리와 사상'),
(15,9,'한국지리 탐구'),
(16,10,'한국 지리'),
(17,9,'세계시민과 지리'),
(18,10,'세계 지리'),
(19,9,'동아시아 역사기행'),
(20,10,'동아시아사'),
(21,9,'세계사'),
(22,10,'세계사'),
(23,9,'경제'),
(24,10,'경제'),
(25,9,'정치'),
(26,10,'정치와법'),
(27,9,'사회와 문화'),
(28,10,'사회문화'),
(29,11,'물리학'),
(30,10,'물리학1'),
(31,11,'화학'),
(32,10,'화학1'),
(33,11,'생명과학'),
(34,10,'생명과학1'),
(35,11,'지구과학'),
(36,10,'지구과학1'),
(37,3,'기본수학1'),
(39,16,'독일어Ⅰ'),
(40,16,'프랑스어Ⅰ'),
(41,16,'스페인어Ⅰ'),
(42,16,'중국어Ⅰ'),
(43,16,'일본어Ⅰ'),
(44,16,'러시아어Ⅰ'),
(45,16,'아랍어Ⅰ'),
(46,16,'베트남어Ⅰ'),
(47,16,'한문Ⅰ'),
(48,12,'성공적인 직업생활'),
(49,12,'농업 기초 기술'),
(50,12,'공업 일반'),
(51,12,'상업 경제'),
(52,12,'수산·해운 산업기초'),
(53,12,'인간발달'),
(54,13,'체육1'),
(55,13,'체육2'),
(56,13,'운동과 건강'),
(57,13,'스포츠 문화'),
(58,13,'스포츠 과학'),
(59,13,'스포츠 생활1'),
(60,13,'스포츠 생활2'),
(61,14,'음악'),
(62,14,'미술'),
(63,14,'연극'),
(64,14,'음악 연주와 창작'),
(65,14,'음악 감상과 비평'),
(66,14,'미술 창작'),
(67,14,'미술 감상과 비평'),
(68,14,'음악과 미디어'),
(69,14,'미술과 매체'),
(70,15,'기술⋅가정'),
(71,15,'로봇과 공학세계'),
(72,15,'생활과학 탐구'),
(73,15,'창의공학설계'),
(74,15,'지식재산일반'),
(75,15,'생애 설계와 자립'),
(76,15,'아동 발달과 부모'),
(77,15,'정보'),
(78,15,'인공지능 기초'),
(79,15,'데이터과학'),
(80,15,'소프트웨어와 생활'),
(81,16,'독일어'),
(82,16,'프랑스어'),
(83,16,'스페인어'),
(84,16,'중국어'),
(85,16,'일본어'),
(86,16,'러시아어'),
(87,16,'아랍어'),
(88,16,'베트남어'),
(89,16,'독일어 회화'),
(90,16,'프랑스어 회화'),
(91,16,'스페인어 회화'),
(92,16,'중국어 회화'),
(93,16,'일본어 회화'),
(94,16,'러시아어 회화'),
(95,16,'아랍어 회화'),
(96,16,'베트남어 회화'),
(97,16,'심화 독일어'),
(98,16,'심화 프랑스어'),
(99,16,'심화 스페인어'),
(100,16,'심화 중국어'),
(101,16,'심화 일본어'),
(102,16,'심화 러시아어'),
(103,16,'심화 아랍어'),
(104,16,'심화 베트남어'),
(105,16,'한문'),
(106,16,'한문고전읽기'),
(107,17,'진로와 직업'),
(108,17,'생태와 환경'),
(109,17,'인간과 철학'),
(110,17,'논리와 사고'),
(111,17,'인간과 심리'),
(112,17,'교육의 이해'),
(113,17,'삶과 종교'),
(114,17,'보건'),
(115,17,'인간과 경제활동'),
(116,17,'논술'),
(117,1,'공통국어1'),
(118,1,'공통국어2'),
(119,1,'주제탐구독서'),
(120,1,'문학과 영상'),
(121,1,'직무 의사소통'),
(122,1,'독서 토론과 글쓰기'),
(123,1,'매체 의사소통'),
(124,1,'언어생활 탐구'),
(125,3,'기본수학2'),
(126,3,'공통수학1'),
(127,3,'공통수학2'),
(128,3,'대수'),
(129,3,'미적분Ⅰ'),
(130,3,'확률과 통계'),
(131,3,'기하'),
(132,3,'미적분Ⅱ'),
(133,3,'경제 수학'),
(134,3,'인공지능 수학'),
(135,3,'직무 수학'),
(136,3,'수학과 문화'),
(137,3,'실용 통계'),
(138,3,'수학과제 탐구'),
(139,6,'기본영어2'),
(140,6,'공통영어1'),
(141,6,'공통영어2'),
(142,6,'영어 독해와 작문'),
(143,6,'영어Ⅰ'),
(144,6,'영어Ⅱ'),
(145,6,'영미문학읽기'),
(146,6,'영어 발표와 토론'),
(147,6,'심화영어'),
(148,6,'심화 영어 독해와 작문'),
(149,6,'직무 영어'),
(150,6,'실생활 영어 회화'),
(151,6,'미디어 영어'),
(152,6,'세계문화와 영어'),
(153,7,'한국사2'),
(154,9,'통합사회1'),
(155,9,'통합사회2'),
(156,9,'현대사회와 윤리'),
(157,9,'도시의 미래 탐구'),
(158,9,'법과 사회'),
(159,9,'국제 관계의 이해'),
(160,9,'여행지리'),
(161,9,'역사로 탐구하는 현대 세계'),
(162,9,'사회문제 탐구'),
(163,9,'금융과 경제생활'),
(164,9,'윤리문제 탐구'),
(165,9,'기후변화와 지속 가능한 세계'),
(166,11,'통합과학1'),
(167,11,'통합과학2'),
(168,11,'과학탐구실험1'),
(169,11,'과학탐구실험2'),
(170,11,'역학과 에너지'),
(171,11,'물질과 에너지'),
(172,11,'세포와 물질대사'),
(173,11,'지구시스템과학'),
(174,11,'전자기와 양자'),
(175,11,'화학 반응의 세계'),
(176,11,'생물의 유전'),
(177,11,'행성우주과학'),
(178,11,'과학의 역사와 문화'),
(179,11,'기후변화와 환경생태'),
(180,11,'융합과학 탐구'),
(181,18,'독일어Ⅰ'),
(182,18,'프랑스어Ⅰ'),
(183,18,'스페인어Ⅰ'),
(184,18,'중국어Ⅰ'),
(185,18,'일본어Ⅰ'),
(186,18,'러시아어Ⅰ'),
(187,18,'아랍어Ⅰ'),
(188,18,'베트남어Ⅰ'),
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
(1,1),
(1,2),
(1,2),
(1,1),
(1,4),
(1,4),
(1,5),
(2,1),
(2,121),
(6,121),
(41,37),
(41,2),
(41,1);
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
  `role` varchar(10) NOT NULL COMMENT '관리자(ROLE_ADMIN), 선생님(ROLE_TC), 학생(ROLE_STD)',
  `apr_pic` varchar(100) DEFAULT NULL,
  `apr_yn` tinyint(4) NOT NULL DEFAULT 0 COMMENT '승인대기(0),승인완료(1)',
  `del_yn` tinyint(4) NOT NULL DEFAULT 0 COMMENT '삭제처리(1)',
  `join_date` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_class_TO_user_1` (`class_id`),
  CONSTRAINT `FK_class_TO_user_1` FOREIGN KEY (`class_id`) REFERENCES `van` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(1,1,'tncjs3598@naver.com','tncjs123','성수천','user/1/210ac267-698b-4097-97f8-4962a951da59.jpg','1997-09-01','01028087210','대구','ROLE_ADMIN',NULL,1,0,'2023-07-27 14:23:20'),
(2,2,'naver@naver.com','q1w2e3r4','나강림','user/2/3d024f29-96ab-4251-ba14-f03f512085c6.jpg','1997-12-11','010-0000-0000','어드레스 ㅋㅋ','ROLE_STD',NULL,1,0,'2023-07-27 16:58:12'),
(3,1,'mail1234@naver.com','4321','이아메',NULL,'2001-03-21','01055547754','대구','ROLE_STD',NULL,1,0,'2023-07-27 16:59:30'),
(4,1,'ghghgh@naver.com','ghgh12','호날두',NULL,'1993-11-24','01077887788','리스본','ROLE_STD',NULL,0,0,'2023-07-27 17:00:10'),
(5,1,'haha@naver.com','haha','김바람',NULL,'1999-07-04','01023567812','대구','ROLE_STD',NULL,0,0,'2023-07-27 17:01:19'),
(6,3,'teacher11@naver.com','0001','김담임','123123','1981-01-15','010-0101-0101','옆집','ROLE_TC',NULL,1,0,'2023-07-27 17:02:21'),
(7,2,'std132@naver.com','654','정우등',NULL,'2002-02-01','01032132222','대구','ROLE_STD',NULL,1,0,'2023-07-27 17:03:04'),
(8,2,'tlfgja1@naver.com','111','박박박',NULL,'2002-05-05','01023432123','대구','ROLE_STD',NULL,1,0,'2023-07-27 17:03:45'),
(9,2,'sadfasdf@naver.com','999','뭐지',NULL,'1981-01-15','01056654554','대구','0',NULL,1,0,'2023-07-27 17:24:35'),
(11,2,'what@naver.com','6546','실험실',NULL,'1998-02-02','010-9977-7799','대구','0',NULL,1,0,'2023-07-27 17:26:06'),
(12,1,'test@gmail.com','{bcrypt}$2a$10$vBMmymA944GQSC31fRbrKO.soaXZaOkxNrnFuqx/A./gGTHRC5joa','김선생','b520c22a-a42e-4e4f-845e-5e206abb5723.png','2023-07-28','010-2384-2394','대구시 중구','ROLE_TC','5c41762b-4ea9-4277-a055-b209e51b47b0.png',1,0,'2023-07-28 18:22:14'),
(15,2,'std1@gmail.com','{bcrypt}$2a$10$RjDnE4mZB4tMJyyoiZkm8u3DlM.d3z7kVWCgBzpLYCHBpmQ7bYu7i','김학생','b3b6d809-29f8-4293-9737-470759970c48.png','2003-08-02','010-2739-3928','대구시 북구','ROLE_STD',NULL,1,0,'2023-08-02 10:20:18'),
(16,2,'d4342@naver.com','{bcrypt}$2a$10$WkjoV6gNrR/LzgxiT3IaLO1.t8Hr1iMpXilC5WnWM3rRdR.XAWXJi','김이름','5c015192-82ce-472c-9766-6e2916e58c76.jpg','2023-08-02','010-0000-0000','대구광역시 서구','ROLE_STD',NULL,1,0,'2023-08-02 16:55:23'),
(19,2,'d43423@naver.com','{bcrypt}$2a$10$BzJk983fQ/C8vHWfTo8fp.6XZJp4VNs6E84iA1NE8Wc7yYWdAbX4G','김박스','b42e012b-6999-4c26-984f-4eb335fb6e36.jpg','2023-06-03','010-0000-0000','대구광역시 서구','ROLE_STD',NULL,1,0,'2023-08-02 16:55:48'),
(20,2,'d4342323@naver.com','{bcrypt}$2a$10$P2e5Cb9p0VxpR3G0wEdtZ.xg.6FiOBAsHfYDLQsQL4Q1Bt/GuBQuy','김냥냥','2af9e753-1a36-4082-b9c9-6e1e5f964e58.jpg','2023-06-05','010-0000-0000','대구광역시 서구','ROLE_STD',NULL,1,0,'2023-08-02 16:55:56'),
(21,2,'d43422323@naver.com','{bcrypt}$2a$10$WkGyfoZhS2RZ4bzO38DD4.4oBe9jrqiT.px324nD/Tg.atKQDoAW.','이랄랄','183d58b4-0ff1-4d91-b99d-d9abe78da4cf.png','2000-06-05','010-0500-0000','대구광역시 서구','ROLE_STD',NULL,1,0,'2023-08-02 16:57:04'),
(22,2,'d43gd3@naver.com','{bcrypt}$2a$10$iP2kWdErEMxrNDqJkDDtbe/fld2LlNl4WF/Dk7oYVDx9S8vCy8wmC','박자바','1a22ea57-2a54-4451-abd5-189c486a3db4.png','2000-08-05','010-0500-0000','대구광역시 서구','ROLE_STD',NULL,1,0,'2023-08-02 16:57:24'),
(23,2,'52350@naver.com','{bcrypt}$2a$10$MxEItifHiF5zPvd0IIyzeO6N9609FG6hqOkC7ByVZ4HKrjjOUFFlO','성자바','c000ba74-2d87-4eb7-bb58-4b486300dd83.png','2000-08-05','010-0500-0420','대구광역시 서구','ROLE_STD',NULL,1,0,'2023-08-02 16:57:40'),
(26,2,'5242350@naver.com','{bcrypt}$2a$10$vvfZevEDc6ZqM99xfXRjFevq.gcisU/dgO3f6QDstzqu09dVAsLh6','나주배','a847d549-484c-469e-963d-9149adb18655.png','2000-08-05','010-0500-0424','두류역','ROLE_STD',NULL,0,0,'2023-08-02 16:58:14'),
(31,2,'52423450@naver.com','{bcrypt}$2a$10$a2Drm9zd3Z8ZkMLOR/.QUuyO2dHeyqmO2QtgS9pwz5ouVOEue1IkC','이랄롤','6628986a-3d98-4d57-8e3a-cf2969aae852.png','2000-08-04','010-0500-4214','두류역','ROLE_STD',NULL,0,0,'2023-08-02 17:04:06'),
(32,2,'450@naver.com','{bcrypt}$2a$10$MruDlpormQwM5lyhEtyZe.sgtDqrwmIfizw7LQWEAxFxQFug7dBEu','하랄랄','b80658f6-d0a5-4522-9327-a5d9fdd285ec.png','2000-08-04','010-0500-4214','두류역','ROLE_STD',NULL,0,0,'2023-08-02 17:04:11'),
(33,2,'4553523@naver.com','{bcrypt}$2a$10$Rp7gVNvMxyTTwZjzlvuyOeRCL8F/o6wGc9SBXffDC.mAG2Ot9yiuW','가나다','f1441e36-c296-4d6f-8612-7e343d0ca715.png','2000-08-04','010-0500-4214','두류역','ROLE_STD',NULL,0,0,'2023-08-02 17:05:12'),
(34,2,'45535223@naver.com','{bcrypt}$2a$10$RveGZR655YRZ9O0qu1zsS.HT1yDnR8KsEPOPRujOT2f3hCIp8cia6','나다라','b56ffa77-945e-4a8a-ba9d-32cdafb989e8.png','2000-08-04','010-0500-4214','두류역','ROLE_STD',NULL,0,0,'2023-08-02 17:05:21'),
(36,2,'455421423@naver.com','{bcrypt}$2a$10$P9urBTBqalyt/XIrNVKVi.QKs9xsBOYYPP7zwQOMbRxFMjV19WKpO','다라마바사','845ba481-4000-421a-aa91-5b7aad586e60.png','2000-10-04','010-0500-4214','두류역','ROLE_STD',NULL,0,0,'2023-08-02 17:05:42'),
(37,2,'4smk4923@naver.com','{bcrypt}$2a$10$wuM5lnYCzn8w1VruPfaxaurMnSUvdS6aBPVccPpY5IpoTilFjCsS2','박함지','60716234-1752-4c1b-bd0f-d5ec29fff304.png','2000-07-04','010-0500-4214','두류역','ROLE_STD',NULL,0,0,'2023-08-02 17:06:07'),
(39,24,'test222@gmail.com','aaa','최변경','ee6bdcde-b9f2-45ae-9f49-c990fb62b69e.jpg','2023-08-03','010-1111-0111','제주도','ROLE_STD',NULL,0,0,'2023-08-03 17:41:09'),
(40,2,'aa@gmail.com','{bcrypt}$2a$10$Hf4ibiHLkqfpBIJQG5Hv/OSfoWn25PPDMzaaqAxgUjOTcRYeuR2Dy','40','c13f8636-a0e4-476d-b4ee-bf1606192334.png','2006-06-06','010-5555-5555','양양','ROLE_STD',NULL,1,0,'2023-08-03 19:01:55'),
(41,2,'zz@gmail.com','{bcrypt}$2a$10$wRAChy8431mzmlE0mml2BeLNnEVK5GsR5oQKlMUPjiIwf6.YAxZSG','마선생','1cbd3686-ce03-41c9-8b05-82fba90695d2.jpg','1987-07-11','010-5555-2312','대구','ROLE_TC',NULL,1,0,'2023-08-03 19:03:01');
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
  KEY `FK_school_TO_class_1` (`school_id`),
  CONSTRAINT `FK_school_TO_class_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
(23,1,'2023','3','8'),
(24,2,'2023','1','1'),
(25,2,'2023','1','2'),
(26,2,'2023','1','3'),
(27,2,'2023','1','4'),
(28,2,'2023','1','5'),
(29,2,'2023','1','6'),
(30,2,'2023','1','7'),
(31,2,'2023','2','1'),
(32,2,'2023','2','2'),
(33,2,'2023','2','3'),
(34,2,'2023','2','4'),
(35,2,'2023','2','5'),
(36,2,'2023','2','6'),
(37,2,'2023','2','7'),
(38,2,'2023','3','1'),
(39,2,'2023','3','2'),
(40,2,'2023','3','3'),
(41,2,'2023','3','4'),
(42,2,'2023','3','5'),
(43,2,'2023','3','6'),
(44,2,'2023','3','7'),
(45,3,'2023','1','1'),
(46,3,'2023','1','2'),
(47,3,'2023','1','3'),
(48,3,'2023','1','4'),
(49,3,'2023','1','5'),
(50,3,'2023','1','6'),
(51,3,'2023','1','7'),
(52,3,'2023','2','1'),
(53,3,'2023','2','2'),
(54,3,'2023','2','3'),
(55,3,'2023','2','4'),
(56,3,'2023','2','5'),
(57,3,'2023','2','6'),
(58,3,'2023','2','7'),
(59,3,'2023','3','1'),
(60,3,'2023','3','2'),
(61,3,'2023','3','3'),
(62,3,'2023','3','4'),
(63,3,'2023','3','5'),
(64,3,'2023','3','6'),
(65,3,'2023','3','7');
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

-- Dump completed on 2023-08-04 18:26:21
