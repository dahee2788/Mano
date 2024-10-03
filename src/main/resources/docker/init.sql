-- 새로운 어드민 계정 생성
CREATE USER 'new_admin'@'%' IDENTIFIED BY 'new_admin_password';
GRANT ALL PRIVILEGES ON *.* TO 'new_admin'@'%' WITH GRANT OPTION;

-- root 계정 삭제
DROP USER 'root'@'localhost';

-- 시작 DB에 접근 가능한 유저 생성
CREATE USER 'db_user'@'%' IDENTIFIED BY 'db_user_password';
GRANT ALL PRIVILEGES ON test_mano_db.* TO 'db_user'@'%';

FLUSH PRIVILEGES;