-- helpdesk.board definition

CREATE TABLE `board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `title` varchar(200) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `read_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '조회수',
  `register_id` varchar(100) NOT NULL COMMENT '작성자',
  `register_time` datetime DEFAULT NULL COMMENT '작성일',
  `update_time` datetime DEFAULT NULL COMMENT '수정일',
  `is_notice` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='게시판';


-- helpdesk.board_file definition

CREATE TABLE `board_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `board_id` bigint(20) NOT NULL COMMENT '게시글 번호',
  `orig_file_name` varchar(250) DEFAULT NULL COMMENT '파일원본이름',
  `save_file_name` varchar(250) DEFAULT NULL COMMENT '파일이름',
  `file_size` int(11) DEFAULT 0 COMMENT '파일크기',
  `file_ext` varchar(10) DEFAULT NULL COMMENT '파일확장자',
  `file_path` varchar(250) DEFAULT NULL COMMENT '파일경로',
  `delete_yn` char(1) DEFAULT 'N' COMMENT '삭제여부',
  `register_time` datetime DEFAULT NULL COMMENT '작성일',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `board_id` (`board_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시판파일관리';


-- helpdesk.category_list definition

CREATE TABLE `category_list` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='분류항목';


-- helpdesk.contact_list definition

CREATE TABLE `contact_list` (
  `dept_name` varchar(100) DEFAULT NULL COMMENT '부서',
  `ptb_name` varchar(50) DEFAULT NULL COMMENT '담당자',
  `ptb_tel_no` varchar(50) DEFAULT NULL COMMENT '대표번호',
  `task_name` varchar(200) DEFAULT NULL COMMENT '담당업무',
  `show_order` tinyint(4) DEFAULT NULL COMMENT '표시순서'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='담당자리스트';


-- helpdesk.driver definition

CREATE TABLE `driver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `title` varchar(200) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `tag` varchar(100) NOT NULL COMMENT '태그',
  `read_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '조회수',
  `register_id` varchar(100) NOT NULL COMMENT '작성자',
  `register_time` datetime DEFAULT NULL COMMENT '등록일',
  `update_time` datetime DEFAULT NULL COMMENT '수정일',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='장치드라이버';


-- helpdesk.menu_list definition

CREATE TABLE `menu_list` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '메뉴번호',
  `menu_name` varchar(80) NOT NULL COMMENT '메뉴명',
  `menu_lv` int(11) DEFAULT NULL COMMENT '메뉴레벨',
  `menu_dislist` int(11) DEFAULT NULL COMMENT '표시순서',
  `menu_upperid` int(11) DEFAULT NULL COMMENT '상위메뉴번호',
  `menu_role` int(11) DEFAULT 0 COMMENT '메뉴권한',
  `menu_link` varchar(80) DEFAULT '/' COMMENT '메뉴링크',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='메뉴리스트';

-- helpdesk.`user` definition

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '시퀀스',
  `userid` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `userrole` varchar(20) DEFAULT NULL,
  `totallogincount` int(11) DEFAULT NULL,
  `loginfailcount` int(11) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  `lastlogintime` datetime DEFAULT NULL,
  `registertime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `changepwdupdatedate` datetime DEFAULT NULL,
  `enckey` varchar(50) NOT NULL COMMENT '암호화키',
  `encpwd` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `bank_id` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='사용자';


-- helpdesk.tag_list definition

CREATE TABLE `tag_list` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(100) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `tag_list_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category_list` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='태그항목';