FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY backend/services/business-order/pom.xml services/business-order/
COPY backend/services/commons-api/pom.xml services/commons-api/
COPY backend/services/config-server/pom.xml services/config-server/
COPY backend/services/configuration/pom.xml services/configuration/
COPY backend/services/discovery/pom.xml services/discovery/
COPY backend/services/gateway/pom.xml services/gateway/
COPY backend/services/notification/pom.xml services/notification/
COPY backend/services/order/pom.xml services/order/
COPY backend/services/service/pom.xml services/service/
COPY backend/services/statistics/pom.xml services/statistics/
COPY backend/services/storage/pom.xml services/storage/
COPY backend/services/user/pom.xml services/user/

RUN mvn dependency:go-offline

COPY backend/services/business-order/src services/business-order/src
COPY backend/services/commons-api/src services/commons-api/src
COPY backend/services/config-server/src services/config-server/src
COPY backend/services/configuration/src services/configuration/src
COPY backend/services/discovery/src services/discovery/src
COPY backend/services/gateway/src services/gateway/src
COPY backend/services/notification/src services/notification/src
COPY backend/services/order/src services/order/src
COPY backend/services/service/src services/service/src
COPY backend/services/statistics/src services/statistics/src
COPY backend/services/storage/src services/storage/src
COPY backend/services/user/src services/user/src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /app/core-service/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
