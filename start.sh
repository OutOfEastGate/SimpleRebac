
#!/usr/bin/env bash
main_name='simple_rebac'

#cd ./dashboard
#
#npm run build
#
#cd ..
#
#rm -rf ./rebac-web/src/main/resources/static
#
#mv ./dashboard/build/ ./rebac-web/src/main/resources/static/
#
#pnpm run build
#
#rm -rf ./rebac-web/src/main/resources/static/document
#
#mv ./dist ./rebac-web/src/main/resources/static/document

mvn package -DskipTests

docker stop ${main_name}
echo '----stop container----'
docker rm ${main_name}
echo '----rm container----'
docker rmi ${main_name}
echo '----rm images----'
docker build -f ./DockerFile -t ${main_name} .
echo '----build image----'
docker run -id -p 8080:8080 \
--name=${main_name} ${main_name}
echo '----start container----'
