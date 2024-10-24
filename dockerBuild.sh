
 # -- check the path
 $pwd
 /home/<username>

 # --- clone the  repository
 git clone https://github.com/rnhc1000/apiChallenge.git

 # --- move to the project directory
 cd apiChallenge/src

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



