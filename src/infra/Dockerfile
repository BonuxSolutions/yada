FROM openjdk:10-jdk

VOLUME /tmp

ARG JAR_FILE
ARG JAVA_OPTS

ADD ${JAR_FILE} yada.jar
COPY run.sh run.sh

ENV JAVA_OPTS "${JAVA_OPTS}"

HEALTHCHECK --interval=5m --timeout=1m CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["/bin/sh", "run.sh"]