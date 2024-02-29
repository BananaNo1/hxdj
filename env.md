# 数据库集群

## docker子网络

```shell
docker network create --subnet 172.18.0.0/18 mynet
```

## 数据库

### 数据库1

```shell

docker run -it -d --name mysql_1 -p 12001:3306 \
    --net mynet --ip 172.18.0.2  \
    -m 400m -v /root/mysql_1/data:/var/lib/mysql \
    -v /root/mysql_1/config:/etc/mysql/conf.d \
    -e MYSQL_ROOT_PASSWORD=abc123456 \
    -e TZ=Asia/Shanghai --privileged=true \
    mysql:8.0.23 \
    --lower_case_table_names=1
```

### 数据库2

```shell
docker run -it -d --name mysql_2 -p 12002:3306 \
    --net mynet --ip 172.18.0.3  \
    -m 400m -v /root/mysql_2/data:/var/lib/mysql \
    -v /root/mysql_2/config:/etc/mysql/conf.d \
    -e MYSQL_ROOT_PASSWORD=abc123456 \
    -e TZ=Asia/Shanghai --privileged=true \
    mysql:8.0.23 \
    --lower_case_table_names=1
```

### 数据库3

```shell
docker run -it -d --name mysql_3 -p 12003:3306 \
    --net mynet --ip 172.18.0.4  \
    -m 400m -v /root/mysql_3/data:/var/lib/mysql \
    -v /root/mysql_3/config:/etc/mysql/conf.d \
    -e MYSQL_ROOT_PASSWORD=abc123456 \
    -e TZ=Asia/Shanghai --privileged=true \
    mysql:8.0.23 \
    --lower_case_table_names=1
```

### 数据库4

```shell
docker run -it -d --name mysql_4 -p 12004:3306 \
    --net mynet --ip 172.18.0.5  \
    -m 400m -v /root/mysql_4/data:/var/lib/mysql \
    -v /root/mysql_4/config:/etc/mysql/conf.d \
    -e MYSQL_ROOT_PASSWORD=abc123456 \
    -e TZ=Asia/Shanghai --privileged=true \
    mysql:8.0.23 \
    --lower_case_table_names=1
```

### 数据库5

逻辑数据库  不算在集群当中

```shell
docker run -it -d --name mysql_5 -p 12005:3306 \
    --net mynet --ip 172.18.0.6  \
    -m 400m -v /root/mysql_5/data:/var/lib/mysql \
    -v /root/mysql_5/config:/etc/mysql/conf.d \
    -e MYSQL_ROOT_PASSWORD=abc123456 \
    -e TZ=Asia/Shanghai --privileged=true \
    mysql:8.0.23 \
    --lower_case_table_names=1
```

