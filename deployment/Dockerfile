FROM openjdk:8u131-jdk

ENV APP_NAME service-smart-home

COPY build/libs/${APP_NAME}*.jar /var/${APP_NAME}/${APP_NAME}.jar

WORKDIR /var/${APP_NAME}
ENTRYPOINT exec java -jar ${APP_NAME}.jar --spring.cloud.bootstrap.enabled=false
