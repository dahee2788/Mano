# MaunNote : Mano

프로젝트 Mano는 사용자가 하루의 기분을 숫자로 표현할 수 있는 일기와 일기장을 관리하는 API 프로젝트입니다. 

이 프로젝트를 통해 사용자는 자신의 감정을 기록하고, 일기장을 통해 기분 변화를 쉽게 추적할 수 있습니다.

## TODO
✅ redis를 이용한 securityUser 로그아웃 구현

✅ Auth2를 사용하여 socialMedia 회원가입/로그인 구현

✅ 프로메테우스와 그라파나를 이용한 모니터링 추가

✅ 상세한 기능 추가
    - 일기장 공유
    - 카프카를 이용해 일기 추가시, 공유자에게 알림
    -  Native Query를 사용하여 일기 통계 제공

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
     ```
     git clone https://github.com/dahee2788/Mano.git
     ```

- docker 설치
  1. 이 프로젝트는 dokcer를 이용해 db서버를 컨테이너로 띄우고 있습니다. 원활한 이용을 위해 docker설치를 해주세요.
  2. https://www.docker.com/products/docker-desktop/ 에 접속합니다.
  3. 'Download Docker Desktop' 버튼을 마우스오버하시어 pc 환경에 맞는 항목을 확인한 후, 클릭해주세요.
  4. docker를 설치해주세요.
  5. terminal을 열고 아래와 같이 명령어를 입력하시어 도커가 잘 설치되었는지 확인해주세요.
     ```
     docker --version
     ```

- db서버 시작하기
  1. 'src/main/resources/docker' 위치에서 terminal을 열어주세요.
  2. 아래 명령어를 입력하시어 docker를 시작해주세요.
     ```
     docker-compose up
     ```
  3. 정상적으로 실행되었는지 아래 명령어를 통해 확인 할 수 있습니다. service 이름 'db'인 컨테이너의 status가 'Up'인지 확인해주세요.
     ```
     docker-compose ps
     ```
  4. 만약 정상적으로 작동하지 않는다면 'docker-compose logs db' 를 입력하시어 로그를 확인해보세요.
     ```
     docker-compose logs db
     ```
  5. docker 종료 명령어. docker 종료시 db서버가 내려가면서 어플리케이션 실행에 문제가 있을 수 있습니다.
     ```
     docker-compose down
     ```

- 어플리케이션 시작하기
  
  project root에서 아래와 같이 명령어를 입력해주세요

  1. MAC
     ```
     ./gradlew bootRun
     ```
  2. Window
     ```
     gradlew.bat bootRun
     ```




## 프로젝트 구조도



```
root/src
├── main
│   ├── java
│   │   └── maumnote
│   │       └── mano
│   │           ├── controller
│   │           ├── domain     -> entity
│   │           ├── dto    
│   │           ├── exception -> business exception 클래스
│   │           ├── global
│   │           │   ├── anotation -> custom anotation
│   │           │   ├── config  
│   │           │   ├── security   -> springSecurity 관련 폴더
│   │           │   └── util
│   │           ├── repository
│   │           └── service
│   └── resources
│       ├── db   -> docker를 실행하면 생성됨.
│       ├── docker -> db 컨테이너를 위한 설정 폴더
│       ├── json -> 접근 허용 endpoint를 json으로 관리하고 있음
│       ├── static
│       └── templates
└── test -> 테스트 코드
    └── java
        └── maumnote
            └── mano
                ├── controller
                ├── repository
                └── service
```
<img width="850" alt="스크린샷 2024-10-09 오후 6 42 27" src="https://github.com/user-attachments/assets/a2868a2c-b812-4945-bf22-05db7ff74c53">



## API 문서

이 프로젝트의 API 문서는 Swagger를 통해 제공됩니다. 

* 개발
> http://43.200.254.39:8080/swagger-ui/index.html#/
* 로컬
> http://localhost:8080/swagger-ui/index.html#/


## 트러블슈팅

1. 요청 데이터 역직렬화시 발생했던 문제
   > https://dev-heeya.tistory.com/44
3. Springboot 3.xx의 기본 패키지 문제
   > https://dev-heeya.tistory.com/47
5. SpringSecurity 도입 이후, Controller test 실패 문제
   > https://dev-heeya.tistory.com/51
7. entity 간의 관계 정의 후, builer의 문제
   > https://dev-heeya.tistory.com/67

  
