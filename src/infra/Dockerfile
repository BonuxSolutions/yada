FROM openjdk:8-jdk-alpine

VOLUME /tmp

ARG JAR_FILE
ARG JAVA_OPTS

ADD ${JAR_FILE} yada.jar

HEALTHCHECK --interval=5m --timeout=1m CMD curl -f http://localhost:8080/actuator/health && exit 0 || exit 1

ENTRYPOINT ["java","-jar","yada.jar"]