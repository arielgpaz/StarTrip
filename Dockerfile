FROM openjdk:8-oracle

ENV APP_NAME=startrip

COPY ./target/${APP_NAME}.jar  /app/${APP_NAME}.jar

WORKDIR /app

CMD java -jar ${APP_NAME}.jar

EXPOSE 8080