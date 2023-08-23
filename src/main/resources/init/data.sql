
INSERT INTO `school` VALUES
                         (1,'함지고등학교','hamji.png','7240273'),
                         (2,'오성고등학교','오성고등학교.png','7240099');

INSERT INTO `van` VALUES
                      (1,1,'2023','1','1'),
                      (2,1,'2023','1','2');

INSERT INTO `user` VALUES
                       (1,1,'test@gmail.com','{bcrypt}$2a$10$vBMmymA944GQSC31fRbrKO.soaXZaOkxNrnFuqx/A./gGTHRC5joa','김선생','b520c22a-a42e-4e4f-845e-5e206abb5723.png','2023-07-28','010-2384-2394','대구시 중구','ROLE_TC','5c41762b-4ea9-4277-a055-b209e51b47b0.png',1,0,'2023-07-28 18:22:14'),
                       (2,2,'std1@gmail.com','{bcrypt}$2a$10$RjDnE4mZB4tMJyyoiZkm8u3DlM.d3z7kVWCgBzpLYCHBpmQ7bYu7i','김학생','b3b6d809-29f8-4293-9737-470759970c48.png','2003-08-02','010-2739-3928','대구시 북구','ROLE_STD',NULL,1,0,'2023-08-02 10:20:18');

INSERT INTO `sbj_category` VALUES
                               (1,'국어',1),
                               (2,'국어',2),
                               (3,'수학',1),
                               (4,'수학',2);

INSERT INTO `subject` VALUES
                          (1,1,'화법과 언어'),
                          (2,1,'독서와 작문'),
                          (3,2,'국어');

INSERT INTO `aca_result` VALUES
                             (1,1,1,'2023',1,1,100,1,1,1),
                             (2,1,1,'2023',1,1,100,1,1,1);

INSERT INTO `mock_result` VALUES
                              (1,1,4,'2023',6,110,1,3),
                              (2,1,7,'2023',12,0,3,30);

INSERT INTO `tc_sbj` VALUES
                         (1,1),
                         (1,2);

