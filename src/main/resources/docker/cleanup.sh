#!/bin/bash

# 데이터베이스 초기화가 완료될 때까지 대기
while ! mysqladmin ping -h"localhost" --silent; do
    sleep 1
done

# init.sql 실행
mysql -u root -p"$MYSQL_ROOT_PASSWORD" < /docker-entrypoint-initdb.d/init.sql

# init.sql 파일 삭제
rm /docker-entrypoint-initdb.d/init.sql

# 자기 자신(cleanup.sh) 삭제
rm -- "$0"