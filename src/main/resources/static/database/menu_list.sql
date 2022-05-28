INSERT INTO helpdesk.menu_list (menu_name,menu_lv,menu_dislist,menu_upperid,menu_role,menu_link) VALUES
	 ('ITAMS',0,1,0,0,'/'),
	 ('HTMS',0,2,0,0,'/'),
	 ('정기점검등록',0,3,0,0,'/'),
	 ('로그인',0,4,0,999,'/login'),
	 ('로그아웃',0,5,0,999,'/'),
	 ('관리자',0,6,0,999,'/'),
	 ('장애처리프로그램',1,1,10,0,'/'),
	 ('엔지니어프로그램',2,2,10,999,'/'),
	 ('PC Host Name',3,1,10,0,'/'),
	 ('FAQ/자료요청',4,1,10,0,'/');
INSERT INTO helpdesk.menu_list (menu_name,menu_lv,menu_dislist,menu_upperid,menu_role,menu_link) VALUES
	 ('구 119서버',5,1,10,0,'/'),
	 ('OA프로그램',100,1,1,0,'/'),
	 ('업무프로그램',100,2,1,0,'/'),
	 ('장치드라이버',100,3,1,0,'/board/driver'),
	 ('프린터/복합기',100,4,1,0,'/'),
	 ('메뉴얼',100,5,1,0,'/'),
	 ('엔지니어공지사항',100,1,2,0,'/'),
	 ('OA프로그램',100,2,2,0,'/'),
	 ('장애처리드라이버',100,3,2,0,'/'),
	 ('서식자료실',100,4,2,0,'/');
INSERT INTO helpdesk.menu_list (menu_name,menu_lv,menu_dislist,menu_upperid,menu_role,menu_link) VALUES
	 ('임시자료실',100,5,2,0,'/'),
	 ('메뉴얼',100,6,2,0,'/');