### 分库分表ShardingSphere


    docker run -it -d --name ss -p 3307:3307 \
        --net mynet --ip  172.18.0.7 \
        -m 800m -v /root/ss:/root/ss \
        -e TZ=Asiz/Shanghai --privileged=true  \
        jdk bash
        
    cd /root/ss/
    unzip ShardingSphere.zip
    cd ShardingSphere/bin
    chmod -R 777 ./*
    docker exec -it ss bash
    cd /root/ss/ShardingSphere/bin
    ./start.sh


```she
docker start ss
docker exec -it ss bash
cd /root/ss/ShardingSphere/bin
./start.sh
```

# Minio

## 镜像

`docker pull bitnami/minio`

## 安装

1. 创建文件夹 /root/minio/data

   `chmod -R 777 /root/minio/data `

2. 创建容器

```shell
docker run -it -d --name minio -m 400m \
	-p 9000:9000  -p 9001:9001 \
	--net mynet --ip 172.18.0.10 \
	-v /root/minio/data:/data \
	-e TZ=Asia/Shanghai --privileged=true \
	--env MINIO_ROOT_USER="root" \
	--env MINIO_ROOT_PASSWORD="abc123456" \
	bitnami/minio:latest
```

# Mongo

## 镜像

`docker pull mongo`

## 安装

1. 创建/root/mongo/mongod.conf

```
net:
  port: 27017
  bindIp: "0.0.0.0"

storage:
  dbPath: "/data/db"

security:
  authorization: enabled
```

2. 创建容器

```shell
docker run -it -d --name mongo \
	-p 27017:27017 \
	--net mynet --ip 172.18.0.8 \
	-v /root/mongo:/etc/mongo \
	-v /root/mongo/data/db:/data/db \
	-m 400m --privileged=true \
	-e MONGO_INITDB_ROOT_USERNAME=admin \
	-e MONGO_INITDB_ROOT_PASSWORD=abc123456 \
	-e TZ=Asia\Shanghai \
 	mongo --config /etc/mongo/mongod.conf
```

# Redis

## 镜像

`docker pull redis`

## 安装

1. 创建`/root/redis/conf/redis.conf` 文件

```yaml
bind 0.0.0.0
protected-mode yes
port 6379
tcp-backlog 511
timeout 0
tcp-keepalive 0
loglevel notice
logfile ""
databases 12
save 900 1
save 300 10
save 60 10000
stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump.db
dir ./
requirepass abc123456
```

2. 创建容器

```shell
docker run -it -d --name redis -m 200m \
-p 6379:6379 --privileged=true \
--net mynet --ip 172.18.0.9 \
-v /root/redis/conf:/usr/local/etc/redis \
-e TZ=Asia/Shanghai redis \
redis-server /usr/local/etc/redis/redis.conf
```

# Rabbitmq

## 镜像

`docker pull rabbitmq:3-management`

## 安装

```shell
docker run -it -d --name mq \
--net mynet --ip 172.18.0.11 \
-p 5672:5672 -p 15672:15672 -m 500m \
-e TZ=Asia/Shanghai --privileged=true \
 rabbitmq:3-management
```

# nacos

## 镜像

`docker pull nacos/nacos-server`

## 安装

```shell
docker run -it -d -p 8848:8848 --env MODE=standalone \
--net mynet --ip 172.18.0.12 -e TZ=Asia/Shanghai \
--name nacos nacos/nacos-server
```

# sentinel

## 镜像

`docker pull bladex/sentinel-dashboard`

## 安装

```shell
docker run -it -d --name sentinel \
-p 8719:8719 -p 8858:8858 \
--net mynet --ip 172.18.0.13 \
-e TZ=Asia/Shanghai -m 600m \
bladex/sentinel-dashboard
```

# phoenix

## 安装

```shell
docker run -it -d -p 2181:2181 -p 8765:8765 -p 15165:15165 \
-p 16000:16000 -p 16010:16010 -p 16020:16020 \
-v /root/hbase/data:/tmp/hbase-root/hbase/data \
--name phoenix --net mynet --ip 172.18.0.14 \
boostport/hbase-phoenix-all-in-one:2.0-5.0
```

## 创建表语句

```shell
docker exec -it phoenix bash
export HBASE_CONF_DIR=/opt/hbase/conf/

## 命令行客户端
/opt/phoenix-server/bin/sqlline.py localhost
```

```sql
create schema hxds;
use hxds;

create table hxds.order_voice_text( 
	"id" BIGINT not null primary key,
    "uuid" VARCHAR,
    "order_id" bigint,
    "record_file" varchar,
    "text" varchar,
    "label" varchar,
    "suggestion" varchar,
	"keywords" varchar,
    "create_time" date
);

create sequence hxds.ovt_sequence start with 1 increment by 1;

create index ovt_index_1 on hxds.order_voice_text("uuid");
create index ovt_index_2 on hxds.order_voice_text("order_id");
create index ovt_index_3 on hxds.order_voice_text("label");
create index ovt_index_4 on hxds.order_voice_text("suggestion");
create index ovt_index_5 on hxds.order_voice_text("create_time");
```

```sql
create table hxds.order_monitoring(
	"id" bigint not null primary key,
    "order_id" bigint,
    "status" tinyint,
    "records" integer,
    "safety" varchar,
    "reviews" integer,
    "alarm" tinyint,
    "create_time" date
);


create index om_index_1 on hxds.order_monitoring("order_id");
create index om_index_2 on hxds.order_monitoring("status");
create index om_index_3 on hxds.order_monitoring("safety");
create index om_index_4 on hxds.order_monitoring("suggestion");
create index om_index_5 on hxds.order_monitoring("reviews");
create index om_index_6 on hxds.order_monitoring("create_time");

create sequence hxds.om_sequence start with 1 increment by 1;
```

```sql
create table hxds.order_gps(
	"id" bigint not null primary key,
    "order_id" bigint,
    "driver_id" bigint,
    "customer_id" bigint,
    "latitude" varchar,
    "longitude" varchar,
    "speed" varchar,
    "create_time" date
);

create sequence hxds.og_sequence start with 1 increment by 1;

create index og_index_1 on hxds.order_gps("order_id");
create index og_index_2 on hxds.order_gps("driver_id");
create index og_index_3 on hxds.order_gps("customer_id");
create index og_index_4 on hxds.order_gps("create_time");
```

