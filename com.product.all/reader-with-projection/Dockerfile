FROM openjdk:15-jdk-alpine As packager

# create a custom JRE image to reduce the size of docker image from 500MB to 99MB
RUN jlink \
    --add-modules java.base,java.sql,java.desktop,jdk.unsupported,java.naming,java.management,java.instrument,java.security.jgss,jdk.crypto.ec,jdk.crypto.cryptoki,jdk.management.agent \
        --compress 2 \
        --no-header-files \
        --no-man-pages \
        --output /opt/jre-min

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN true
RUN java -Djarmode=layertools -jar application.jar extract

RUN ls
#==> the custom image
FROM alpine:latest

ENV JAVA_MINIMAL=/opt/jre-min
ENV PATH="$JAVA_MINIMAL/bin:${PATH}"

COPY --from=packager "$JAVA_MINIMAL" "$JAVA_MINIMAL"
RUN true

COPY --from=packager dependencies/ ./
RUN true
COPY --from=packager snapshot-dependencies/ ./
RUN true
COPY --from=packager spring-boot-loader/ ./
RUN true
COPY --from=packager application/ ./
RUN true

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]