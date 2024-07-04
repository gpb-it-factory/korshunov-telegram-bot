FROM gradle:8.7 as build

WORKDIR /appBot

COPY . .

RUN ./gradlew build


FROM openjdk:17-alpine

WORKDIR /appBot

COPY --from=build /appBot/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]