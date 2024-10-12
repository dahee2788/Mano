-- 새로운 어드민 계정 생성
CREATE USER 'new_admin'@'%' IDENTIFIED BY 'new_admin_password';
GRANT ALL PRIVILEGES ON *.* TO 'new_admin'@'%' WITH GRANT OPTION;

-- root 계정 삭제
DROP USER 'root'@'localhost';

-- 시작 DB에 접근 가능한 유저 생성
CREATE USER 'local_user'@'%' IDENTIFIED BY 'local_user_password';
GRANT ALL PRIVILEGES ON mano.* TO 'local_user'@'%';

FLUSH PRIVILEGES;

-- auto-generated definition
create table member
(
    id              varchar(36)                         not null comment '사용자 고유 식별 키'
        primary key,
    nickname        varchar(50)                         null comment '사용자 별명',
    profile_image   varchar(255)                        null comment '사용자 프로필 이미지 경로',
    introduction    varchar(200)                        null comment '소개글',
    join_date       timestamp default (now())           not null comment '가입일시',
    withdrawal_date timestamp                           null comment '탈퇴일시',
    create_date     timestamp default CURRENT_TIMESTAMP not null comment '데이터 생성일시',
    create_id       varchar(36)                         not null comment '데이터 생성자 id',
    update_date     timestamp default CURRENT_TIMESTAMP not null comment '데이터 수정일시',
    update_id       varchar(36)                         not null comment '데이터 수정자 id',
    join_type       varchar(20)                         not null comment '가입 종류',
    email           varchar(255)                        not null comment '이메일',
    password        varchar(64)                         null comment '비밀번호',
    social_key      varchar(50)                         null comment '소셜미디어 연동 고유키'
)
    comment '사용자 테이블';

INSERT INTO mano.member (id, nickname, profile_image, introduction, join_date, withdrawal_date, create_date, create_id,
                         update_date, update_id, join_type, email, password, social_key)
VALUES ('a7aeda02-a508-44f7-b17b-55b1b7400ac0', 'system', null, null, '2024-08-25 21:34:39', null,
        '2024-08-25 21:34:47', 'system', '2024-08-25 21:34:47', 'system', 'GENERAL', 'system@test.com',
        '$2a$10$V0qqknCUlOZMjmWyf7YZM.EuMxoCiRGYuJpJQMsLJQwXTB1BINhci', null);
INSERT INTO mano.member (id, nickname, profile_image, introduction, join_date, withdrawal_date, create_date, create_id,
                         update_date, update_id, join_type, email, password, social_key)
VALUES ('cd6545c8-77b3-41d0-9c6b-8777f83f4c78', null, null, null, '2024-09-01 10:19:22', null, '2024-09-01 10:19:23',
        'system', '2024-09-01 10:19:23', 'system', 'GENERAL', 'test@test.com',
        '$2a$10$V0qqknCUlOZMjmWyf7YZM.EuMxoCiRGYuJpJQMsLJQwXTB1BINhci', null);


-- auto-generated definition
create table member_role
(
    id        int auto_increment comment 'id'
        primary key,
    member_id varchar(36) not null comment '회원 id',
    role_id   int         not null comment '권한 id'
)
    comment '회원별 권한 테이블';

INSERT INTO mano.member_role (id, member_id, role_id)
VALUES (1, 'a7aeda02-a508-44f7-b17b-55b1b7400ac0', 2);
INSERT INTO mano.member_role (id, member_id, role_id)
VALUES (2, 'cd6545c8-77b3-41d0-9c6b-8777f83f4c78', 1);


-- auto-generated definition
create table note
(
    id            bigint auto_increment comment 'id'
        primary key,
    notebook_id   bigint                              not null comment '일기장 id',
    member_id     varchar(36)                         not null comment '사용자 id',
    emotion_score int                                 not null comment '감정의 척도',
    content       varchar(2000)                       not null comment '일기 내용',
    write_date    date                                not null comment '일기 작성일자 ',
    create_date   timestamp default CURRENT_TIMESTAMP not null comment '데이터 생성일자',
    create_id     varchar(36)                         not null comment '데이터 생성자 id',
    update_date   timestamp default CURRENT_TIMESTAMP not null comment '데이터 수정일자',
    update_id     varchar(36)                         not null comment '데이터 수정자 id'
)
    comment '일기 테이블';

-- auto-generated definition
create table note_photo
(
    id      bigint auto_increment comment 'id'
        primary key,
    note_id bigint       not null comment '일기 id',
    image   varchar(255) not null comment '이미지 경로'
)
    comment '일기 사진 테이블';

create index note_photo_note_id_fk
    on note_photo (note_id);

-- auto-generated definition
create table notebook
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(50)                         not null comment '이름',
    create_date timestamp default CURRENT_TIMESTAMP not null comment '데이터 생성일자',
    create_id   varchar(36)                         not null comment '데이터 생성자 id',
    update_date timestamp default CURRENT_TIMESTAMP not null comment '데이터 수정일자',
    update_id   varchar(36)                         not null comment '데이터 수정자 id'
)
    comment '일기장 테이블';

-- auto-generated definition
create table notebook_permission
(
    id          bigint auto_increment comment 'id'
        primary key,
    notebook_id bigint                              not null comment '일기장 id',
    member_id   varchar(36)                         not null comment '사용자 id',
    create_date timestamp default CURRENT_TIMESTAMP not null comment '데이터 생성일자',
    create_id   varchar(36)                         not null comment '데이터 생성자 id',
    update_date timestamp default CURRENT_TIMESTAMP not null comment '데이터 수정일자',
    update_id   varchar(36)                         not null comment '데이터 수정자 id'
)
    comment '일기장 권한 테이블';

-- auto-generated definition
create table role
(
    id               int auto_increment comment 'id'
        primary key,
    role_name        varchar(50)  not null comment '권한명',
    role_description varchar(200) null comment '권한 설명'
)
    comment '권한';

INSERT INTO mano.role (id, role_name, role_description)
VALUES (1, 'ROLE_USER', '일반 유저');
INSERT INTO mano.role (id, role_name, role_description)
VALUES (2, 'ROLE_ADMIN', '운영자');

commit;






    
    

