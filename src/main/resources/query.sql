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
  `year` int(11) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aca_result`
--

LOCK TABLES `aca_result` WRITE;
/*!40000 ALTER TABLE `aca_result` DISABLE KEYS */;
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
  `year` int(11) NOT NULL,
  `mon` tinyint(4) NOT NULL,
  `standard_score` tinyint(4) NOT NULL,
  `rating` tinyint(4) NOT NULL,
  `percent` tinyint(4) NOT NULL,
  PRIMARY KEY (`result_id`),
  KEY `FK_user_TO_mock_result_1` (`user_id`),
  KEY `FK_subject_TO_mock_result_1` (`subject_id`),
  CONSTRAINT `FK_subject_TO_mock_result_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
  CONSTRAINT `FK_user_TO_mock_result_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mock_result`
--

LOCK TABLES `mock_result` WRITE;
/*!40000 ALTER TABLE `mock_result` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
(6,'영어',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES
(1,1,'화법과 작문'),
(2,1,'언어와 매체'),
(3,1,'비문학');
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
(1,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(1,1,'tncjs3598@naver.com','tncjs123','성수천',NULL,'1997-09-01','01028087210','대구','ROLE_ADMIN',NULL,1,0,'2023-07-27 14:23:20'),
(2,2,'naver@naver.com','q1w2e3r4','나강림',NULL,'1997-12-11','01012345678','런던','ROLE_STD',NULL,1,0,'2023-07-27 16:58:12'),
(3,1,'mail1234@naver.com','4321','이아메',NULL,'2001-03-21','01055547754','대구','ROLE_STD',NULL,1,0,'2023-07-27 16:59:30'),
(4,1,'ghghgh@naver.com','ghgh12','호날두',NULL,'1993-11-24','01077887788','리스본','ROLE_STD',NULL,0,0,'2023-07-27 17:00:10'),
(5,1,'haha@naver.com','haha','김바람',NULL,'1999-07-04','01023567812','대구','ROLE_STD',NULL,0,0,'2023-07-27 17:01:19'),
(6,2,'teacher11@naver.com','001','김담임','123123','1981-01-15','01014141414','123123','ROLE_TC',NULL,1,1,'2023-07-27 17:02:21'),
(7,2,'std132@naver.com','654','정우등',NULL,'2002-02-01','01032132222','대구','ROLE_STD',NULL,1,0,'2023-07-27 17:03:04'),
(8,2,'tlfgja1@naver.com','111','박박박',NULL,'2002-05-05','01023432123','대구','ROLE_STD',NULL,0,0,'2023-07-27 17:03:45'),
(9,2,'sadfasdf@naver.com','999','뭐지',NULL,'1981-01-15','01056654554','대구','0',NULL,0,0,'2023-07-27 17:24:35'),
(11,2,'what@naver.com','6546','실험실',NULL,'1998-02-02','010-9977-7799','대구','0',NULL,0,0,'2023-07-27 17:26:06'),
(12,1,'test@gmail.com','{bcrypt}$2a$10$vBMmymA944GQSC31fRbrKO.soaXZaOkxNrnFuqx/A./gGTHRC5joa','김선생','b520c22a-a42e-4e4f-845e-5e206abb5723.png','2023-07-28','010-2384-2394','대구시 중구','ROLE_TC','5c41762b-4ea9-4277-a055-b209e51b47b0.png',0,0,'2023-07-28 18:22:14'),
(15,24,'std1@gmail.com','{bcrypt}$2a$10$RjDnE4mZB4tMJyyoiZkm8u3DlM.d3z7kVWCgBzpLYCHBpmQ7bYu7i','김학생','b3b6d809-29f8-4293-9737-470759970c48.png','2003-08-02','010-2739-3928','대구시 북구','ROLE_STD',NULL,0,0,'2023-08-02 10:20:18');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_token`
--

DROP TABLE IF EXISTS `user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_token` (
  `token_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `access_token` varchar(200) NOT NULL,
  `refresh_token` varchar(200) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`token_id`),
  KEY `FK_user_TO_user_token_1` (`user_id`),
  CONSTRAINT `FK_user_TO_user_token_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_token`
--

LOCK TABLES `user_token` WRITE;
/*!40000 ALTER TABLE `user_token` DISABLE KEYS */;
INSERT INTO `user_token` VALUES
(1,12,'0:0:0:0:0:0:0:1','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMiIsInJvbGVzIjpbIlJPTEVfVEMiXSwiaWF0IjoxNjkwOTM4NjQyLCJleHAiOjE2OTA5NDIyNDJ9.bZd4zneAfiqwlwulJM9j6MrwhtM7CJc-ftpNWoeo9hk','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMiIsInJvbGVzIjpbIlJPTEVfVEMiXSwiaWF0IjoxNjkwOTM4NjQyLCJleHAiOjE2OTIyMzQ2NDJ9.Mb_I-6IvMKZMl94Hm46W4cHpTe2m2nkkYYZbaWfQ1Fg','2023-08-02 10:10:46','2023-08-02 10:10:46'),
(2,15,'0:0:0:0:0:0:0:1','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNSIsInJvbGVzIjpbIlJPTEVfU1REIl0sImlhdCI6MTY5MDkzOTI2MSwiZXhwIjoxNjkwOTQyODYxfQ.UJVRLbcVs2m-pTFFxd4iFmrYwjRQjnTMR48RqOW1X-Y','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNSIsInJvbGVzIjpbIlJPTEVfU1REIl0sImlhdCI6MTY5MDkzOTI2MSwiZXhwIjoxNjkyMjM1MjYxfQ.L77MfzBo_8W-MrNTGHnpXugCmUouNOVvR56ukHClGQ8','2023-08-02 10:21:06','2023-08-02 10:21:06'),
(3,12,'0:0:0:0:0:0:0:1','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMiIsInJvbGVzIjpbIlJPTEVfVEMiXSwiaWF0IjoxNjkwOTQzNzU1LCJleHAiOjE2OTA5NDczNTV9.3lqunAxPwPY5FXTaAVrEVWIwo7DQcCRy3kljfxyTq2A','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMiIsInJvbGVzIjpbIlJPTEVfVEMiXSwiaWF0IjoxNjkwOTQzNzU1LCJleHAiOjE2OTIyMzk3NTV9.-pCmMnEJjUi6vCiUJmDS_SiXKi6d3j4PAj62A0n5TcQ','2023-08-02 11:35:55','2023-08-02 11:35:55');
/*!40000 ALTER TABLE `user_token` ENABLE KEYS */;
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

-- Dump completed on 2023-08-02 11:44:34
