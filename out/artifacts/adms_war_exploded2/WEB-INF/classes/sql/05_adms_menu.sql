USE adms;

insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (1,'메뉴',0,0,1,null,null);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (2,'검색 및 다운로드',0,0,1,null,1);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (3,'업로드 및 수정',0,0,2,null,1);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (4,'평생회비현황',0,0,3,null,1);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (5,'ADMS관리',0,0,4,null,1);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (6,'검색 및 다운로드',0,1,1,'/members',2);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (8,'업로드',0,1,1,'/upload',3);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (9,'ADMIN 관리',0,1,1,'/user',5);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (10,'그룹관리',0,1,2,'/group',5);
insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (11,'일괄수정',0,1,2,'/group',3);
-- insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (7,'회원상세',0,1,2,'/member',2);
-- insert into `adms_menu`(`MENU_ID`,`MENU_NAME`,`IS_DEL`,`IS_LEAF`,`DISP_ORDER`,`MENU_PATH`,`PARENT_MENU_ID`) values (11,'권한관리',0,1,3,'/roll',5);

-- 2018.03.28 Add --
insert into `adms_menu`(`MENU_ID`,`IS_DEL`,`IS_LEAF`,`MENU_PATH`,`MENU_NAME`,`DISP_ORDER`,`PARENT_MENU_ID`) values (14,0,0,null,'개인 정보 조회',5,1);
insert into `adms_menu`(`MENU_ID`,`IS_DEL`,`IS_LEAF`,`MENU_PATH`,`MENU_NAME`,`DISP_ORDER`,`PARENT_MENU_ID`) values (12,0,1,'/myuser','개인 정보 조회',1,14);
insert into `adms_menu`(`MENU_ID`,`IS_DEL`,`IS_LEAF`,`MENU_PATH`,`MENU_NAME`,`DISP_ORDER`,`PARENT_MENU_ID`) values (13,0,1,'/alumnusmembers','동기 조회',2,14);

