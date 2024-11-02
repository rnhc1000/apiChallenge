
 # -- check the path
 $pwd
 /home/<username>

 # --- clone the  repository
 git clone https://github.com/rnhc1000/apiChallenge.git

 # --- move to the project directory
 cd apiChallenge

 # --- check if Dockerfile and dockerBuild.sh are there
 ls -l

# - dockerBuild.sh
# - Dockerfile

 # -- pull java 17 image
 docker pull  amazoncorretto:17-alpine3.20

# --- check if build is ok
./gradlew build && java -jar build/libs/challengeapi-1.1.23.0.1
# --- preparing the directory layout
mkdir -p build/dependency

cd build/dependency
jar -xf ../libs/*.jar

# --- mode to where Dockerfile is
cd ../../

#--- build the image, adjusting the version
docker build --build-arg DEPENDENCY=build/dependency -t jobsity/challenge:challengeapi:x-y-z .

# --- check the image
docker image | grep api

# --- create a volume for database
docker create volume mysql_db

# --- connect to the database using credentials available at classpath:db.properties

# --- setup the database as per instructions below

CREATE DATABASE challenge;
 use challenge;
 CREATE USER 'adminChallenge'@'%' identified by '@ch4ll3ng3@';
 GRANT ALL PRIVILEGES on challenge.* to 'adminChallenge'@'%' with grant option;
 CREATE TABLE `tb_roles` (

    `role_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` varchar(50) NOT NULL

);
INSERT INTO tb_roles(role_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO tb_roles(role_id, role) VALUES (2, 'ROLE_USER');

# -- run the command

docker-compose -f docker-compose.jobsity.yml up -docker

# -- check the container status
docker ps

CONTAINER ID   IMAGE                                       COMMAND                  CREATED          STATUS                 PORTS
                                                                       NAMES
f8309d6c9a7c   rnhc757/images:challengeapi-1.1.10.25.1     "java -cp app:app/li…"   29 minutes ago   Up 1 minutes          0.0.0.0:8097->8097/t
cp, :::8097->8097/tcp

8a0d1a67c6e7   mysql:latest                                "docker-entrypoint.s…"   7 weeks ago      Up 1 minutes (healthy)   0.0.0.0:3306->3306/t
cp, :::3306->3306/tcp, 33060/tcp                                       mysql

# --- check if the application is running

docker logs --tail 1 challenge-api

 # --- check if the message is like below:

24-10-25 Fri 13:00:55.690 INFO  [restartedMain] b.d.f.c.Application Application startup at 2024-10-25T13:00:55.690392949, zone 2024-10-25T13:00:55.690597059-03:00[America/Sao_Paulo], running java 17.0.12

# --- if the message is like above, go to http://127.0.0.1:8097/swagger-ui/index.html
# --- get a token via POST using the credentials (admin@challenge.com) and (@ch4ll3ng3@) and choosing
# --- the option TryOut.
# --- Copy the access token returned and paste it at the Authorize button on top of the web page
# --- Confirm, close the pop-up window and go to the GET /api/v1/contacts and check the results.







