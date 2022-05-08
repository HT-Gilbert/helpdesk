CREATE TABLE user (
  id integer not null AUTO_INCREMENT COMMENT '시퀀스',
  userid VARCHAR(20) not null COMMENT '직원번호',
  username VARCHAR(20) not null COMMENT '직원명',
  userlv VARCHAR(20) not null COMMENT '사용자등급',  
  totallogincount int DEFAULT 0 COMMENT '총접속횟수',
  loginfailcount int DEFAULT 0 COMMENT '접속오류횟수',
  pwd VARCHAR(30) not null COMMENT '패스워드' COLLATE 'utf8mb4_general_ci',
  lsatlogintime DATETIME NULL DEFAULT NULL COMMENT '마지막 로그인 시간',
  registertime DATETIME NULL DEFAULT NULL COMMENT '등록일',
  updatetime DATETIME NULL DEFAULT NULL COMMENT '수정일',
  changepwdupdatedate DATETIME NULL DEFAULT NULL COMMENT '비밀번호변경일',
  encpwd VARCHAR(50) not null COMMENT '암호화비밀번호' COLLATE 'utf8mb4_general_ci',
  PRIMARY KEY (id),
  UNIQUE INDEX bank_id (bank_id)
) COMMENT = '사용자';