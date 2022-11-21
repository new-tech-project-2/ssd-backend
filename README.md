![header](https://capsule-render.vercel.app/api?type=rounded&color=auto&section=header&text="SSD%20Dispenser"&fontSize=70)


# Introduction

`SSD(Smart Soju Dispenser)` 프로젝트의 백엔드 서버에 사용되는 레포지토리입니다.

### 👉 연계 Frontend Repo
> https://github.com/new-tech-project-2/ssd-frontend

### 👉 연계 Dispenser Repo
> https://github.com/new-tech-project-2/ssd-dispenser

### 👉 사용된 API 확인하기 (서버 구동 후 확인 가능)
> http://localhost:8080/swagger-ui.html

# Software Development Environment

- IDE: Intellij IDEA Ultimate

- OS: Windows 11

- Language: Java SE 11

- SDK: Java SE Devlopment Kit 11

- Application: SpringBoot 2.7.4 (Gradle)

- Database: MySQL 8.0

- Infrastructure: Docker 20.10.12

- AWS EC2 AMI: Ubuntu 20.04.5 LTS


# Distribution using AWS EC2 before Execution 
```
sudo apt-get update
```

```
sudo apt install docker.io

sudo apt install docker-compose

sudo usermod -aG docker ${USER}

sudo reboot
```

```
sudo apt install openjdk-11-jdk

nano ~/.bashrc

export JAVA_HOME= $(dirname $(dirname $(readlink -f $(which java))))
export PATH=$PATH:$JAVA_HOME/bin

source ~/.bashrc
```

# Execution
```
git clone https://github.com/new-tech-project-2/ssd-backend.git

cd ./ssd-backend

sudo chmod 777 ./gradlew

[Write down "MYSQL_ROOT_PASSWORD", "SPRING_DATASOURCE_PASSWORD" in docker-compose.yml] 

./gradlew build

docker-compose up -d
```
(※ 처음에 SpringBoot Container 와 MySQL Container 동시에 시작되기 때문에, MySQL Container가 다 켜지지 않은 상태에선 SpringBoot Container가 꺼질 수 있음. MySQL Container가 다 켜진 뒤 SpringBoot Container만 다시 시작해주면 됌) 

# Function
**디스펜서 소켓 연결**   
디스펜서와 서버간의 소켓 연결을 진행한다. 만약 디스펜서 소켓 연결이 종료된다면, 같은 디스펜서의 모든 사용자의 소켓 연결도 강제로 종료시킨다.

**술잔 등록**  
술잔 등록 모드에서 디스펜서와 술잔이 NFC 태그를 진행하면, 술잔의 정보가 데이터베이스에 저장된다.

**로그인**  
디스펜서에 붙어있는 디스펜서 토큰을 입력하면, 서버에서 JWT를 발급하며 로그인을 진행하고 소켓 연결도 진행한다.

**술잔 조회**  
같은 디스펜서의 술잔의 정보를 조회할 수 있다.

**술잔 수정**  
술잔에 사용자 이름, 세부사항, 가능 음주량을 작성할 수 있다.

**술잔 삭제**  
술잔을 삭제할 수 있다.

**술자리 시작**  
술자리 시작 버튼을 누르면 술잔 등록 모드에서 술자리 모드로 바뀐다.

**술자리 종료**  
술자리 종료 버튼을 누르면 술자리 모드에서 술잔 등록 모드로 바뀌며, 모든 사람의 현재 음주량이 초기화된다.

**술 마시기**  
술자리 모드에서 디스펜서와 술잔이 NFC 태그를 진행하며, 데이터베이스에서 현재 음주량에서 1잔이 추가된다.

<div align=center>
  derived by<br>
  <img src="https://user-images.githubusercontent.com/39671049/202901117-1890b7f6-792b-4165-8ad7-06ff466454bc.png">
</div>

![Footer](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=footer)
