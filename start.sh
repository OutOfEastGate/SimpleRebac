
#!/usr/bin/env bash
main_name='simple_rebac'

cd ./dashboard

npm run build

#mvn package -DskipTests
#
#docker stop ${main_name}
#echo '----stop container----'
#docker rm ${main_name}
#echo '----rm container----'
#docker rmi ${main_name}
#echo '----rm images----'
#docker build -f ./DockerFile -t ${main_name} .
#echo '----build image----'
#docker run -id -p 8080:8080 \
#--name=${main_name} ${main_name}
#echo '----start container----'
