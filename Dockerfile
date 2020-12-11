
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=build/libs/inventory-management-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} inventory-management.jar

ENTRYPOINT ["java","-jar","inventory-management.jar"]
