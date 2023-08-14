CREATE TABLE `school` (
                          `school_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `nm` varchar(50) NOT NULL,
                          `logo` varchar(100) NOT NULL,
                          `school_cd` char(7) NOT NULL,
                          PRIMARY KEY (`school_id`),
                          UNIQUE KEY `nm` (`nm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `sbj_category` (
                                `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `nm` varchar(10) NOT NULL,
                                `type` tinyint(4) NOT NULL COMMENT '내신 1, 모의고사 2',
                                PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `subject` (
                           `subject_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `category_id` bigint(20) NOT NULL,
                           `nm` varchar(20) NOT NULL,
                           PRIMARY KEY (`subject_id`),
                           KEY `FK_sbj_category_TO_subject_1` (`category_id`),
                           CONSTRAINT `FK_sbj_category_TO_subject_1` FOREIGN KEY (`category_id`) REFERENCES `sbj_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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