#####################멀티스테이지 빌드 Dockerfile#####################
#FROM openjdk:11-slim as build
#
#LABEL maintainer=:"Soobin Song <sssssoob_dev@gmail.com>"
#
#ARG JAR_FILE
#
#COPY ${JAR_FILE} app.jar
#
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)
#
#FROM openjdk:11-slim
#
#VOLUME /tmp
#
#ARG DEPENDENCY=/target/dependency
#COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
#
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.optimagrowth.license.LicenseServiceApplication"]

#####################레이어로 나뉜 JAR를 위한 Dockerfile#####################
FROM openjdk:11-slim as build
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-slim
WORKDIR application
COPY --from=build application/dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/application/ ./
ENTRYPOINT ["java","org.springfamework.boot.loader/JarLauncher"]
