# ssd-backend
API Server For SSD(Smart Soju Dispenser)

### How to distribute using AWS EC2
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

```
git clone https://github.com/new-tech-project-2/ssd-backend.git

cd ./ssd-backend

sudo chmod 777 ./gradlew

[Write down "MYSQL_ROOT_PASSWORD", "SPRIMG_DATASOURCE_PASSWORD" in docker-compose.yml] 

./gradlew build

docker-compose up -d
```
