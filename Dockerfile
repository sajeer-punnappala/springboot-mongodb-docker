#Build a JAR file
FROM gradle:7.3.3-jdk11-alpine as builder1
WORKDIR /repo/docker
COPY . /repo/docker
RUN gradle -i build


# the second stage of our build will extract the layers
FROM openjdk:11 as builder2
WORKDIR /repo/docker
ARG JAR_FILE=/repo/docker/build/libs/docker-0.0.1.jar
COPY --from=builder1 ${JAR_FILE} docker-training.jar
RUN java -Djarmode=layertools -jar docker-training.jar extract

# the third stage of our build will copy the extracted layers
FROM openjdk:11
WORKDIR /repo/docker
COPY --from=builder2 /repo/docker/dependencies/ ./
COPY --from=builder2 /repo/docker/spring-boot-loader/ ./
COPY --from=builder2 /repo/docker/snapshot-dependencies/ ./
COPY --from=builder2 /repo/docker/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]