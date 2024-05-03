FROM openjdk:17.0.2-jdk

WORKDIR /usr/src/app

COPY ./build/libs/grow_control-0.0.1-SNAPSHOT.jar .

COPY ./src/main/resources/docker-application.yml ./application.yml

RUN echo "OpenIA API Key: $OPENIA_API_KEY"

EXPOSE 8080

CMD ["java", "-jar", "/usr/src/app/grow_control-0.0.1-SNAPSHOT.jar", "--assistant.enabled=true", "--assistant.openia.apikey=$OPENIA_API_KEY"]
