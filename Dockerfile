FROM openjdk:17.0.2-jdk

ARG OPENIA_API_KEY_ARG

ENV OPENIA_API_KEY=$OPENIA_API_KEY_ARG

WORKDIR /usr/src/app

COPY ./build/libs/grow_control-0.0.1-SNAPSHOT.jar .

COPY ./src/main/resources/docker-application.yml ./application.yml

RUN echo OpenIA API Key: $OPENIA_API_KEY

EXPOSE 8080

#CMD ["java", "-jar", "/usr/src/app/grow_control-0.0.1-SNAPSHOT.jar", "--assistant.enabled=true", "--assistant.openia.apikey=$OPENIA_API_KEY"]

CMD ["/bin/sh", "-c", "if [ -n \"$OPENIA_API_KEY\" ]; then \
    java -jar /usr/src/app/grow_control-0.0.1-SNAPSHOT.jar --assistant.enabled=true --assistant.openia.apikey=$OPENIA_API_KEY; \
else \
    java -jar /usr/src/app/grow_control-0.0.1-SNAPSHOT.jar; \
fi"]