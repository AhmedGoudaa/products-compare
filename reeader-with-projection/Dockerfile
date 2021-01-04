FROM openjdk:12-jdk-alpine As packager


RUN jlink \
    --add-modules java.base,java.sql,java.desktop,jdk.unsupported,java.naming,java.management,java.instrument,java.security.jgss,jdk.crypto.ec,jdk.crypto.cryptoki,jdk.management.agent \
        --verbose \
        --strip-debug \
        --compress 2 \
        --no-header-files \
        --no-man-pages \
        --output /opt/jre-min

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

#==> the custom image
FROM alpine:latest

ENV JAVA_MINIMAL=/opt/jre-min
ENV PATH="$JAVA_MINIMAL/bin:${PATH}"

COPY --from=packager "$JAVA_MINIMAL" "$JAVA_MINIMAL"


COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./


ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]



