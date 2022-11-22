![header](https://capsule-render.vercel.app/api?type=rounded&color=auto&section=header&text="SSD%20Dispenser"&fontSize=70)

SSD(Smart Soju Dispenser) 프로젝트의 백엔드 서버에 사용되는 레포지토리입니다.
## 👉 연계 Frontend Repo
> https://github.com/new-tech-project-2/ssd-front

## 👉 연계 Dispenser Repo
> https://github.com/new-tech-project-2/ssd-dispenser

## 👉 사용된 API 확인하기 (서버 구동 후 확인 가능)
> http://localhost:8080/swagger-ui.html

---


## 프로젝트 소개
SSD는 Smart Soju Dispenser의 줄임말로, NFC를 이용한 스마트 소주 디스펜서이다.
SSD는 건전하고 공정한 술자리 문화 형성을 목표로 하고 있다.

## 개발 동기 및 개발 목적
주 1회 이상 술자리를 가지는 20대 비율은 43.6%로 우리나라 술자리 문화는 활발하다. 그러나 과도한 술자리로 인해 많은 문제가 발생할 수 있다. 실제로 우리나라 20대의 44%는 과도한 음주로 블랙아웃을 경험하였 고, 잔 공유 시 감염 위험이 392% 증가한다 는 통계 결과가 존재한다.
이처럼 과음 문제와 위생 문제를 예방하기 위해 NFC를 이용한 스마트 소주 디스펜서를 제안한다. 추가적으로 사용자에게 친근함 및 흥미를 더해주기 위해 재미 요소를 추가하여 사용 동기를 유발한다.

## 개발 환경 및 사용 기술
- FrontEnd : React.js, Recoil, ReactQuery, Tailwind CSS, DaisyUI, Flutter, Route53, CloudFront
- BackEnd : Spring Boot, MySQL, Docker, Route53, EC2, ALB
- Embedded : RaspberryPi, NFC, TinkerCad, Python (, 모듈 및 드라이버 : PN532, JT-DC3W, L9110S)
- 공통 : WebSocket, Git
- 협업툴 : Nothion, GoogleMeet, Slack, Figma, GitHub, PostMan

## 사용 방법
1. 모바일 앱의 초기 화면에서 디스펜서의 토큰을 입력하여 로그인한다.
2. 로그인 수행 시, 자동으로 '술잔 등록 모드'로 메인 페이지에 접속된다.
3. 디스펜서의 NFC리더에 술잔의 NFC 스티커를 태그한다.
4. 태그 시, 모바일 앱의 메인 페이지에 자동으로 해당 술잔이 등록되어 보여진다.
5. 메인 페이지에서 술잔의 정보(이름, 설명, 주량)를 수정할 수 있다.
6. 메인 페이지에서 '술자리 시작하기' 버튼을 누른다.
7. 자동으로 디스펜서는 '술자리 모드'로 변경되고, 모바일 앱 화면은 술자리 페이지로 변경된다.
8.디스펜서의 NFC리더에 술잔의 NFC 스티커를 태그한다.
9. 태그 시, 디스펜서에서 자동으로 술 한잔이 토출된다.
10. 동시에 모바일 앱 화면에서 현재 음주량이 갱신되어 보여진다.
11. 주량을 넘어선 과음을 할 시, 과음 알림이 모바일 앱을 통해 발생한다.

## 메인 기능
- 디바이스 :
1. NFC를 통한 술잔 등록
2. NFC를 통한 자동 술 토출
- 모바일 앱 :
1. 등록된 술자리 구성원들의 술잔 확인 및 편집
2. 술자리 모드, 술잔 등록 모드 변경
3. 술자리 구성원들의 현재 음주량 및 술자리 진행 시간 확인
4. 과음, 빠른 음주, 느린 음주, 격차, 시간 등의 다양한 경고 이벤트 발생 시 경고 알림창과 함께 경고음 발생
<img src="https://user-images.githubusercontent.com/82189072/203345436-ff101e97-685f-43c3-a797-abbe7b40bb46.jpg" width="720" height="1080"/>


## 기대 효과
- 건전하고 공정한 술자리 문화 형성에 기여할 것으로 기대된다.
- 다양한 이벤트 알림으로 단조로운 술자리에 재미 요소가 될 것으로 기대된다.

## 향후 계획
- 사용자 베타 테스트를 통해 피드백을 받아 업데이트할 예정이다.
- 다양한 음주 이벤트를 추가하여 사용자에게 엔터테인먼트적인 효과를 제공할 예정이다.
- 디바이스 디자인을 업그레이드할 예정이다.

## 시연 영상
- YouTube Link : https://youtu.be/mpeXgQcRQto

---


## 백엔드 개발환경
- IDE: Intellij IDEA Ultimate

- OS: Windows 11

- Language: Java SE 11

- SDK: Java SE Devlopment Kit 11

- Application: SpringBoot 2.7.4 (Gradle)

- Database: MySQL 8.0

- Infrastructure: Docker 20.10.12

- AWS EC2 AMI: Ubuntu 20.04.5 LTS


## AWS EC2 배포 방법(실행 전)
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

## 실행
```
git clone https://github.com/new-tech-project-2/ssd-backend.git

cd ./ssd-backend

sudo chmod 777 ./gradlew

[Write down "MYSQL_ROOT_PASSWORD", "SPRING_DATASOURCE_PASSWORD" in docker-compose.yml] 

./gradlew build

docker-compose up -d
```
(※ 처음에 SpringBoot Container 와 MySQL Container 동시에 시작되기 때문에, MySQL Container가 다 켜지지 않은 상태에선 SpringBoot Container가 꺼질 수 있음. MySQL Container가 다 켜진 뒤 SpringBoot Container만 다시 시작해주면 됌) 


<div align=center>
  derived by<br>
  <img src="https://user-images.githubusercontent.com/39671049/202901117-1890b7f6-792b-4165-8ad7-06ff466454bc.png">
</div>

![Footer](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=footer)
