# 프로젝트 제목

프로젝트에 대한 간결하고 명확한 설명.


## 프로젝트 환경
- Java: JDK 17

- Spring Boot: 3.3.2

- Spring Security: 6 (Spring Boot 3.3.2에 포함됨)

- Database: MySQL 8.0.33 (Docker 컨테이너에서 실행)

- 기타 라이브러리:

    Spring Web

    Spring Web Services

    Spring Data JPA

    Spring Validation

    JSON Web Token (JJWT)

    Lombok

    Spring Boot Test

    Spring Security Test

- 빌드 도구: Gradle

- 테스트 프레임워크: JUnit5 


## 설치 방법

- 원격저장소에서 코드 가져오기
  1. 수행하고자 하는 로컬 폴더에서 terminal을 엽니다.
  2. terminal에서 아래 명령어를 입력하세요.

> git clone https://github.com/dahee2788/Mano.git

- docker 설치
  1. 이 프로젝트는 dokcer를 이용해 db서버를 컨테이너로 띄우고 있습니다. 원활한 이용을 위해 docker설치를 해주세요.
  2. https://www.docker.com/products/docker-desktop/ 에 접속합니다.
  3. 'Download Docker Desktop' 버튼을 마우스오버하시어 pc 환경에 맞는 항목을 확인한 후, 클릭해주세요.
  4. docker를 설치해주세요.
  5. terminal을 열고 아래와 같이 명령어를 입력하시어 도커가 잘 설치되었는지 확인해주세요.
> docker --version

- db서버 시작하기
  1. 'src/main/resources/docker' 위치에서 terminal을 열어주세요.
  2. 아래 명령어를 입력하시어 docker를 시작해주세요.
     > docker-compose up
  3. 정상적으로 실행되었는지 아래 명령어를 통해 확인 할 수 있습니다. service 이름 'db'인 컨테이너의 status가 'Up'인지 확인해주세요.
     > docker-compose ps
  4. 만약 정상적으로 작동하지 않는다면 'docker-compose logs db' 를 입력하시어 로그를 확인해보세요.
     > docker-compose logs db
  5. docker 종료 명령어. docker 종료시 db서버가 내려가면서 어플리케이션 실행에 문제가 있을 수 있습니다.
     > docker-compose down

- 어플리케이션 시작하기
  project root에서 아래와 같이 명령어를 입력해주세요

  1. MAC
     > ./gradlew bootRun
  2. Window
     > ./gradlew bootRun
  
